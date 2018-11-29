package cn.gdqy.aotw.service.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.gdqy.aotw.common.GlobalConstant;
import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.mapper.GroupMapper;
import cn.gdqy.aotw.mapper.GroupmemberMapper;
import cn.gdqy.aotw.mapper.UserMapper;
import cn.gdqy.aotw.pojo.Group;
import cn.gdqy.aotw.pojo.GroupExample;
import cn.gdqy.aotw.pojo.GroupmemberExample;
import cn.gdqy.aotw.pojo.GroupmemberKey;
import cn.gdqy.aotw.pojo.User;
import cn.gdqy.aotw.pojo.UserExample;
import cn.gdqy.aotw.pojo.UserExample.Criteria;
import cn.gdqy.aotw.service.UserService;
import cn.gdqy.aotw.utils.MD5Code;
import cn.gdqy.aotw.utils.UploadFileHelper;
import cn.gdqy.aotw.utils.WebHelper;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private GroupMapper groupMapper;
	@Autowired
	private GroupmemberMapper groupmemberMapper;

	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultView login(String username, String password, boolean isRemeber) {
		if (isRemeber && isCookieExist()) {
			ResultView result = loginByCookie();
			if (result.getIsOk().equals(ResultView.ERROR)) {
				result = loginByUsernamePassword(username, password, true);
			}
			return result;
		}
		return loginByUsernamePassword(username, password, isRemeber);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultView login(String username, String password) {
		return loginByUsernamePassword(username, password, false);
	}

	private ResultView loginByUsernamePassword(String username, String password, boolean isRemeber) {
		if ((username == null || username.trim().equals("")) || ((password == null) || password.trim().equals(""))) {
			ResultView result = new ResultView();
			result.setIsOk(ResultView.ERROR);
			result.setMsg("登录名或密码错误！");
			clearCookie();
			return result;
		}
		password = new MD5Code().getMD5ofStr(password);
		ResultView result = checkLogin(username, password);
		if (isRemeber) {
			if (result.getIsOk().equals(ResultView.SUCCESS)) {
				generateCookie();
			}
		}
		return result;
	}
	
	//清除cookie数据
	private void clearCookie() {
		String host = WebHelper.getHttpServletRequest().getServerName();
		Cookie loginNameCookie = new Cookie(GlobalConstant.SESSION_LOGIN_USERNAME, null);
		loginNameCookie.setMaxAge(0);
		loginNameCookie.setPath("/");
		loginNameCookie.setDomain(host);
        WebHelper.getHttpServletResponse().addCookie(loginNameCookie);
        Cookie passwordCookie = new Cookie(GlobalConstant.SESSION_LOGIN_PASSWORD, null);
        passwordCookie.setMaxAge(0);
        passwordCookie.setPath("/");
        passwordCookie.setDomain(host);
        WebHelper.getHttpServletResponse().addCookie(passwordCookie);
	}
	
	//检查cookie是否存在
	private boolean isCookieExist() {
		Cookie[] cookies = WebHelper.getHttpServletRequest().getCookies();
		boolean isCookieExist = false;
		if (cookies != null) {
			boolean isUsernameCookieExist = false;
			boolean isPasswordCookieExist = false;
			for (Cookie cookie : cookies) {
				if (GlobalConstant.SESSION_LOGIN_USERNAME.equals(cookie.getName())) {
					isUsernameCookieExist = true;
				}
				if (GlobalConstant.SESSION_LOGIN_PASSWORD.equals(cookie.getName())) {
					isPasswordCookieExist = true;
				}
			}
			isCookieExist = isUsernameCookieExist && isPasswordCookieExist;
		}
		return isCookieExist;
	}
	
	//生成cookie
	private void generateCookie() {
		User user = WebHelper.getCurrentUser();
        String host = WebHelper.getHttpServletRequest().getServerName();
        Cookie cookie = new Cookie(GlobalConstant.SESSION_LOGIN_USERNAME, user.getUsername()); // 保存用户名到Cookie
        cookie.setPath("/");
        cookie.setDomain(host);
        cookie.setMaxAge(60*60*24*30);	//保存期限为一个月
        WebHelper.getHttpServletResponse().addCookie(cookie);
        cookie = new Cookie(GlobalConstant.SESSION_LOGIN_PASSWORD, user.getPassword());
        cookie.setPath("/");
        cookie.setDomain(host);
        cookie.setMaxAge(60*60*24*30);
        WebHelper.getHttpServletResponse().addCookie(cookie);
	}
	
	//检查登录的结果
	private ResultView checkLogin(String username, String password) {
		ResultView result = new ResultView();
		User user = findUser(username, password);
		if (user == null) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("登录名或密码错误！");
			clearCookie();
			return result;
		}
		if (GlobalConstant.UserStatus.ENABLE.equals(user.getStatus())) {
			WebHelper.getSession().setAttribute("user", user);
		} else {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("当前账号已被禁用!");
		}
		return result;
	}

	//通过cookie进行登录
	private ResultView loginByCookie() {
		ResultView result = new ResultView();
		String usernameCookie = null;
		String passwordCookie = null;
		Cookie[] cookies = WebHelper.getHttpServletRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (GlobalConstant.SESSION_LOGIN_USERNAME.equals(cookie.getName())) {
					usernameCookie = cookie.getValue(); // 得到cookie的用户名
				}
				if (GlobalConstant.SESSION_LOGIN_PASSWORD.equals(cookie.getName())) {
					passwordCookie = cookie.getValue(); // 得到cookie的密码
				}
			}

			if ((usernameCookie == null || usernameCookie.trim().equals(""))
					|| ((passwordCookie == null) || passwordCookie.trim().equals(""))) {
				result.setIsOk(ResultView.ERROR);
				result.setMsg("登录名或密码错误！");
				clearCookie();
			} else {
				result = checkLogin(usernameCookie, passwordCookie);
			}
		} else {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("登录名或密码错误！");
			clearCookie();
		}

		return result;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public User findUser(String username, String password) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username).andPasswordEqualTo(password);
		List<User> list = userMapper.selectByExample(example);
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public User findUser(String username) {
		return userMapper.selectByPrimaryKey(username);
	}

	public ResultView register(String username, String password, String email) {
		ResultView result = new ResultView();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		User oldUser = userMapper.selectByPrimaryKey(user.getUsername());
		if (oldUser != null) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("用户名已被注册");
			return result;
		}
		user.setStatus(GlobalConstant.UserStatus.ENABLE);
		user.setIsadmin(GlobalConstant.UserRole.ORDINARY_USER);
		user.setRegistertime(new Date());
		user.setPassword(new MD5Code().getMD5ofStr(user.getPassword()));
		user.setImage(UploadFileHelper.DEFAULT_FILE_URL);
		userMapper.insert(user);
		return result;
	}

	public ResultView register(User user, MultipartFile file) {
		ResultView result = new ResultView();
		User oldUser = userMapper.selectByPrimaryKey(user.getUsername());
		if (oldUser != null) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("用户名已被注册");
			return result;
		}
		String imageUrl = UploadFileHelper.saveFile(file);
		user.setImage(imageUrl);
		user.setRegistertime(new Date());
		user.setStatus(GlobalConstant.UserStatus.ENABLE);
		user.setIsadmin(GlobalConstant.UserRole.ORDINARY_USER);
		user.setPassword(new MD5Code().getMD5ofStr(user.getPassword()));
		userMapper.insert(user);
		return result;
	}

	public ResultView updateNoPassword(User user) {
		ResultView result = new ResultView();
		userMapper.updateByPrimaryKeySelective(user);
		user = findUser(WebHelper.getCurrentUser().getUsername(), WebHelper.getCurrentUser().getPassword());
		WebHelper.getSession().setAttribute("user", user);
		return result;
	}

	public ResultView update(User user) {
		ResultView result = new ResultView();
		String password = new MD5Code().getMD5ofStr(user.getPassword());
		user.setPassword(password);
		userMapper.updateByPrimaryKeySelective(user);
		WebHelper.getCurrentUser().setPassword(password);
		return result;
	}

	public ResultView updateNoPassword(User user, MultipartFile file) {
		if (file != null) {
			String imageUrl = UploadFileHelper.saveFile(file);
			user.setImage(imageUrl);
		}
		ResultView result = updateNoPassword(user);
		return result;
	}

	public ResultView updatePassword(String newPassword, String oldPassword) {
		ResultView result = new ResultView();
		oldPassword = new MD5Code().getMD5ofStr(oldPassword);
		if (WebHelper.getCurrentUser().getPassword().equals(oldPassword)) {
			User user = new User();
			user.setUsername(WebHelper.getCurrentUser().getUsername());
			user.setPassword(new MD5Code().getMD5ofStr(newPassword));
			userMapper.updateByPrimaryKeySelective(user);
		} else {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("原始密码不正确！");
		}
		return result;
	}

	public ResultView updateLocation(String userName, String location) {
		ResultView result = new ResultView();
		User user = new User();
		user.setUsername(userName);
		user.setLocation(location);
		userMapper.updateByPrimaryKeySelective(user);
		return result;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultView fuzzySearchByUserName(String username) {
		ResultView result = new ResultView();
		UserExample example = new UserExample();
		example.setOrderByClause("userName");
		Criteria criteria = example.createCriteria();
		criteria.andUsernameLike("%" + username + "%");
		List<User> userList = userMapper.selectByExample(example);
		result.putData("userList", userList);
		return result;
	}

	public ResultView updateUserStatus(String userName, Byte status) {
		ResultView result = new ResultView();
		if (WebHelper.getCurrentUser().getIsadmin().equals(GlobalConstant.UserRole.ADMIN)) {
			User user = new User();
			user.setUsername(userName);
			user.setStatus(status);
			userMapper.updateByPrimaryKeySelective(user);
		} else {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("抱歉，你没有权限！");
		}
		return result;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultView findAllJoinGroups(String userName) {
		ResultView result = new ResultView();
		GroupmemberExample example = new GroupmemberExample();
		example.createCriteria().andUsernameEqualTo(userName);
		List<GroupmemberKey> list = groupmemberMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			List<Group> groupList = new ArrayList<Group>();
			for (GroupmemberKey e : list) {
				Group group = groupMapper.selectByPrimaryKey(e.getGroupid());
				groupList.add(group);
			}
			Collections.sort(groupList, new Comparator<Group>() {
				public int compare(Group o1, Group o2) {
					return Collator.getInstance(Locale.CHINESE).compare(o1.getName(), o2.getName());
				}
			});
			result.putData("groupList", groupList);
		}
		return result;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultView findAllCreateGroups(String userName) {
		ResultView result = new ResultView();
		GroupExample example = new GroupExample();
		example.setOrderByClause("name");
		example.createCriteria().andUsernameEqualTo(userName);
		List<Group> groupList = groupMapper.selectByExample(example);
		Collections.sort(groupList, new Comparator<Group>() {
			public int compare(Group o1, Group o2) {
				return Collator.getInstance(Locale.CHINESE).compare(o1.getName(), o2.getName());
			}
		});
		result.putData("groupList", groupList);
		return result;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void logout() {
		if (isCookieExist()) {
			clearCookie();
		}
		WebHelper.getSession().removeAttribute("user");
		WebHelper.getSession().invalidate();
		return;
	}
	
	public ResultView resetPassword(String username, String password, String validCode) {
		ResultView result = new ResultView();
		String emailCode = (String) WebHelper.getSession().getAttribute("emailCode");
		if (emailCode == null || !emailCode.equals(validCode)) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("验证码不正确！");
			return result;
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(new MD5Code().getMD5ofStr(password));
		userMapper.updateByPrimaryKeySelective(user);
		return result;
	}
}	

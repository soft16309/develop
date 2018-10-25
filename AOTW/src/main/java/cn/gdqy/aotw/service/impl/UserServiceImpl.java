package cn.gdqy.aotw.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public ResultView login(String userName, String password) {
		ResultView result = new ResultView();
		password = new MD5Code().getMD5ofStr(password);
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName).andPasswordEqualTo(password);
		List<User> list = userMapper.selectByExample(example);
		if (list == null || list.size() < 1) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("登录名或密码错误！");
			return result;
		}
		User user = list.get(0);
		if (GlobalConstant.UserStatus.ENABLE.equals(user.getStatus())) {
			WebHelper.getSession().setAttribute("user", user);			
		} else {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("当前账号已被禁用!");
		}
		return result;
	}
	
	public ResultView register(User user) {
		ResultView result = new ResultView();
		User oldUser = userMapper.selectByPrimaryKey(user.getUsername());
		if (oldUser != null) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("用户名已被注册");
			return result;
		}
		user.setStatus(GlobalConstant.UserStatus.ENABLE);
		user.setRegistertime(new Date());
		user.setPassword(new MD5Code().getMD5ofStr(user.getPassword()));
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
		user.setPassword(new MD5Code().getMD5ofStr(user.getPassword()));
		userMapper.insert(user);
		return result;
	}

	public ResultView updateNoPassword(User user) {
		ResultView result = new ResultView();
		userMapper.updateByPrimaryKeySelective(user);
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

	public ResultView updatePassword(String userName, String password) {
		ResultView result = new ResultView();
		User user = new User();
		user.setUsername(userName);
		user.setPassword(new MD5Code().getMD5ofStr(password));
		userMapper.updateByPrimaryKeySelective(user);
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

	@Transactional(propagation=Propagation.SUPPORTS)
	public ResultView fuzzySearchByUserName(String username) {
		ResultView result = new ResultView();
		UserExample example = new UserExample();
		example.setOrderByClause("userName");
		Criteria criteria =  example.createCriteria();
		criteria.andUsernameLike("%"+username+"%");
		List<User> userList = userMapper.selectByExample(example);
		result.putData("userList", userList);
		return result;
	}

	public ResultView updateUserStatus(String userName, Byte status) {
		ResultView result = new ResultView();
		User user = new User();
		user.setUsername(userName);
		user.setStatus(status);
		userMapper.updateByPrimaryKeySelective(user);
		return result;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
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
			groupList.sort(new Comparator<Group>() {
				public int compare(Group o1, Group o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			result.putData("groupList", groupList);
		}
		return result;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public ResultView findAllCreateGroups(String userName) {
		ResultView result = new ResultView();
		GroupExample example = new GroupExample();
		example.setOrderByClause("name");
		example.createCriteria().andUsernameEqualTo(userName);
		List<Group> groupList = groupMapper.selectByExample(example);
		result.putData("groupList", groupList);
		return result;
	}
}

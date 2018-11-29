package cn.gdqy.aotw.controller;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.Group;
import cn.gdqy.aotw.pojo.User;
import cn.gdqy.aotw.service.UserService;
import cn.gdqy.aotw.utils.WebHelper;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("register")
	public ResultView register(User user, MultipartFile file) {
		return userService.register(user, file);
	}
	
	@ResponseBody
	@RequestMapping("quickRegister")
	public ResultView register(String username, String password, String email) {
		return userService.register(username, password, email);
	}
	
	@ResponseBody
	@RequestMapping("login")
	public ResultView login(String username, String password, @RequestParam(defaultValue="false") boolean isRemeber) {
		ResultView result = userService.login(username, password, isRemeber);
		if (result.getIsOk().equals(ResultView.SUCCESS)) {
			result.putData("user", WebHelper.getCurrentUser());
			result.putData("sessionId", WebHelper.getSession().getId());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("updateLocation")
	public ResultView updateLocation(String username, String location) {
		return userService.updateLocation(username, location);
	}
	
	@ResponseBody
	@RequestMapping("updatePersonInfo")
	public ResultView updatePersonInfo(User user, MultipartFile file) {
		return userService.updateNoPassword(user, file);
	}
	
	@ResponseBody
	@RequestMapping("updatePersonInfoNoImage")
	public ResultView updatePersonInfo(User user) {
		return userService.updateNoPassword(user);
	}
	
	@ResponseBody
	@RequestMapping("logout")
	public ResultView logout() {
		ResultView result = new ResultView();
		userService.logout();
		return result;
	}
	
	@ResponseBody
	@RequestMapping("findUser")
	public ResultView findUser(String username) {
		ResultView result = new ResultView();
		User user = userService.findUser(username);
		result.putData("user", user);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("getAllGroups")
	public ResultView getAllGroups() {
		ResultView r1 = userService.findAllCreateGroups(WebHelper.getCurrentUser().getUsername());
		ResultView r2 = userService.findAllJoinGroups(WebHelper.getCurrentUser().getUsername());
		List<Group> createGroups =  (List<Group>) r1.getData("groupList");
		List<Group> joinGroups = (List<Group>) r2.getData("groupList");
		List<Group> groupList = new ArrayList<Group>();
		if (createGroups != null && createGroups.size() > 0) {
			groupList.addAll(createGroups);
		}
		if (joinGroups != null && joinGroups.size() > 0) {			
			groupList.addAll(joinGroups);
		}
		Collections.sort(groupList, new Comparator<Group>() {
			@Override
			public int compare(Group o1, Group o2) {
				return Collator.getInstance(Locale.CHINESE).compare(o1.getName(),o2.getName());
			}
		});
		ResultView result = new ResultView();
		result.putData("groupList", groupList);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("getCreateGroups")
	public ResultView getCreateGroups() {
		return userService.findAllCreateGroups(WebHelper.getCurrentUser().getUsername());
	}
	
	@ResponseBody
	@RequestMapping("getJoinGroups")
	public ResultView getJoinGroups() {
		return userService.findAllJoinGroups(WebHelper.getCurrentUser().getUsername());
	}
	
	@ResponseBody
	@RequestMapping("updatePassword")
	public ResultView updatePassword(String newPassword, String oldPassword) {
		return userService.updatePassword(newPassword, oldPassword);
	}
	
	@ResponseBody
	@RequestMapping("fuzzySearchByUserName")
	public ResultView fuzzySearchByUserName(String username) {
		return userService.fuzzySearchByUserName(username);
	}
	
	@ResponseBody
	@RequestMapping("updateUserStatus")
	public ResultView updateUserStatus(String username, Byte status) {
		return userService.updateUserStatus(username, status);
	}
	
	@ResponseBody
	@RequestMapping("resetPassword")
	public ResultView resetPassword(String username, String password, String validCode) {
		return userService.resetPassword(username, password, validCode);
	}
	
	@RequestMapping("userManage")
	public String groupManage(String username) {
		ResultView result = userService.fuzzySearchByUserName(username);
		WebHelper.getHttpServletRequest().setAttribute("userList", result.getData("userList"));
		return "/pages/admin/userManage";
	}
}

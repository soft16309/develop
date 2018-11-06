package cn.gdqy.aotw.controller;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.gdqy.aotw.common.ResultView;
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
	public ResultView register(User user) {
		return userService.register(user);
	}
	
	@ResponseBody
	@RequestMapping("login")
	public ResultView login(String username, String password) {
		return userService.login(username, password);
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
	@RequestMapping("logout")
	public ResultView logout() {
		ResultView result = new ResultView();
		WebHelper.getSession().removeAttribute("user");
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
}

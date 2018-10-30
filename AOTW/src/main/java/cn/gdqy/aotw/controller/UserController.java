package cn.gdqy.aotw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.User;
import cn.gdqy.aotw.service.UserService;

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
	@RequestMapping("login")
	public ResultView login(String userName, String password) {
		return userService.login(userName, password);
	}
	
}

package cn.gdqy.aotw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.service.JavaMailService;

@Controller
@RequestMapping("/email/")
public class EmailCodeController {
	@Autowired
	private JavaMailService mailService;
	
	@ResponseBody
	@RequestMapping("sendMailCode")
	public ResultView sendMailCode(String username, String email) {
		return mailService.sendEmailCode(username, email);
	}
}

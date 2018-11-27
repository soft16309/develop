package cn.gdqy.aotw.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.User;
import cn.gdqy.aotw.service.JavaMailService;
import cn.gdqy.aotw.service.UserService;
import cn.gdqy.aotw.utils.WebHelper;

@Service
public class JavaMailServiceImpl implements JavaMailService{
	private final String[] CODES =  new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
			"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
			"b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
			"w", "x", "y", "z" };
	private List<String> codeList;		//保存每一个code的集合
	private final String emailRegex = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
	
	@Autowired
	private UserService userService;
	
	@Value("${javamail.hostname}")
	private String HOST_NAME;
	@Value("${javamail.sendEmail}")
	private String SEND_EMAIL;
	@Value("${javamail.authorization}")
	private String AUTHORIZATION;
	@Value("${javamail.smtpPort}")
	private Integer SMTP_PORT;
	@Value("${javamail.charset}")
	private String CHARSET;
	@Value("${javamail.nickname}")
	private String NICKNAME;
	
	/**
	 * 生成随机验证码
	 * @param codeNum		验证码位数
	 * @return				生成的验证码
	 */
	private String generateCode(int codeNum) {
		if (codeList == null) {
			codeList = Arrays.asList(CODES);
		}
		Collections.shuffle(codeList);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < codeNum; i++) {
			sb.append(codeList.get(i));
		}
		System.out.print(sb.toString());
		return sb.toString();
	}
	
	private String sendEamilCode(String email) {
		HtmlEmail send = new HtmlEmail();
		String code = generateCode(4);
		try {
			send.setHostName(HOST_NAME);
			send.setSmtpPort(SMTP_PORT);
			send.setSSLOnConnect(true); //开启SSL加密
			send.setCharset(CHARSET);
			send.addTo(email); 			//接收者的QQEamil
			send.setFrom(SEND_EMAIL, NICKNAME);
			send.setAuthentication(SEND_EMAIL, AUTHORIZATION);
			send.setSubject("【天下纵横开发团队】");		//email的标题
			send.setMsg("【天下纵横】欢迎使用天下纵横APP，本次验证码为: " + code + "。");
	
			send.send();//发送
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	public ResultView sendEmailCode(String username, String email) {
		ResultView result = new ResultView();
		if (username == null || username.trim().equals("")) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("登录名不能为空！");
			return result;
		}
		if (email == null || email.trim().equals("") || !email.matches(emailRegex)) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("邮箱格式不正确！");
			return result;
		}
		User user = userService.findUser(username);
		if (user == null || !user.getEmail().equals(email)) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("输入邮箱与登录名账号所绑定的邮箱不一致！");
			return result;
		}
		String code = sendEamilCode(email);
		WebHelper.getSession().setAttribute("emailCode", code);
		return result;
	}
}

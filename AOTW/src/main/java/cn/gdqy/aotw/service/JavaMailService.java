package cn.gdqy.aotw.service;

import cn.gdqy.aotw.common.ResultView;

public interface JavaMailService {
	/**
	 * 发送邮箱验证码
	 * @param username  账号的用户名
	 * @param email		账号绑定的邮箱
	 * @return			
	 */
	ResultView sendEmailCode(String username, String email);
}

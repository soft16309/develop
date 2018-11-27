package cn.gdqy.aotw.service;

import org.springframework.web.multipart.MultipartFile;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.User;

public interface UserService {
	ResultView login(String userName, String password);
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param isRemeber 是否记住登录用户，true时回写cookie以便下次自动登录
	 * @return
	 */
	ResultView login(String username, String password, boolean isRemeber);
	ResultView register(User user);
	ResultView register(User user, MultipartFile file);
	ResultView updateNoPassword(User user);
	ResultView update(User user);
	ResultView updateLocation(String userName, String location);
	ResultView fuzzySearchByUserName(String userName);
	ResultView updateUserStatus(String userName, Byte status);
	ResultView findAllJoinGroups(String userName);
	ResultView findAllCreateGroups(String userName);
	
	/**
	 * 更新用户信息（密码不作更改）
	 * @param user	要更新的用户信息（用户username必须存在）
	 * @param file  要更新的头像文件
	 * @return
	 */
	ResultView updateNoPassword(User user, MultipartFile file);
	User findUser(String username, String password);
	User findUser(String username);

	ResultView updatePassword(String newPassword, String oldPassword);

	void logout();

	/**
	 * 重置密码
	 * @param username		要重置密码的用户名
	 * @param password		新密码
	 * @param validCode		邮箱验证码
	 * @return
	 */
	ResultView resetPassword(String username, String password, String validCode);
}

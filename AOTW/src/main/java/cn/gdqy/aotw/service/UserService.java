package cn.gdqy.aotw.service;

import org.springframework.web.multipart.MultipartFile;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.User;

public interface UserService {
	ResultView login(String userName, String password);
	ResultView register(User user);
	ResultView register(User user, MultipartFile file);
	ResultView updateNoPassword(User user);
	ResultView update(User user);
	ResultView updatePassword(String userName, String password);
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
}

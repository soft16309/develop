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
	ResultView fuzzySearchByName(String userName);
	ResultView updateUserStatus(String userName, Byte status);
	ResultView findAllJoinGroups(String userName);
	ResultView findAllCreateGroups(String userName);
}

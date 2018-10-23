package cn.gdqy.aotw.service;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.User;

public interface UserService {
	ResultView login(String userName, String password);
	ResultView register(User user);
}

package cn.gdqy.aotw.service;

import cn.gdqy.aotw.pojo.User;

public interface UserService {
	User findUser(String username);
	User findUser(String username, String password);
}

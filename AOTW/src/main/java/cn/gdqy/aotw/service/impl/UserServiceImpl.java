package cn.gdqy.aotw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdqy.aotw.mapper.UserMapper;
import cn.gdqy.aotw.pojo.User;
import cn.gdqy.aotw.pojo.UserExample;
import cn.gdqy.aotw.pojo.UserExample.Criteria;
import cn.gdqy.aotw.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User findUser(String username) {
		return userMapper.selectByPrimaryKey(username);
	}

	public User findUser(String username, String password) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username).andPasswordEqualTo(password);
		List<User> list = userMapper.selectByExample(example);
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

}

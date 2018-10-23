package cn.gdqy.aotw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.mapper.UserMapper;
import cn.gdqy.aotw.pojo.User;
import cn.gdqy.aotw.pojo.UserExample;
import cn.gdqy.aotw.pojo.UserExample.Criteria;
import cn.gdqy.aotw.service.UserService;
import cn.gdqy.aotw.utils.WebHelper;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public ResultView login(String userName, String password) {
		ResultView result = new ResultView();
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName).andPasswordEqualTo(password);
		List<User> list = userMapper.selectByExample(example);
		if (list == null || list.size() < 1) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("登录名或密码错误！");
			return result;
		}
		WebHelper.getSession().setAttribute("user", list.get(0));
		return result;
	}
	
	public ResultView register(User user) {
		ResultView result = new ResultView();
		User oldUser = userMapper.selectByPrimaryKey(user.getUsername());
		if (oldUser != null) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("用户名已被注册");
			return result;
		}
		userMapper.insert(user);
		return result;
	}
}

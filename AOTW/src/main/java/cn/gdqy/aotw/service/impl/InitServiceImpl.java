package cn.gdqy.aotw.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.gdqy.aotw.common.GlobalConstant;
import cn.gdqy.aotw.mapper.UserMapper;
import cn.gdqy.aotw.pojo.User;
import cn.gdqy.aotw.service.InitService;
import cn.gdqy.aotw.utils.MD5Code;
import cn.gdqy.aotw.utils.UploadFileHelper;

@Service
public class InitServiceImpl implements InitService{

	@Autowired
	private UserMapper userMapper;
	
	@Value("${admin.username}")
	private String USERNAME;
	@Value("${admin.password}")
	private String PASSWORD;
	@Value("${admin.email}")
	private String EMAIL;
	
	@PostConstruct
	public void init() {
		createAdminAccount();
	}
	
	//创建管理员账号
	public void createAdminAccount() {User user = userMapper.selectByPrimaryKey(USERNAME);
		if (user == null) {
			user = new User();
			user.setUsername(USERNAME);
			user.setPassword(new MD5Code().getMD5ofStr(PASSWORD));
			user.setEmail(EMAIL);
			user.setRegistertime(new Date());
			user.setStatus(GlobalConstant.UserStatus.ENABLE);
			user.setIsadmin(GlobalConstant.UserRole.ADMIN);
			user.setImage(UploadFileHelper.DEFAULT_FILE_URL);
			userMapper.insert(user);
		}
	}
}

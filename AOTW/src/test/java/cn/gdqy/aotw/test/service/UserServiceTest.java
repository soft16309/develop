package cn.gdqy.aotw.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.User;
import cn.gdqy.aotw.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class UserServiceTest {

	@Autowired
	private UserService service;
	
	@Test
	public void testLogin() {
		ResultView result = service.login("xiaoming", "123456");
		System.out.println(result);
	}

	@Test
	public void testRegisterUser() {
		User user = new User();
		user.setUsername("xiaoming");
		user.setPassword("123456");
		user.setEmail("xiaoming@qq.com");
		service.register(user);
	}

	@Test
	public void testRegisterUserMultipartFile() {

	}

	@Test
	public void testUpdateNoPassword() {
		User user = new User();
		user.setUsername("xiaoming");
		user.setNickname("小明");
		service.updateNoPassword(user);
	}

	@Test
	public void testUpdate() {
		
	}

	@Test
	public void testUpdatePassword() {
		
	}

	@Test
	public void testUpdateLocation() {
		service.updateLocation("xiaoming", "广东省");
	}

	@Test
	public void testFuzzySearchByUserName() {
		ResultView result = service.fuzzySearchByUserName("a");
		System.out.println(result.getData("userList"));
	}

	@Test
	public void testUpdateUserStatus() {

	}

	@Test
	public void testFindAllJoinGroups() {

	}

	@Test
	public void testFindAllCreateGroups() {
		ResultView result = service.findAllCreateGroups("xiaoming");
		System.out.println(result);
	}

}

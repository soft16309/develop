package cn.gdqy.aotw.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.service.GroupMemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class GroupMemberServiceTest {

	@Autowired
	private GroupMemberService service;
	
	@Test
	public void testFindAllGroupMember() {
		ResultView result = service.findAllGroupMember(4);
		System.out.println(result.getData("userList"));
	}

	@Test
	public void testAddGroupMember() {
		ResultView result = service.addGroupMember(4, "admin");
		System.out.println(result.getData("userList"));
	}

	@Test
	public void testDeleteGroupMember() {
		service.deleteGroupMember(4, "admin");
	}

}

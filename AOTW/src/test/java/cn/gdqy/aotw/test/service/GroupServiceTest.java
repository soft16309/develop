package cn.gdqy.aotw.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.gdqy.aotw.common.GlobalConstant;
import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.Group;
import cn.gdqy.aotw.service.GroupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class GroupServiceTest {

	@Autowired
	private GroupService service;
	
	@Test
	public void testCreateGroup() {
		Group group = new Group();
		group.setName("群002");
		group.setScale(50);
		group.setSort("游戏");
		service.createGroup("xiaoming", group, null);
	}

	@Test
	public void testDissolveGroup() {
		service.dissolveGroup(4);
	}

	@Test
	public void testUpdateGroupData() {
		Group group = new Group();
		group.setName("群聊1");
		group.setId(3);
		service.updateGroupData(group);
	}

	@Test
	public void testUpdateGroupStatus() {
		service.updateGroupStatus(4, GlobalConstant.GroupStatus.DISABLE);
	}

	@Test
	public void testFuzzySearchGroupsByName() {
		ResultView result = service.fuzzySearchGroupsByName("群");
		System.out.println(result);
	}

}

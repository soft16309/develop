package cn.gdqy.aotw.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.mapper.GroupmemberMapper;
import cn.gdqy.aotw.mapper.UserMapper;
import cn.gdqy.aotw.pojo.GroupmemberExample;
import cn.gdqy.aotw.pojo.GroupmemberKey;
import cn.gdqy.aotw.pojo.User;
import cn.gdqy.aotw.service.GroupMemberService;

@Service
@Transactional
public class GroupMemberServiceImpl implements GroupMemberService {

	@Autowired
	private GroupmemberMapper groupmemberMapper;
	@Autowired
	private UserMapper userMapper;
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public ResultView findAllGroupMember(Integer groupId) {
		ResultView result = new ResultView();
		GroupmemberExample example = new GroupmemberExample();
		example.createCriteria().andGroupidEqualTo(groupId);
		List<GroupmemberKey> list = groupmemberMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			List<User> userList = new ArrayList<User>();
			for (GroupmemberKey e : list) {
				User user = userMapper.selectByPrimaryKey(e.getUsername());
				userList.add(user);
			}
			Collections.sort(userList, new Comparator<User>() {
				public int compare(User o1, User o2) {
					return o1.getUsername().compareTo(o2.getUsername());
				}
			});
			result.putData("userList", userList);
		}
		result.putData("groupOwner", userMapper.getGroupOwner(groupId));
		return result;
	}

	public ResultView addGroupMember(Integer groupId, String userName) {
		ResultView result = new ResultView();
		GroupmemberExample example = new GroupmemberExample();
		example.createCriteria().andGroupidEqualTo(groupId).andUsernameEqualTo(userName);
		List<GroupmemberKey> groupmemberKeys = groupmemberMapper.selectByExample(example);
		if (groupmemberKeys != null && !groupmemberKeys.isEmpty()) {
			result.setIsOk(ResultView.ERROR);
			result.setMsg("该成员已经在群聊里了，不能重复添加！");
			return result;
		}
		
		GroupmemberKey groupmemberKey = new GroupmemberKey();
		groupmemberKey.setGroupid(groupId);
		groupmemberKey.setUsername(userName);
		groupmemberMapper.insert(groupmemberKey);
		return result;
	}

	public ResultView deleteGroupMember(Integer groupId, String userName) {
		ResultView result = new ResultView();
		GroupmemberKey groupmemberKey = new GroupmemberKey();
		groupmemberKey.setGroupid(groupId);
		groupmemberKey.setUsername(userName);
		groupmemberMapper.deleteByPrimaryKey(groupmemberKey);
		return result;
	}
	
	public ResultView deleteGroupMembers(Integer groupId, String[] userNames) {
		ResultView result = new ResultView();
		if (userNames == null || userNames.length < 1) {
			return result;
		}
		GroupmemberKey groupmemberKey = new GroupmemberKey();
		groupmemberKey.setGroupid(groupId);
		for (String userName : userNames) {
			groupmemberKey.setUsername(userName);
			groupmemberMapper.deleteByPrimaryKey(groupmemberKey);
		}
		return result;
	}
	
	public boolean isUserInGroup(String username, Integer groupId) {
		boolean exist = false;
		GroupmemberExample example = new GroupmemberExample();
		example.createCriteria().andUsernameEqualTo(username).andGroupidEqualTo(groupId);
		List<GroupmemberKey> groupMembers = groupmemberMapper.selectByExample(example);
		if (groupMembers != null && groupMembers.size() > 0) {
			exist = true;
		}
		return exist;
	}
}

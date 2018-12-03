package cn.gdqy.aotw.service;

import cn.gdqy.aotw.common.ResultView;

public interface GroupMemberService {
	ResultView findAllGroupMember(Integer groupId);
	ResultView addGroupMember(Integer groupId, String userName);
	ResultView deleteGroupMember(Integer groupId, String userName);
	ResultView deleteGroupMembers(Integer groupId, String[] userNames);
	boolean isUserInGroup(String username, Integer groupId);
}

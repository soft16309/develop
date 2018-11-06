package cn.gdqy.aotw.service;

import org.springframework.web.multipart.MultipartFile;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.Group;

public interface GroupService {
	ResultView createGroup(String userName, Group group, MultipartFile imageFile);
	ResultView dissolveGroup(Integer groupId);
	ResultView updateGroupData(Group group);
	ResultView updateGroupStatus(Integer groupId, Byte status);
	ResultView fuzzySearchGroupsByName(String name);
	ResultView getGroupById(Integer id);
}

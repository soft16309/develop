package cn.gdqy.aotw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.service.GroupMemberService;
import cn.gdqy.aotw.service.GroupService;

@Controller
@RequestMapping("/group/")
public class GroupController {

	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupMemberService groupMemberService;
	
	@ResponseBody
	@RequestMapping("getAllGroupMember")
	public ResultView getAllGroupMember(Integer groupId) {
		return groupMemberService.findAllGroupMember(groupId);
	}
}

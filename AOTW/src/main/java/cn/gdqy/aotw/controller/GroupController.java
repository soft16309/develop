package cn.gdqy.aotw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.Group;
import cn.gdqy.aotw.service.GroupMemberService;
import cn.gdqy.aotw.service.GroupService;
import cn.gdqy.aotw.utils.WebHelper;

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
	
	@ResponseBody
	@RequestMapping("createGroup")
	public ResultView createGroup(String username, Group group, MultipartFile file) {
		return groupService.createGroup(username, group, file);
	}
	
	@ResponseBody
	@RequestMapping("fuzzySearchGroupsByName")
	public ResultView fuzzySearchGroupsByName(String name) {
		return groupService.fuzzySearchGroupsByName(name);
	}
	
	@RequestMapping("toGroupData")
	public String toGroupData(Integer groupId, Model model) {
		ResultView result = groupService.getGroupById(groupId);
		model.addAttribute("group", result.getData("group"));
		return "pages/group/groupData";
	}
	
	@ResponseBody
	@RequestMapping("joinGroup")
	public ResultView joinGroup(Integer groupId) {
		return groupMemberService.addGroupMember(groupId, WebHelper.getCurrentUser().getUsername());
	}
}

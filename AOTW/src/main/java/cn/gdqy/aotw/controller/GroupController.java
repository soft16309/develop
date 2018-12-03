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
	@RequestMapping("createGroupNoImage")
	public ResultView createGroupNoImage(String username, Group group) {
		return groupService.createGroup(username, group);
	}
	
	@ResponseBody
	@RequestMapping("fuzzySearchGroupsByName")
	public ResultView fuzzySearchGroupsByName(String name) {
		return groupService.fuzzySearchGroupsByName(name);
	}
	
	@RequestMapping("toGroupData")
	public String toGroupData(Integer groupId, Model model) {
		ResultView result = groupService.getGroupById(groupId);
		boolean isExist = groupMemberService.isUserInGroup(WebHelper.getCurrentUser().getUsername(), groupId);
		model.addAttribute("isUserInGroup", isExist);
		model.addAttribute("group", result.getData("group"));
		return "pages/group/groupData";
	}
	
	@ResponseBody
	@RequestMapping("joinGroup")
	public ResultView joinGroup(Integer groupId) {
		return groupMemberService.addGroupMember(groupId, WebHelper.getCurrentUser().getUsername());
	}
	
	@ResponseBody
	@RequestMapping("inviteGroupMember")
	public ResultView inviteGroupMember(String username, Integer groupId) {
		return groupMemberService.addGroupMember(groupId, username);
	}
	
	@ResponseBody
	@RequestMapping("deleteGroupMembers")
	public ResultView deleteGroupMembers(String usernames, Integer groupId) {
		return groupMemberService.deleteGroupMembers(groupId, usernames.split(","));
	}
	
	@ResponseBody
	@RequestMapping("quitGroup")
	public ResultView quitGroup(String username, Integer groupId) {
		return groupMemberService.deleteGroupMember(groupId, username);
	}
	
	@ResponseBody
	@RequestMapping("getGroupById")
	public ResultView getGroupById(Integer groupId) {
		return groupService.getGroupById(groupId);
	}
	
	@ResponseBody
	@RequestMapping("dissolveGroup")
	public ResultView dissolveGroup(Integer groupId) {
		return groupService.dissolveGroup(groupId);
	}
	
	@ResponseBody
	@RequestMapping("updateGroupData")
	public ResultView updateGroupData(Group group) {
		return groupService.updateGroupData(group);
	}
	
	@ResponseBody
	@RequestMapping("updateGroupStatus")
	public ResultView updateGroupStatus(Integer groupId, Byte status) {
		return groupService.updateGroupStatus(groupId, status);
	}
	
	@RequestMapping("groupManage")
	public String groupManage(String name) {
		ResultView result = groupService.fuzzySearchGroupsByName(name);
		WebHelper.getHttpServletRequest().setAttribute("groupList", result.getData("groupList"));
		return "/pages/admin/groupManage";
	}
}

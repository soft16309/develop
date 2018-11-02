package cn.gdqy.aotw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.service.UserService;
import cn.gdqy.aotw.utils.WebHelper;

@Controller
@RequestMapping("/page/")
public class PageDispatchController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("toIndex")
	public String toIndex(Model model) {
		ResultView r1 = userService.findAllCreateGroups(WebHelper.getCurrentUser().getUsername());
		ResultView r2 = userService.findAllJoinGroups(WebHelper.getCurrentUser().getUsername());
		model.addAttribute("createGroups", r1.getData("groupList"));
		model.addAttribute("joinGroups", r2.getData("groupList"));
		return "index";
	}
}

package com.ptsisi.daily.web.controller.common;

import com.google.common.collect.Lists;
import com.ptsisi.daily.model.CustomPrincipal;
import com.ptsisi.daily.model.Menu;
import com.ptsisi.daily.model.Resource;
import com.ptsisi.daily.web.service.MenuService;
import com.ptsisi.daily.web.service.ResourceService;
import com.ptsisi.daily.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhaoding on 14-10-28.
 */
@Controller
public class HomeController {

	@javax.annotation.Resource
	protected ResourceService resourceService;

	@javax.annotation.Resource
	protected UserService userService;

	@javax.annotation.Resource
	protected MenuService menuService;

	@RequestMapping({ "home", "/" })
	public void index() {
	}

	@RequestMapping("/loginUser")
	@ResponseBody
	public Object loginUser() {
		userService.reload();
		CustomPrincipal principal = CustomPrincipal.getCurrentPrincipal();
		return userService.getUser(principal.getUser().getId());
	}

	@RequestMapping("/currentMenus")
	@ResponseBody
	public Object currentMenus() {
		CustomPrincipal principal = CustomPrincipal.getCurrentPrincipal();
		List<Resource> resources = resourceService.getResources(principal.getUser());
		return menuService.getMenus(resources);
	}

	@RequestMapping("homeList")
	public void list(String id, ModelMap model) {
		if (StringUtils.isNotBlank(id)) {
			model.put("id", id);
		}
	}
}

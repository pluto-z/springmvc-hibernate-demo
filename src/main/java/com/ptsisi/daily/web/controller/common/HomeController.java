package com.ptsisi.daily.web.controller.common;

import com.ptsisi.daily.web.service.SecurityService;
import com.ptsisi.daily.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhaoding on 14-10-28.
 */
@Controller
public class HomeController {

	@javax.annotation.Resource
	protected SecurityService securityService;

	@javax.annotation.Resource
	protected UserService userService;

	@RequestMapping("home")
	public void index() {
	}

	@RequestMapping("homeList")
	public void list(String id, ModelMap model) {
		if (StringUtils.isNotBlank(id)) {
			model.put("id", id);
		}
	}
}

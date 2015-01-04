package com.ptsisi.daily.web.controller.business;

import com.ptsisi.daily.web.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhaoding on 15-1-5.
 */
@Controller
@RequestMapping("/search/param")
public class SearchController extends AbstractController {

	@Override
	protected String getEntityName() {
		return null;
	}

	@RequestMapping("info")
	@RequiresPermissions("param:info")
	public String info() {
		return "redirect:/security/user/list";
	}
}

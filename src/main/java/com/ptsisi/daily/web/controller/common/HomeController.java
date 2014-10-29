package com.ptsisi.daily.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhaoding on 14-10-28.
 */
@Controller
public class HomeController {

	@RequestMapping("/home")
	public String index() {
		return "home";
	}
}

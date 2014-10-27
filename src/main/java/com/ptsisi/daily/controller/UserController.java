package com.ptsisi.daily.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ptsisi.daily.model.UserBean;
import com.ptsisi.daily.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	protected UserService userService;

	@RequestMapping("/index")
	public ModelAndView index(UserBean user, ModelAndView modelAndView, HttpServletRequest request) {
		System.out.println(1111);
		System.out.println(modelAndView.getModel().size());
		//		userService.saveOrUpdate(user);
		return new ModelAndView("user/list");
	}
}

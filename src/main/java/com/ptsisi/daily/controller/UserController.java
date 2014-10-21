package com.ptsisi.daily.controller;

import javax.servlet.http.HttpServletRequest;

import com.ptsisi.daily.model.UserBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

  @RequestMapping("/index")
  public ModelAndView index(UserBean user, HttpServletRequest request) {
    System.out.println("11");
    return new ModelAndView("user/list");
  }
}

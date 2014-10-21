package com.ptsisi.daily.controller;

import com.ptsisi.daily.model.UserBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

  @RequestMapping("/login")
  public String login(UserBean user,boolean keepLoginCache,String captcha,Model model) {
    System.out.println(user.getUsername());
    System.out.println(user.getPassword());
    System.out.println(keepLoginCache);
    System.out.println(captcha);
    return "redirect:/user/index.action";
  }
}

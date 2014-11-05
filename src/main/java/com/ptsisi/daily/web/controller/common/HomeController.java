package com.ptsisi.daily.web.controller.common;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsisi.daily.Menu;
import com.ptsisi.daily.Resource;
import com.ptsisi.daily.model.CustomPrincipal;
import com.ptsisi.daily.web.service.SecurityService;
import com.ptsisi.daily.web.service.UserService;

/**
 * Created by zhaoding on 14-10-28.
 */
@Controller
public class HomeController {

  @Autowired
  protected SecurityService securityService;

  @Autowired
  protected UserService userService;

  @RequestMapping("home")
  public void index(Model model) {
    CustomPrincipal principal = CustomPrincipal.getCurrentPrincipal();
    Set<Resource> resources = principal.getUser().getResources();
    List<Menu> menus = securityService.getMenus(resources);
    model.addAttribute("menus", menus);
    model.addAttribute("user", principal.getUser());
  }
}

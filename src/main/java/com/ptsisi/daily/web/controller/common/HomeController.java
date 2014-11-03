package com.ptsisi.daily.web.controller.common;

import com.ptsisi.daily.Menu;
import com.ptsisi.daily.Resource;
import com.ptsisi.daily.model.CustomPrincipal;
import com.ptsisi.daily.web.service.SecurityService;
import com.ptsisi.daily.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

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
  public ModelAndView index() {
    CustomPrincipal principal = CustomPrincipal.getCurrentPrincipal();
    Set<Resource> resources = principal.getUser().getResources();
    List<Menu> menus = securityService.getMenus(resources);
    ModelAndView mv = new ModelAndView("home/index");
    mv.addObject("menus", menus);
    mv.addObject("user", principal.getUser());
    return mv;
  }
}

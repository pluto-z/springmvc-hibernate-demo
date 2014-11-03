package com.ptsisi.daily.web.controller.security;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsisi.common.collection.page.PageLimit;
import com.ptsisi.common.query.builder.OqlBuilder;
import com.ptsisi.daily.User;
import com.ptsisi.daily.model.CustomPrincipal;
import com.ptsisi.daily.model.UserBean;
import com.ptsisi.daily.web.controller.AbstractController;
import com.ptsisi.daily.web.service.UserService;

/**
 * Created by zhaoding on 14-10-27.
 */
@Controller
@RequestMapping("/security/user")
public class UserController extends AbstractController {

  @Autowired
  protected UserService userService;

  @RequestMapping("list")
  @RequiresPermissions("user:list")
  public String list(Boolean enabled, UserBean user, String orderBy, PageLimit pageLimit,ModelMap modelMap) {
    OqlBuilder<User> builder = getQueryBuilder(orderBy, pageLimit);
    if (StringUtils.isNotBlank(user.getUsername())) {
      builder.where(getShortName() + ".username like :username", "%" + user.getUsername() + "%");
    }
    if (StringUtils.isNotBlank(user.getFullName())) {
      builder.where(getShortName() + ".fullName like :fullname", "%" + user.getFullName() + "%");
    }
    if (null != enabled) {
      builder.where(getShortName() + ".enabled = :enabled", enabled);
    }
    modelMap.addAttribute("users", entityDao.search(builder));
    return "security/user/list";
  }

  @RequestMapping("get-portrait")
  public ResponseEntity<byte[]> getCurrentUserPortrait() throws IOException {
    CustomPrincipal customPrincipal = CustomPrincipal.getCurrentPrincipal();
    User user = customPrincipal.getUser();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);
    return new ResponseEntity<byte[]>(userService.getPortrait(user), headers, HttpStatus.OK);

  }

  @Override
  protected String getEntityName() {
    return User.class.getName();
  }
}

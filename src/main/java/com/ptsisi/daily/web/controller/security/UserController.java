package com.ptsisi.daily.web.controller.security;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ptsisi.common.collection.Order;
import com.ptsisi.daily.User;
import com.ptsisi.daily.model.CustomPrincipal;
import com.ptsisi.daily.web.controller.AbstractController;
import com.ptsisi.daily.web.service.UserService;

@Controller
@RequestMapping("/security/user")
public class UserController extends AbstractController {

  @Resource
  protected UserService userService;

  @RequestMapping("list")
  @RequiresPermissions("user:list")
  public void list() {
  }

  @RequestMapping("update")
  @RequiresPermissions("user:update")
  public void update() {
  }

  @RequestMapping("edit")
  @RequiresPermissions("user:edit")
  public void edit() {
  }

  @RequestMapping("delete")
  @RequiresPermissions("user:delete")
  public void delete() {
  }

  @RequestMapping("info")
  @RequiresPermissions("user:info")
  public void info() {
  }

  @RequestMapping("json")
  @RequiresPermissions("user:list")
  @ResponseBody
  public Object json(Order order) {
    return entityDao.searchObj(getQueryBuilder());
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

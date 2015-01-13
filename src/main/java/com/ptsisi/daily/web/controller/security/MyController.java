package com.ptsisi.daily.web.controller.security;

import com.ptsisi.common.Entity;
import com.ptsisi.daily.model.User;
import com.ptsisi.daily.web.controller.AbstractController;
import com.ptsisi.daily.web.service.UserService;
import com.ptsisi.security.utils.PasswordUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;

@Controller
@RequestMapping("/security/my")
public class MyController extends AbstractController {

	@Resource
	protected UserService userService;

	@Override protected String getEntityName() {
		return User.class.getName();
	}

	@RequestMapping(value = "/edit_info")
	public void editInfo(ModelMap modelMap) {
		modelMap.put(getShortName(), userService.getLoginUser());
	}

	@RequestMapping(value = "/edit_password")
	public void editPassword(ModelMap modelMap) {
		modelMap.put(getShortName(), userService.getLoginUser());
	}

	@RequestMapping(value = "/edit_portrait")
	public void editPortrait(ModelMap modelMap) {
		modelMap.put(getShortName(), userService.getLoginUser());
	}

	@RequestMapping("password")
	@RequiresPermissions("my:password")
	public String password(User user) {
		User tempUser = user;
		user = entityDao.get(User.class, tempUser.getId());
		user.setPassword(tempUser.getPassword());
		PasswordUtil.populateEncryptInfo(user);
		try {
			userService.saveOrUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:list";
	}

	@RequestMapping("update")
	@RequiresPermissions("my:update")
	public String update(User user) {
		User tempUser = user;
		user = entityDao.get(User.class, tempUser.getId());
		user.setEmail(tempUser.getEmail());
		user.setFullName(tempUser.getFullName());
		user.setUsername(tempUser.getUsername());
		user.setEnabled(tempUser.isEnabled());
		try {
			userService.saveOrUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:list";
	}

	@Override
	@RequiresPermissions("my:delete")
	protected void doDelete(ModelAndView modelAndView, Collection<Entity> entities) {
	}

	@Override
	@RequiresPermissions("my:info")
	protected void infoSetting(ModelMap modelMap) {
		super.infoSetting(modelMap);
	}

	@RequestMapping("get-portrait")
	public ResponseEntity<byte[]> getPortrait() throws IOException {
		User user = userService.getLoginUser();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(userService.getPortrait(user), headers, HttpStatus.OK);
	}
}

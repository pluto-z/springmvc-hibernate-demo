package com.ptsisi.daily.web.controller.security;

import com.ptsisi.common.Entity;
import com.ptsisi.common.collection.Order;
import com.ptsisi.common.exception.ServiceException;
import com.ptsisi.daily.model.Role;
import com.ptsisi.daily.model.User;
import com.ptsisi.daily.web.controller.AbstractController;
import com.ptsisi.daily.web.service.UserService;
import com.ptsisi.security.utils.PasswordUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

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
	public String update(User user, @RequestParam(value = "role") Integer[] roleIds, ModelMap modelMap) {
		if (user.isPersisted()) {
			User tempUser = user;
			user = entityDao.get(User.class, tempUser.getId());
			user.setEmail(tempUser.getEmail());
			user.setFullName(tempUser.getFullName());
			user.setPassword(tempUser.getPassword());
			user.setUsername(tempUser.getUsername());
			user.setEnabled(tempUser.isEnabled());
		}
		PasswordUtil.populateEncryptInfo(user);
		user.getRoles().clear();
		if (ArrayUtils.isNotEmpty(roleIds)) {
			user.getRoles().addAll(entityDao.get(Role.class, roleIds));
		}
		try {
			userService.saveOrUpdate(user);
			flash(modelMap, "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			flash(modelMap, "保存失败");
		}
		return "redirect:list";
	}

	@Override
	@RequiresPermissions("user:edit")
	public void editSetting(ModelMap modelMap) {
		super.editSetting(modelMap);
		modelMap.put("roles", entityDao.getAll(Role.class));
	}

	@Override
	@RequiresPermissions("user:delete")
	protected void doDelete(ModelAndView modelAndView, Collection<Entity> entities) {
		super.doDelete(modelAndView, entities);
		userService.reload();
	}

	@Override
	@RequiresPermissions("user:info")
	protected void infoSetting(ModelMap modelMap) {
		super.infoSetting(modelMap);
	}

	@RequestMapping("json")
	@RequiresPermissions("user:list")
	@ResponseBody
	public Object json(Order order) {
		return entityDao.searchObj(getQueryBuilder());
	}

	@RequestMapping("temp-portrait")
	public ResponseEntity<byte[]> tempPortait(MultipartRequest request) throws IOException {
		MultipartFile file = request.getFile("avatar");
		if (file == null) throw new ServiceException("缺少头像图片");
		BufferedImage image = ImageIO
				.read(file.getInputStream());
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ImageIO.write(image, guessImageType(file.getContentType()), b);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(b.toByteArray(), headers, HttpStatus.OK);
	}

	private String guessImageType(String contentType) {
		if (StringUtils.containsIgnoreCase(contentType, "jpg") || StringUtils.containsIgnoreCase(contentType, "jpeg")) {
			return "JPG";
		} else if (StringUtils.containsIgnoreCase(contentType, "png")) {
			return "PNG";
		} else if (StringUtils.containsIgnoreCase(contentType, "gif")) {
			return "GIF";
		} else {
			return "PNG";
		}
	}

	@Override
	protected String getEntityName() {
		return User.class.getName();
	}
}

package com.ptsisi.daily.web.controller.common;

import com.ptsisi.daily.User;
import com.ptsisi.daily.model.UserBean;
import com.ptsisi.daily.web.service.UserService;
import com.ptsisi.security.utils.CaptchaProvider;
import com.ptsisi.security.utils.PasswordUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * Created by zhaoding on 14-10-27.
 */
@Controller
public class AccountController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected UserService userService;

	@RequestMapping({ "/login", "/" })
	public String login() {
		if (null == SecurityUtils.getSubject().getPrincipal()) {
			return "index";
		}
		return "redirect:/home";
	}

	@RequestMapping("/register")
	public String register(HttpSession session, UserBean user, String captcha, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("username", user.getUsername());
		redirectAttributes.addFlashAttribute("fullName", user.getFullName());
		if (!CaptchaProvider.getInstance().validateResponseForID(session.getId(), captcha)) {
			redirectAttributes.addFlashAttribute("registerFailured", "验证码错误");
		} else {
			PasswordUtil.populateEncryptInfo(user);
			User userExist = userService.getUserByAccount(user.getUsername());
			if (null != userExist) {
				redirectAttributes.addFlashAttribute("registerFail", "用户名已存在");
			} else {
				try {
					userService.saveOrUpdate(user);
					redirectAttributes.addFlashAttribute("registerSuccess", "注册成功");
				} catch (Exception e) {
					e.printStackTrace();
					redirectAttributes.addFlashAttribute("registerFail", "注册失败");
				}
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/get-captcha")
	public ResponseEntity<byte[]> captcha(HttpServletRequest request)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BufferedImage challenge = CaptchaProvider.getInstance()
				.getImageChallengeForID(request.getSession().getId(),
						request.getLocale());
		ImageIO.write(challenge, "jpeg", outputStream);
		byte[] captchaBytes = outputStream.toByteArray();
		outputStream.close();
		return new ResponseEntity<byte[]>(captchaBytes, headers, HttpStatus.OK);
	}
}

package com.ptsisi.daily.web.controller.common;

import com.ptsisi.daily.model.CustomPrincipal;
import com.ptsisi.daily.model.User;
import com.ptsisi.daily.web.service.UserService;
import com.ptsisi.security.UnautherizedException;
import com.ptsisi.security.utils.CaptchaProvider;
import com.ptsisi.security.utils.PasswordUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * Created by zhaoding on 14-10-27.
 */
@Controller
public class LoginController {

	@Resource
	protected UserService userService;

	@RequestMapping("/login")
	public String login() {
		try {
			CustomPrincipal principal = CustomPrincipal.getCurrentPrincipal();
		} catch (Exception e) {
			return "login";
		}
		return "redirect:/home";
	}

	@RequestMapping("/unauthroized")
	public String unauthroized() {
		throw new UnautherizedException("没有授权");
	}

	@RequestMapping("/register")
	public String register(HttpSession session, User user, String captcha, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("username", user.getUsername());
		redirectAttributes.addFlashAttribute("fullName", user.getFullName());
		if (!CaptchaProvider.getInstance().validateResponseForID(session.getId(), captcha)) {
			redirectAttributes.addFlashAttribute("registerFailured", "验证码错误");
		} else {
			User userExist = userService.getUserByAccount(user.getUsername());
			if (null != userExist) {
				redirectAttributes.addFlashAttribute("registerFail", "用户名已存在");
			} else {
				try {
					PasswordUtil.populateEncryptInfo(user);
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
	public ResponseEntity<byte[]> captcha(HttpServletRequest request) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BufferedImage challenge = CaptchaProvider.getInstance().getImageChallengeForID(request.getSession().getId(),
				request.getLocale());
		JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(outputStream);
		jpegEncoder.encode(challenge);
		byte[] captchaBytes = outputStream.toByteArray();
		outputStream.close();
		return new ResponseEntity<byte[]>(captchaBytes, headers, HttpStatus.OK);
	}
}

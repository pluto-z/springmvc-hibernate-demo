package com.ptsisi.daily.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ptsisi.daily.model.UserBean;
import com.ptsisi.security.utils.CaptchaProvider;

@Controller
public class LoginController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private final String LOGIN_FAILED = "LOGIN_FAILED";

	private final int NEED_CAPTCHA_LIMITS = 2;

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		Integer loginFailed = (Integer) session.getAttribute(LOGIN_FAILED);
		ModelAndView modelAndView = new ModelAndView("index");
		if (null != loginFailed && loginFailed >= NEED_CAPTCHA_LIMITS) {
			modelAndView.addObject("needCaptcha", true);
		}
		return modelAndView;
	}

	@RequestMapping("/login")
	public String login(UserBean current, boolean rememberMe, String captcha,
			HttpSession session, RedirectAttributes attr) {
		Integer loginFailed = (Integer) session.getAttribute(LOGIN_FAILED);
		String msg = "";
		if (loginFailed != null && loginFailed >= NEED_CAPTCHA_LIMITS) {
			if (StringUtils.isBlank(captcha)) {
				msg = "请填写验证码";
			} else if (!CaptchaProvider.getInstance().validateResponseForID(
					session.getId(), captcha)) {
				msg = "验证码错误";
			}
		}
		attr.addFlashAttribute("username", current.getUsername());
		if (StringUtils.isNoneBlank(msg)) {
			attr.addFlashAttribute("cmsg", msg);
			return "redirect:/index.action";
		}
		Subject user = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(
				current.getUsername(), current.getPassword());
		token.setRememberMe(rememberMe);
		try {
			user.login(token);
			return "forward:/user/index";
		} catch (AuthenticationException e) {
			loginFailed = (Integer) session.getAttribute(LOGIN_FAILED);
			if (null == loginFailed) {
				session.setAttribute(LOGIN_FAILED, 1);
			} else {
				session.setAttribute(LOGIN_FAILED, ++loginFailed);
			}
			token.clear();
			attr.addFlashAttribute("umsg", "用户名或密码错误");
			return "redirect:/index.action";
		}
	}

	@RequestMapping("/captcha")
	public void captcha(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String captchaId = request.getSession().getId();
		BufferedImage challenge = CaptchaProvider.getInstance()
				.getImageChallengeForID(captchaId, request.getLocale());
		ImageIO.write(challenge, "jpg", outputStream);
		// flush it in the response
		captchaChallengeAsJpeg = outputStream.toByteArray();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		out.write(captchaChallengeAsJpeg);
		out.flush();
		out.close();
	}
}

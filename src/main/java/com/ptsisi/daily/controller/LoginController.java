package com.ptsisi.daily.controller;

import com.ptsisi.daily.model.UserBean;
import com.ptsisi.security.utils.CaptchaProvider;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Controller
public class LoginController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		Integer loginFailed = (Integer) session.getAttribute("loginFailed");
		ModelAndView modelAndView = new ModelAndView("index");
		if (null != loginFailed && loginFailed >= 2) {
			modelAndView.addObject("needCaptcha", true);
		}
		return modelAndView;
	}

	@RequestMapping("/login")
	public String login(UserBean current, boolean rememberMe, String captcha, HttpSession session) {
		Subject user = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(current.getUsername(), current.getPassword());
		token.setRememberMe(rememberMe);
		try {
			user.login(token);
			return "redirect:/user/index";
		} catch (AuthenticationException e) {
			Integer loginFailed = (Integer) session.getAttribute("loginFailed");
			if (null == loginFailed) {
				session.setAttribute("loginFailed", 1);
			} else {
				session.setAttribute("loginFailed", ++loginFailed);
			}
			logger.error("登录失败错误信息:" + e);
			token.clear();
			return "redirect:/index.action";
		}
	}

	@RequestMapping("/captcha")
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String captchaId = request.getSession().getId();
		BufferedImage challenge =
				CaptchaProvider.getInstance().getImageChallengeForID(captchaId,
						request.getLocale());
		ImageIO.write(challenge, "jpg", outputStream);
		// flush it in the response
		captchaChallengeAsJpeg = outputStream.toByteArray();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream out =
				response.getOutputStream();
		out.write(captchaChallengeAsJpeg);
		out.flush();
		out.close();
	}
}

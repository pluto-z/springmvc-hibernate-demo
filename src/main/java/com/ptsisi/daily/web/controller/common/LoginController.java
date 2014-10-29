package com.ptsisi.daily.web.controller.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsisi.security.utils.CaptchaProvider;

/**
 * Created by zhaoding on 14-10-27.
 */
@Controller
public class LoginController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping({ "/login", "/" })
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		if (subject == null || !subject.isAuthenticated()) {
			return "index";
		}
		return "forward:/home";
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

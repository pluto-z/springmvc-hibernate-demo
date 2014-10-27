package com.ptsisi.security.utils;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * Created by zhaoding on 14-10-27.
 */
public class CaptchaProvider {

	private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService();

	public static ImageCaptchaService getInstance() {
		return instance;
	}
}

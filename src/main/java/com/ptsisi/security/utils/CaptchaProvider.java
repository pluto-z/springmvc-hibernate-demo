package com.ptsisi.security.utils;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * Created by zhaoding on 14-10-27.
 */
public class CaptchaProvider {

  private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService(new FastHashMapCaptchaStore(),
      new CustomCaptchaEngine(), 180, 100000, 75000);

  public static ImageCaptchaService getInstance() {
    return instance;
  }

}

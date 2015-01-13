package com.ptsisi.security.filter;

import com.ptsisi.security.AccountNotFoundException;
import com.ptsisi.security.CaptchaNotMatchException;
import com.ptsisi.security.utils.CaptchaProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaoding on 14-10-28.
 */
@Component
public class CaptchaAuthenticationFilter extends FormAuthenticationFilter {

	private static final String DEFAULT_LOGIN_INCORRECT_NUMBER_KEY_ATTRIBUTE = "incorrectNumber";

	private static final String DEFAULT_CAPTCHA_KEY_ATTRIBUTE = "captcha";

	private static final int ALLOW_INCORRECT_NUMBER = 2;

	private static final String NEED_CAPATCHA = "needCaptcha";

	private int allowIncorrectNumber = ALLOW_INCORRECT_NUMBER;

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		Session session = SecurityUtils.getSubject().getSession();
		Integer number = (Integer) session.getAttribute(DEFAULT_LOGIN_INCORRECT_NUMBER_KEY_ATTRIBUTE);
		if (number != null && number > this.allowIncorrectNumber) {
			String submitCaptcha = WebUtils.getCleanParam(request, DEFAULT_CAPTCHA_KEY_ATTRIBUTE);
			if (StringUtils.isEmpty(submitCaptcha)
					|| !CaptchaProvider.getInstance().validateResponseForID(httpServletRequest.getSession().getId(),
					submitCaptcha)) {
				return onLoginFailure(this.createToken(request, response), new CaptchaNotMatchException(), request, response);
			}
		}
		return super.executeLogin(request, response);
	}

	/**
	 * 重写父类方法，当登录失败将异常信息设置到 request 的 attribute 中
	 */
	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		if (ae instanceof CaptchaNotMatchException) {
			request.setAttribute(getFailureKeyAttribute(), "验证码不正确");
		} else if (ae instanceof IncorrectCredentialsException) {
			request.setAttribute(getFailureKeyAttribute(), "密码错误");
		} else if (ae instanceof DisabledAccountException) {
			request.setAttribute(getFailureKeyAttribute(), "你的账户已被禁用");
		} else if (ae instanceof AccountNotFoundException) {
			request.setAttribute(getFailureKeyAttribute(), "用户名不存在");
		} else {
			request.setAttribute(getFailureKeyAttribute(), "服务器出现异常");
		}
	}

	/**
	 * 重写父类方法，当登录失败后，将 allowIncorrectNumber（允许登错误录次） + 1
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		Session session = SecurityUtils.getSubject().getSession();
		Integer number = (Integer) session.getAttribute(DEFAULT_LOGIN_INCORRECT_NUMBER_KEY_ATTRIBUTE);
		if (number == null) number = 0;
		session.setAttribute(DEFAULT_LOGIN_INCORRECT_NUMBER_KEY_ATTRIBUTE, ++number);
		if (number >= this.allowIncorrectNumber) {
			session.setAttribute(NEED_CAPATCHA, true);
		}
		request.setAttribute("username", token.getPrincipal());
		return super.onLoginFailure(token, e, request, response);
	}

	/**
	 * 重写父类方法，当登录成功后，将 allowIncorrectNumber（允许登错误录次）设置为 0，重置下一次登录的状态
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		Session session = subject.getSession();
		session.removeAttribute(DEFAULT_LOGIN_INCORRECT_NUMBER_KEY_ATTRIBUTE);
		return super.onLoginSuccess(token, subject, request, response);
	}

	public void setAllowIncorrectNumber(int allowIncorrectNumber) {
		this.allowIncorrectNumber = allowIncorrectNumber;
	}
}

package com.ptsisi.spring;

import org.springframework.cache.interceptor.KeyGenerator;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by zhaoding on 15-1-9.
 */
public class DailyKeyGenerator implements KeyGenerator {

	@Override public Object generate(Object target, Method method, Object... params) {
		return new DailyKey(target, method, params);
	}

}


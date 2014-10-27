package com.ptsisi.hibernate.util;

import java.lang.reflect.ParameterizedType;

/**
 * Created by zhaoding on 14-10-27.
 */
public class GenericUtils {

	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenericType(Class<?> clazz) {
		return ((ParameterizedType) clazz.getGenericSuperclass())
				.getActualTypeArguments()[0].getClass();
	}
}

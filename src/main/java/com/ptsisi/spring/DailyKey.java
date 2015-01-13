package com.ptsisi.spring;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by zhaoding on 15-1-9.
 */
public class DailyKey implements Serializable {

	private Object target;
	private Method method;
	private final Object[] params;
	private final int hashCode;

	public DailyKey(Object target, Method method, Object... elements) {
		Assert.notNull(elements, "Elements must not be null");
		this.method = method;
		this.target = target;
		this.params = new Object[elements.length];
		System.arraycopy(elements, 0, this.params, 0, elements.length);
		this.hashCode = Arrays.deepHashCode(this.params);
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object[] getParams() {
		return params;
	}

	public int getHashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj || (obj instanceof DailyKey
				&& Arrays.deepEquals(this.params, ((DailyKey) obj).params))) && this.target.equals(((DailyKey) obj).getTarget())
				&& this.method.equals(((DailyKey) obj).getMethod());
	}

	@Override
	public final int hashCode() {
		return hashCode;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + StringUtils
				.arrayToCommaDelimitedString(
						new Object[] { this.getTarget().getClass().getName(), this.getMethod().getName(), this.params })
				+ "]";
	}
}

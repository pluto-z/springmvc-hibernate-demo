package com.ptsisi.ui.component.tags;

import com.google.common.collect.Maps;
import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class Form extends UIBean {

	private String validateOutputMode = "tooltip";
	protected String name;
	protected String action;
	protected String target;
	protected String onsubmit;
	protected boolean validate = false;
	private String title;
	private Map<String, StringBuilder> elementChecks = Maps.newHashMap();
	private StringBuilder extraChecks;

	public Form(ComponentContext context) {
		super(context);
	}

	@Override
	protected void evaluateParams() {
		if (null == id) {
			if (null == name) {
				generateIdIfEmpty();
				name = id;
			} else {
				id = name;
			}
		}
		action = render(action);
		if (null != title) title = getText(title);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOnsubmit() {
		return onsubmit;
	}

	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}

	public boolean isValidate() {
		validate = !elementChecks.isEmpty();
		return validate;
	}

	public void addRequire(String id) {
		this.addCheck(id, "require().match('notBlank')");
	}

	public void addCheck(String id, String check) {
		StringBuilder sb = elementChecks.get(id);
		if (null == sb) {
			sb = new StringBuilder(100);
			elementChecks.put(id, sb);
		}
		sb.append('.').append(check);
	}

	public void addCheck(String check) {
		if (null == extraChecks) extraChecks = new StringBuilder();
		extraChecks.append(check);
	}

	public String getValidity() {
		StringBuilder sb = new StringBuilder((elementChecks.size() * 80)
				+ ((null == extraChecks) ? 0 : extraChecks.length()));
		for (Map.Entry<String, StringBuilder> check : elementChecks.entrySet()) {
			sb.append("$('#").append(StringUtils.replace(check.getKey(), ".", "\\\\.")).append("')").append(check.getValue())
					.append(";\n");
		}
		if (null != extraChecks) sb.append(extraChecks);
		return sb.toString();
	}

	public String getValidateOutputMode() {
		return validateOutputMode;
	}

	public void setValidateOutputMode(String validateOutputMode) {
		this.validateOutputMode = validateOutputMode;
	}

	public static class FormHead extends UIBean {

		public FormHead(ComponentContext context) {
			super(context);
		}

	}

	public static class FormBody extends UIBean {

		public FormBody(ComponentContext context) {
			super(context);
		}

	}

	public static class FormFoot extends UIBean {

		public FormFoot(ComponentContext context) {
			super(context);
		}

	}
}

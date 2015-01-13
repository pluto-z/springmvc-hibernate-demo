package com.ptsisi.ui.component.tags;

import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

/**
 * Created by zhaoding on 15-1-22.
 */
public class Field extends UIBean {

	private String label;

	private String required;

	public Field(ComponentContext context) {
		super(context);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}
}

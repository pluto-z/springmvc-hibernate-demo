package com.ptsisi.ui.component.tags;

import com.ptsisi.daily.web.controller.AbstractController;
import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;

/**
 * Created by zhaoding on 15-2-10.
 */
public class Flash extends UIBean {

	private String flash;

	public Flash(ComponentContext context) {
		super(context);
	}

	@Override protected void evaluateParams() {
		super.evaluateParams();
		if (null == this.id) generateIdIfEmpty();
		setFlash(getRequestParameter(AbstractController.FLASH_MSG));
	}

	public String getFlash() {
		return flash;
	}

	public void setFlash(String flash) {
		this.flash = flash;
	}
}

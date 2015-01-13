package com.ptsisi.daily;

import com.ptsisi.daily.model.Menu;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zhaoding on 14-10-31.
 */
public class HierarchyTest {

	private Menu subChild;

	@Before
	public void prepare() {
		Menu parent = new Menu();
		parent.setIndexNo(1);
		Menu subMenu1 = setSubMenu(1, parent);
		setSubMenu(2, parent);
		setSubMenu(1, subMenu1);
		this.subChild = setSubMenu(2, subMenu1);
	}

	private Menu setSubMenu(int indexNo, Menu parent) {
		Menu child = new Menu();
		child.setParent(parent);
		child.setIndexNo(indexNo);
		parent.getChildren().add(child);
		return child;
	}

	@Test
	public void test() {
		Assert.assertEquals(subChild.getIndex(), "1.1.2");
		Assert.assertEquals(subChild.getParent().getChildren().get(1), subChild);
		Assert.assertEquals(subChild.getParent().getChildren().get(0).getIndex(), "1.1.1");
	}
}

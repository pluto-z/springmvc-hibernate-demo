package com.ptsisi.daily.model;

import com.ptsisi.common.model.IntegerIdTimeObject;
import com.ptsisi.daily.Resource;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zhaoding on 14-10-28.
 */
@Entity(name="com.ptsisi.daily.Menu")
@Table(name = "SYS_MENUS")
public class MenuBean extends IntegerIdTimeObject implements com.ptsisi.daily.Menu {

	@NotBlank
	@Length(max = 50)
	private String name;

	@NaturalId
	private int indexNo;

	@NaturalId
	@ManyToOne(targetEntity = MenuBean.class, fetch = FetchType.LAZY)
	private MenuBean parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@OrderBy("indexNo")
	private List<MenuBean> children;

	@ManyToOne(targetEntity = ResourceBean.class, fetch = FetchType.LAZY)
	private Resource resource;

	private boolean enabled;

	@Override public String getName() {
		return name;
	}

	@Override public void setName(String name) {
		this.name = name;
	}

	@Override public int getIndexNo() {
		return indexNo;
	}

	@Override public void setIndexNo(int indexNo) {
		this.indexNo = indexNo;
	}

	@Override public MenuBean getParent() {
		return parent;
	}

	@Override public void setParent(MenuBean parent) {
		this.parent = parent;
	}

	@Override public List<MenuBean> getChildren() {
		return children;
	}

	@Override public void setChildren(List<MenuBean> children) {
		this.children = children;
	}

	@Override public Resource getResource() {
		return resource;
	}

	@Override public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override public boolean isEnabled() {
		return enabled;
	}

	@Override public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

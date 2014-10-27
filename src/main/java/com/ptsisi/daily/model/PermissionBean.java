package com.ptsisi.daily.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.ptsisi.daily.Permission;
import com.ptsisi.daily.model.base.IntegerIdObject;

@Entity(name = "com.ptsisi.daily.Permission")
@Table(name = "sys_permissions")
public class PermissionBean extends IntegerIdObject implements Permission {

	private static final long serialVersionUID = 2436223935048921152L;

	@ManyToOne(targetEntity = PermissionBean.class, fetch = FetchType.LAZY)
	private Permission parent;

	private boolean enabled;

	@NotBlank
	@Length(max = 50)
	private String name;

	@Length(max = 50)
	private String permission;

	@OneToMany(targetEntity = PermissionBean.class, mappedBy = "parent", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Set<Permission> children = new HashSet<Permission>();

	@Override
	public Permission getParent() {
		return parent;
	}

	@Override
	public void setParent(Permission parent) {
		this.parent = parent;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getPermission() {
		return permission;
	}

	@Override
	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public Set<Permission> getChildren() {
		return children;
	}

	@Override
	public void setChildren(Set<Permission> children) {
		this.children = children;
	}

}

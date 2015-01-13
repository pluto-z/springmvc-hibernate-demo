package com.ptsisi.daily.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.ptsisi.common.entity.pojo.IntegerIdHierarchyObject;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

/**
 * Created by zhaoding on 14-10-28.
 */
@Entity
@Table(name = "SYS_MENUS")
public class Menu extends IntegerIdHierarchyObject<Menu> {

	private static final long serialVersionUID = -3188684524932415584L;

	@NotBlank
	@Length(max = 50)
	private String name;

	private String entry;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_MENUS_RESOURCES", joinColumns = {
			@JoinColumn(name = "MENU_ID", updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "RESOURCE_ID", updatable = false) })
	private Set<Resource> resources = Sets.newHashSet();

	private boolean enabled;

	private String icon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public Menu clone(){
		Menu menu = new Menu();
		menu.setId(this.getId());
		menu.setEntry(this.entry);
		menu.setIcon(this.icon);
		menu.setName(this.name);
		menu.setIndexNo(this.getIndexNo());
		menu.setParent(this.getParent());
		return menu;
	}

	public void sort() {
		Collections.sort(getChildren());
	}
}

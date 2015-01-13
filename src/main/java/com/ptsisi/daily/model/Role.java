package com.ptsisi.daily.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.ptsisi.common.entity.pojo.IntegerIdObject;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SYS_ROLES")
public class Role extends IntegerIdObject {

	private static final long serialVersionUID = 5227820401058873706L;

	public static final Integer ADMIN = 1;
	public static final Integer USER = 2;

	@NotBlank
	@Length(max = 32)
	@Column(unique = true)
	private String name;

	@NotBlank
	@Length(max = 50)
	private String description;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<User> users = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_ROLES_RESOURCES", joinColumns = {
			@JoinColumn(name = "ROLE_ID", updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "RESOURCE_ID", updatable = false) })
	private Set<Resource> resources = Sets.newHashSet();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@JsonIgnore
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
}

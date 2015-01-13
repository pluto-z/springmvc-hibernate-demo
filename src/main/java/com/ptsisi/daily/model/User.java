package com.ptsisi.daily.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Sets;
import com.ptsisi.common.entity.pojo.IntegerIdTimeObject;
import com.ptsisi.common.serializer.BooleanSerializer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.Set;

@Entity
@Table(name = "SYS_USERS")
public class User extends IntegerIdTimeObject {

	private static final long serialVersionUID = -3737262583650533418L;

	@NotBlank
	@Length(max = 32)
	@Column(unique = true)
	private String username;

	@NotBlank
	@JsonIgnore
	private String salt;

	@NotBlank
	@Length(max = 100)
	@JsonIgnore
	private String password;

	@NotBlank
	@Length(max = 100)
	private String fullName;

	@Email
	@Length(max = 50)
	@NotNull
	private String email;

	@Lob
	@JsonIgnore
	private Blob portrait;

	@JsonSerialize(using = BooleanSerializer.class)
	private boolean enabled = true;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_USERS_ROLES", joinColumns = {
			@JoinColumn(name = "USER_ID", updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID", updatable = false) })
	private Set<Role> roles = Sets.newHashSet();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Blob getPortrait() {
		return portrait;
	}

	public void setPortrait(Blob portrait) {
		this.portrait = portrait;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	public Set<Resource> getResources() {
		Set<Resource> resources = Sets.newHashSet();
		for (Role role : this.roles) {
			resources.addAll(role.getResources());
		}
		return resources;
	}
}

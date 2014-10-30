package com.ptsisi.daily.model;

import java.sql.Blob;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Sets;
import com.ptsisi.common.model.IntegerIdTimeObject;
import com.ptsisi.daily.Role;
import com.ptsisi.daily.User;

@Entity(name = "com.ptsisi.daily.User")
@Table(name = "SYS_USERS")
public class UserBean extends IntegerIdTimeObject implements User {

	private static final long serialVersionUID = -3737262583650533418L;

	@NotBlank
	@Length(max = 32)
	@Column(unique = true)
	private String username;

	@NotBlank
	private String salt;

	@NotBlank
	@Length(max = 100)
	private String password;
	@NotBlank
	@Length(max = 100)
	private String fullName;
	@Email
	@Length(max = 50)
	@NotNull
	private String email;
	@Lob
	private Blob avatar;

	private boolean enabled = true;

	@ManyToMany(targetEntity = RoleBean.class, mappedBy = "users", fetch = FetchType.LAZY)
	private Set<Role> roles = Sets.newHashSet();

	@Override public String getUsername() {
		return username;
	}

	@Override public void setUsername(String username) {
		this.username = username;
	}

	@Override public String getSalt() {
		return salt;
	}

	@Override public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override public String getPassword() {
		return password;
	}

	@Override public void setPassword(String password) {
		this.password = password;
	}

	@Override public String getFullName() {
		return fullName;
	}

	@Override public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override public String getEmail() {
		return email;
	}

	@Override public void setEmail(String email) {
		this.email = email;
	}

	@Override public Blob getAvatar() {
		return avatar;
	}

	@Override public void setAvatar(Blob avatar) {
		this.avatar = avatar;
	}

	@Override public boolean isEnabled() {
		return enabled;
	}

	@Override public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override public Set<Role> getRoles() {
		return roles;
	}

	@Override public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}

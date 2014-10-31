package com.ptsisi.daily.model;

import com.google.common.collect.Sets;
import com.ptsisi.common.model.IntegerIdTimeObject;
import com.ptsisi.daily.Role;
import com.ptsisi.daily.User;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.Set;

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
	private Blob portrait;

	private boolean enabled = true;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_USERS_ROLES", joinColumns = { @JoinColumn(name = "USER_ID", updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", updatable = false) })
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

	@Override public Blob getPortrait() {
		return portrait;
	}

	@Override public void setPortrait(Blob portrait) {
		this.portrait = portrait;
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

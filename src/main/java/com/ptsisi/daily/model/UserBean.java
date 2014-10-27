package com.ptsisi.daily.model;

import com.ptsisi.daily.Role;
import com.ptsisi.daily.User;
import com.ptsisi.daily.model.base.IntegerIdTimeObject;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "org.ptsisi.keyword.User")
@Table(name = "sys_users")
public class UserBean extends IntegerIdTimeObject implements User {

	private static final long serialVersionUID = -3737262583650533418L;

	@NotBlank
	@Length(max = 32)
	@Column(unique = true)
	private String username;
	@NotBlank
	@Length(max = 16, min = 6)
	private String password;
	@NotBlank
	@Length(max = 100)
	private String fullname;
	@Email
	@Length(max = 50)
	@NotNull
	private String email;

	private boolean enabled;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = RoleBean.class)
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ptsisi.daily.model.User#getUsername()
	 */
	public String getUsername() {
		return username;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ptsisi.daily.model.User#setUsername(java.lang.String)
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ptsisi.daily.model.User#getPassword()
	 */
	public String getPassword() {
		return password;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ptsisi.daily.model.User#setPassword(java.lang.String)
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ptsisi.daily.model.User#getFullname()
	 */
	public String getFullname() {
		return fullname;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ptsisi.daily.model.User#setFullname(java.lang.String)
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ptsisi.daily.model.User#getEmail()
	 */
	public String getEmail() {
		return email;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ptsisi.daily.model.User#setEmail(java.lang.String)
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}

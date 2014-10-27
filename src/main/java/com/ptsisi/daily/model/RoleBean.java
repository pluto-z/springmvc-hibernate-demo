package com.ptsisi.daily.model;

import com.ptsisi.daily.Permission;
import com.ptsisi.daily.Role;
import com.ptsisi.daily.User;
import com.ptsisi.daily.model.base.IntegerIdTimeObject;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "org.ptsisi.daily.Role")
@Table(name = "sys_roles")
public class RoleBean extends IntegerIdTimeObject implements Role {

	@NotBlank
	@Length(max = 32)
	@Column(unique = true)
	private String name;

	private boolean enabled;

	@OneToMany(targetEntity = UserBean.class, cascade = { CascadeType.ALL }, mappedBy = "role")
	private Set<User> users = new HashSet<User>();

	@ManyToMany(targetEntity = PermissionBean.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "sys_roles_permissions",
			joinColumns = { @JoinColumn(name = "role_id", updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "permission_id", updatable = false) })
	private Set<Permission> permissions = new HashSet<Permission>();

	@Override public String getName() {
		return name;
	}

	@Override public void setName(String name) {
		this.name = name;
	}

	@Override public boolean isEnabled() {
		return enabled;
	}

	@Override public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override public Set<User> getUsers() {
		return users;
	}

	@Override public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
}

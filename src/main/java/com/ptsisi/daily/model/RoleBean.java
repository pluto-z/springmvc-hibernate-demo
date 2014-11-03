package com.ptsisi.daily.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Sets;
import com.ptsisi.common.entity.pojo.IntegerIdObject;
import com.ptsisi.daily.Resource;
import com.ptsisi.daily.Role;
import com.ptsisi.daily.User;

@Entity(name = "com.ptsisi.daily.Role")
@Table(name = "SYS_ROLES")
public class RoleBean extends IntegerIdObject implements Role {

  private static final long serialVersionUID = 5227820401058873706L;

  @NotBlank
  @Length(max = 32)
  @Column(unique = true)
  private String name;

  @NotBlank
  @Length(max = 50)
  private String description;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  private Set<User> users = Sets.newHashSet();

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "SYS_ROLES_RESOURCES", joinColumns = { @JoinColumn(name = "ROLE_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "RESOURCE_ID", updatable = false) })
  private Set<Resource> resources = Sets.newHashSet();

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public Set<User> getUsers() {
    return users;
  }

  @Override
  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @Override
  public Set<Resource> getResources() {
    return resources;
  }

  @Override
  public void setResources(Set<Resource> resources) {
    this.resources = resources;
  }
}

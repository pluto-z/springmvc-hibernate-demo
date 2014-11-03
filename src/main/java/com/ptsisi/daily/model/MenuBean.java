package com.ptsisi.daily.model;

import com.google.common.collect.Sets;
import com.ptsisi.common.entity.pojo.IntegerIdHierarchyObject;
import com.ptsisi.daily.Menu;
import com.ptsisi.daily.Resource;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

import java.util.Set;

/**
 * Created by zhaoding on 14-10-28.
 */
@Entity(name = "com.ptsisi.daily.Menu")
@Table(name = "SYS_MENUS")
public class MenuBean extends IntegerIdHierarchyObject<Menu> implements Menu {

  private static final long serialVersionUID = -3188684524932415584L;

  @NotBlank
  @Length(max = 50)
  private String name;

  private String entry;

  @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
  @JoinTable(name = "SYS_MENUS_RESOURCES", joinColumns = { @JoinColumn(name = "MENU_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "RESOURCE_ID", updatable = false) })
  private Set<Resource> resources = Sets.newHashSet();

  private boolean enabled;

  private String icon;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getEntry() {
    return entry;
  }

  @Override
  public void setEntry(String entry) {
    this.entry = entry;
  }

  @Override
  public Set<Resource> getResources() {
    return resources;
  }

  @Override
  public void setResources(Set<Resource> resources) {
    this.resources = resources;
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
  public String getIcon() {
    return icon;
  }

  @Override
  public void setIcon(String icon) {
    this.icon = icon;
  }
}

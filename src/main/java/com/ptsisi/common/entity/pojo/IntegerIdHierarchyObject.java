package com.ptsisi.common.entity.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import com.google.common.collect.Lists;
import com.ptsisi.common.HierarchyEntity;

/**
 * Created by zhaoding on 14-10-31.
 */
@MappedSuperclass
public class IntegerIdHierarchyObject<T> extends IntegerIdObject implements Comparable<T>, HierarchyEntity<T> {

  private static final long serialVersionUID = 6743607478435016957L;

  @NaturalId
  private int indexNo;

  @NaturalId
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private T parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  @OrderBy("indexNo")
  private List<T> children = Lists.newArrayList();

  @Override
  public int getIndexNo() {
    return indexNo;
  }

  @Override
  public void setIndexNo(int indexNo) {
    this.indexNo = indexNo;
  }

  @Override
  public T getParent() {
    return parent;
  }

  @Override
  public void setParent(T parent) {
    this.parent = parent;
  }

  @Override
  public List<T> getChildren() {
    return children;
  }

  @Override
  public void setChildren(List<T> children) {
    this.children = children;
  }

  @Override
  public String getIndex() {
    if (null == this.getParentNode()) {
      return String.valueOf(this.indexNo);
    } else {
      return getIndex(this.getParentNode()) + "." + String.valueOf(this.indexNo);
    }
  }

  private String getIndex(IntegerIdHierarchyObject<T> object) {
    if (null == object.getParentNode()) {
      return String.valueOf(object.indexNo);
    } else {
      return getIndex(object.getParentNode()) + "." + String.valueOf(object.indexNo);
    }
  }

  @SuppressWarnings("unchecked")
  protected IntegerIdHierarchyObject<T> getParentNode() {
    return (IntegerIdHierarchyObject<T>) getParent();
  }

  @SuppressWarnings("unchecked")
  @Override
  public int compareTo(T other) {
    return getIndex().compareTo(((HierarchyEntity<T>) other).getIndex());
  }

}

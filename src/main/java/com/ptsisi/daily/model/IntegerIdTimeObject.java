package com.ptsisi.daily.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.ptsisi.daily.Entity;
import com.ptsisi.daily.TimeEntity;

@MappedSuperclass
public abstract class IntegerIdTimeObject extends IntegerIdObject implements Entity, TimeEntity {

  private static final long serialVersionUID = -3494716518594445433L;

  @Column(name = "created_at", nullable = false)
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  private Date updatedAt;

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}

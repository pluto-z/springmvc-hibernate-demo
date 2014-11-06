package com.ptsisi.common.entity.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptsisi.common.TimeEntity;

@MappedSuperclass
public abstract class IntegerIdTimeObject extends IntegerIdObject implements TimeEntity {

  private static final long serialVersionUID = -3494716518594445433L;

  @NotNull
  @Column(name = "created_at", nullable = false)
  @JsonFormat(pattern="yyyy-MM-dd HH:mm")  
  private Date createdAt;

  @NotNull
  @Column(name = "updated_at", nullable = false)
  @JsonFormat(pattern="yyyy-MM-dd HH:mm")  
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

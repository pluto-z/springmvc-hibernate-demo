package com.ptsisi.daily;

import java.util.Date;

public interface TimeEntity {

  public Date getCreatedAt();

  public void setCreatedAt(Date createdAt);

  public Date getUpdatedAt();

  public void setUpdatedAt(Date updatedAt);
}

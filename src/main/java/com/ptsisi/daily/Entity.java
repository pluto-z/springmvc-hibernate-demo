package com.ptsisi.daily;

import java.io.Serializable;

public interface Entity extends Serializable{

  public Integer getId();

  public void setId(Integer id);

  boolean isPersisted();

  boolean isTransient();

}

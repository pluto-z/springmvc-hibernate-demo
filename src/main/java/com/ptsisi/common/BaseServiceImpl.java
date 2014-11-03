package com.ptsisi.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ptsisi.common.dao.EntityDao;

public abstract class BaseServiceImpl {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  protected EntityDao entityDao;
}

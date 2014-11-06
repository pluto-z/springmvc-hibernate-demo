package com.ptsisi.common;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ptsisi.common.dao.EntityDao;

public abstract class BaseServiceImpl {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Resource
  protected EntityDao entityDao;
}

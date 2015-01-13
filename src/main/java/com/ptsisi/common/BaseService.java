package com.ptsisi.common;

import com.ptsisi.common.dao.EntityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public abstract class BaseService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	protected EntityDao entityDao;

}

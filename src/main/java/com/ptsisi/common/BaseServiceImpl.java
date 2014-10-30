package com.ptsisi.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.ptsisi.hibernate.EntityDao;

public abstract class BaseServiceImpl {

	@Autowired
	protected EntityDao entityDao;
}

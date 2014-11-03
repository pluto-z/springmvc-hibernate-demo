package com.ptsisi.daily.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ptsisi.common.Entity;
import com.ptsisi.common.collection.page.PageLimit;
import com.ptsisi.common.dao.EntityDao;
import com.ptsisi.common.entity.util.EntityUtils;
import com.ptsisi.common.query.builder.OqlBuilder;

public abstract class AbstractController {

  @Autowired
  protected EntityDao entityDao;

  protected abstract String getEntityName();

  protected String getShortName() {
    String name = getEntityName();
    if (StringUtils.isNotEmpty(name)) return EntityUtils.getCommandName(name);
    else return null;
  }

  protected <T extends Entity> OqlBuilder<T> getQueryBuilder(String orderBy, PageLimit pageLimit) {
    if (pageLimit == null) {
      pageLimit = new PageLimit(1, 20);
    }
    if (StringUtils.isEmpty(orderBy)) {
      orderBy = getShortName() + ".id";
    }
    OqlBuilder<T> builder = OqlBuilder.from(getEntityName(), getShortName());
    builder.orderBy(orderBy).limit(pageLimit);
    return builder;
  }
}

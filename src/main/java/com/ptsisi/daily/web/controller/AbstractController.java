package com.ptsisi.daily.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ptsisi.common.Entity;
import com.ptsisi.common.collection.Order;
import com.ptsisi.common.collection.page.PageLimit;
import com.ptsisi.common.dao.EntityDao;
import com.ptsisi.common.entity.util.EntityUtils;
import com.ptsisi.common.query.builder.OqlBuilder;
import org.springframework.web.servlet.mvc.LastModified;

public abstract class AbstractController implements LastModified{

  @Resource
  protected EntityDao entityDao;

  protected abstract String getEntityName();

  protected String getShortName() {
    String name = getEntityName();
    if (StringUtils.isNotEmpty(name)) return EntityUtils.getCommandName(name);
    else return null;
  }

  protected <T extends Entity> OqlBuilder<T> getQueryBuilder() {
    OqlBuilder<T> builder = OqlBuilder.from(getEntityName(), getShortName());
    Object server = getRequest().getParameter("server");
    if (server != null && Boolean.TRUE.toString().equals(server)) {
      builder.orderBy(populateOrder()).limit(populatePageLimit());
    }
    builder.orderBy(getShortName() + ".id");
    return builder;
  }

  protected PageLimit populatePageLimit() {
    HttpServletRequest request = getRequest();
    Object limit = request.getParameter("limit");
    Object offset = request.getParameter("offset");
    if (null != limit && null != offset) {
      Integer limitCount = Integer.valueOf(limit.toString());
      Integer offsetCount = Integer.valueOf(offset.toString());
      return new PageLimit(offsetCount / limitCount + 1, limitCount);
    }
    return null;
  }

  protected Order populateOrder() {
    HttpServletRequest request = getRequest();
    Object sortBy = request.getParameter(Order.ORDER_STR);
    if (null == sortBy) { return null; }
    String sortByStr = sortBy.toString();
    Object order = request.getParameter(Order.ORDER_ASC);
    String orderStr = "asc";
    if (null != order) {
      orderStr = order.toString();
    }
    return Order.parse(sortByStr, orderStr);
  }

  protected HttpServletRequest getRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }

  protected <T extends Entity> OqlBuilder<T> getQueryBuilder(String orderBy, PageLimit pageLimit) {
    if (pageLimit == null) {
      pageLimit = new PageLimit(1, 20);
    }
    if (StringUtils.isEmpty(orderBy)) {
      orderBy = getShortName() + ".id";
    }
    OqlBuilder<T> builder = getQueryBuilder();
    return builder.orderBy(orderBy).limit(pageLimit);
  }

  @Override public long getLastModified(HttpServletRequest request) {

    return 0;
  }
}

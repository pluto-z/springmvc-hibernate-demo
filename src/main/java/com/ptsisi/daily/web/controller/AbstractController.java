package com.ptsisi.daily.web.controller;

import com.google.common.collect.Maps;
import com.ptsisi.common.Entity;
import com.ptsisi.common.collection.Order;
import com.ptsisi.common.collection.page.PageLimit;
import com.ptsisi.common.dao.EntityDao;
import com.ptsisi.common.entity.pojo.IntegerIdTimeObject;
import com.ptsisi.common.entity.util.EntityUtils;
import com.ptsisi.common.metadata.EntityType;
import com.ptsisi.common.metadata.Model;
import com.ptsisi.common.query.builder.OqlBuilder;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.LastModified;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public abstract class AbstractController implements LastModified {

	public static final String FLASH_MSG = "FLASHMSG";

	@Resource
	protected ConversionService conversionService;

	@Resource
	protected EntityDao entityDao;

	protected abstract String getEntityName();

	protected void flash(ModelMap modelMap, String msg) {
		modelMap.addAttribute(FLASH_MSG, msg);
	}

	protected Entity getEntity() {
		Object value = getParam(getShortName() + ".id");
		if (null == value) value = getParam(getShortName() + "Id");
		if (null == value) return null;
		Integer entityId = null;
		try {
			entityId = Integer.valueOf(value.toString());
		} catch (Exception e) {
			return null;
		}
		return entityDao.get(getEntityName(), entityId);
	}

	protected Collection<Entity> getEntities() {
		Integer[] ids = getIntArray("entity.ids");
		if (ArrayUtils.isEmpty(ids))
			ids = getIntArray("entityIds");
		if (ArrayUtils.isEmpty(ids)) return null;
		return entityDao.get(getEntityName(), "id", ids);
	}

	public Entity populateEntity() {
		Entity entity = getEntity();
		if (null == entity) {
			EntityType type = Model.getType(getEntityName());
			entity = (Entity) type.newInstance();
		}
		Map<String, Object> params = sub(getRequest(), getShortName());
		try {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				populateEntity(entity, entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	private void populateEntity(Entity entity, String attr, Object value) throws Exception {
		if (attr.indexOf(".") > -1) {
			String subEntityName = attr.substring(0, attr.indexOf("."));
			Object subEntity = PropertyUtils.getProperty(entity, subEntityName);
			if (null == subEntity) {
				subEntity = PropertyUtils.getPropertyType(entity, subEntityName).newInstance();
			}
			populateEntity((Entity) subEntity, attr.substring(attr.indexOf(".") + 1), value);
			PropertyUtils.setProperty(entity, subEntityName, subEntity);
		} else {
			Class<?> type = PropertyUtils.getPropertyType(entity, attr);
			PropertyUtils.setProperty(entity, attr,
					conversionService.convert(value, type));
		}
	}

	private Enumeration<String> getParamNames() {
		return getRequest().getParameterNames();
	}

	private Map<String, Object> sub(HttpServletRequest request, String prefix) {
		Enumeration<String> paramterNames = getParamNames();
		Map<String, Object> newParams = Maps.newHashMap();
		while (paramterNames.hasMoreElements()) {
			String name = paramterNames.nextElement();
			if (name.indexOf(prefix + ".") == 0) {
				newParams.put(name.substring(prefix.length() + 1), getParam(name));
			}
		}
		return newParams;
	}

	private Object getParam(String name) {
		return getRequest().getParameter(name);
	}

	private String[] getParamValues(String name) {
		return getRequest().getParameterValues(name);
	}

	public String get(String name) {
		return getOrDef(name, null);
	}

	public Integer getInt(String name) {
		Object val = getParam(name);
		if (val == null) return null;
		return conversionService.convert(val, Integer.class);
	}

	public Boolean getBoolean(String name) {
		Object val = getParam(name);
		if (val == null) return null;
		return conversionService.convert(val, Boolean.class);
	}

	public Float getFloat(String name) {
		Object val = getParam(name);
		if (val == null) return null;
		return conversionService.convert(val, Float.class);
	}

	public Long getLong(String name) {
		Object val = getParam(name);
		if (val == null) return null;
		return conversionService.convert(val, Long.class);
	}

	public Integer[] getIntArray(String name) {
		String[] arr = getStringArray(name);
		if (null == arr) return null;
		return (Integer[]) conversionService
				.convert(arr, TypeDescriptor.array(TypeDescriptor.valueOf(String.class)), TypeDescriptor.array(
						TypeDescriptor.valueOf(Integer.class)));
	}

	public String[] getStringArray(String name) {
		String[] arr = getParamValues(name);
		if (ArrayUtils.isEmpty(arr)) return null;
		String[] tempArr = new String[] { };
		for (String s : arr) {
			if (s.indexOf(",") > -1) {
				tempArr = ArrayUtils.addAll(tempArr, s.split(","));
			} else {
				tempArr = ArrayUtils.add(tempArr, s);
			}
		}
		return tempArr;
	}

	public String getOrDef(String name, String def) {
		Object val = getParam(name);
		if (val == null) return def;
		return conversionService.convert(val, String.class);
	}

	protected void saveAndUpdate(ModelAndView modelAndView, Entity entity) {
		Date date = new Date();
		if (entity instanceof IntegerIdTimeObject) {
			if (entity.isTransient()) ((IntegerIdTimeObject) entity).setCreatedAt(date);
			((IntegerIdTimeObject) entity).setUpdatedAt(date);
		}
		try {
			entityDao.saveOrUpdate(Collections.singletonList(entity));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("delete")
	public ModelAndView delete(ModelAndView modelAndView) {
		Collection<Entity> entities = getEntities();
		if (null == entities) throw new RuntimeException(getShortName() + ".ids or " + getShortName() + "Ids is required!");
		modelAndView.setViewName("redirect:list");
		doDelete(modelAndView, entities);
		return modelAndView;
	}

	protected void doDelete(ModelAndView modelAndView, Collection<Entity> entities) {
		try {
			entityDao.remove(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/add")
	public String add(ModelMap modelMap) {
		editSetting(modelMap);
		return "edit";
	}

	@RequestMapping(value = "/edit/{entityId}")
	public void edit(@PathVariable(value = "entityId") int entityId, ModelMap modelMap) {
		modelMap.put(getShortName(), entityDao.get(getEntityName(), entityId));
		editSetting(modelMap);
	}

	protected void editSetting(ModelMap modelMap) {
	}

	@RequestMapping(value = "/info/{entityId}")
	public void info(@PathVariable(value = "entityId") int entityId, ModelMap modelMap) {
		modelMap.put(getShortName(), entityDao.get(getEntityName(), entityId));
		infoSetting(modelMap);
	}

	protected void infoSetting(ModelMap modelMap) {
	}

	protected String getShortName() {
		String name = getEntityName();
		if (StringUtils.isNotEmpty(name)) return EntityUtils.getCommandName(name);
		else return null;
	}

	protected <T extends Entity> OqlBuilder<T> getQueryBuilder() {
		OqlBuilder<T> builder = OqlBuilder.from(getEntityName(), getShortName());
		Object server = getParam("server");
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
		if (null == sortBy) {
			return null;
		}
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

	public static void main(String[] args) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(1422233838576l);
		System.out.println(calendar.getTime());
		calendar.setTimeInMillis(1422233847137l);
		System.out.println(calendar.getTime());
	}

	@Override public long getLastModified(HttpServletRequest request) {
		return 0;
	}
}

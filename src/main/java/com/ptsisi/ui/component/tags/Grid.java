package com.ptsisi.ui.component.tags;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ptsisi.ui.component.ComponentContext;
import com.ptsisi.ui.component.UIBean;
import freemarker.template.utility.StringUtil;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Grid extends UIBean {

	protected String selectItemName = "id";
	protected String height = "500";
	protected String url;
	protected String idField;
	protected boolean search = false;
	protected String searchAlign;
	protected String toolbarAlign;
	protected String pageList;
	protected String pageSize;
	protected String method = "post";
	protected String toolbar;
	protected String toolbarScript;
	protected String rowStyle;
	protected boolean clickToSelect = true;
	protected boolean singleSelect = false;
	protected boolean pagination = true;
	protected boolean serverPaginationSide = false;
	protected boolean striped = false;
	protected boolean cache = true;
	protected boolean showColumns = false;
	protected boolean showToggle = false;
	protected boolean showRefresh = false;
	protected boolean showAll = false;
	protected boolean maintainSelected = false;
	protected boolean sortable = true;

	protected boolean exportable = false;
	protected String exportType;

	protected boolean filterable = false;

	protected boolean editable = false;

	protected List<Col> cols = Lists.newArrayList();
	protected Set<Object> colTitles = Sets.newHashSet();
	protected Object items;
	protected String var;

	protected String emptyMsg;

	public Grid(ComponentContext context) {
		super(context);
	}

	protected void addCol(Col column) {
		String title = column.getTitle();
		if (null == title) title = column.getField();
		if (null == column.getWidth() && column instanceof Boxcol) column.setWidth("25px");
		if (!colTitles.contains(title)) {
			colTitles.add(title);
			cols.add(column);
		}
	}

	@Override
	protected void evaluateParams() {
		generateIdIfEmpty();
		url = render(url);
	}

	public List<Col> getCols() {
		return cols;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public Object getItems() {
		return items;
	}

	public void setItems(Object items) {
		this.items = items;
	}

	public String getSelectItemName() {
		return selectItemName;
	}

	public void setSelectItemName(String selectItemName) {
		this.selectItemName = selectItemName;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public String getSearchAlign() {
		return searchAlign;
	}

	public void setSearchAlign(String searchAlign) {
		this.searchAlign = searchAlign;
	}

	public String getToolbarAlign() {
		return toolbarAlign;
	}

	public void setToolbarAlign(String toolbarAlign) {
		this.toolbarAlign = toolbarAlign;
	}

	public String getPageList() {
		return pageList;
	}

	public void setPageList(String pageList) {
		this.pageList = pageList;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getToolbar() {
		return toolbar;
	}

	public void setToolbar(String toolbar) {
		this.toolbar = toolbar;
	}

	public String getRowStyle() {
		return rowStyle;
	}

	public void setRowStyle(String rowStyle) {
		this.rowStyle = rowStyle;
	}

	public boolean isClickToSelect() {
		return clickToSelect;
	}

	public void setClickToSelect(boolean clickToSelect) {
		this.clickToSelect = clickToSelect;
	}

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public boolean isSingleSelect() {
		return singleSelect;
	}

	public void setSingleSelect(boolean singleSelect) {
		this.singleSelect = singleSelect;
	}

	public boolean isPagination() {
		return pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public boolean isServerPaginationSide() {
		return serverPaginationSide;
	}

	public void setServerPaginationSide(boolean serverPaginationSide) {
		this.serverPaginationSide = serverPaginationSide;
	}

	public boolean isStriped() {
		return striped;
	}

	public void setStriped(boolean striped) {
		this.striped = striped;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public boolean isShowColumns() {
		return showColumns;
	}

	public void setShowColumns(boolean showColumns) {
		this.showColumns = showColumns;
	}

	public boolean isShowToggle() {
		return showToggle;
	}

	public void setShowToggle(boolean showToggle) {
		this.showToggle = showToggle;
	}

	public boolean isShowRefresh() {
		return showRefresh;
	}

	public void setShowRefresh(boolean showRefresh) {
		this.showRefresh = showRefresh;
	}

	public boolean isMaintainSelected() {
		return maintainSelected;
	}

	public void setMaintainSelected(boolean maintainSelected) {
		this.maintainSelected = maintainSelected;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public Set<Object> getColTitles() {
		return colTitles;
	}

	public void setColTitles(Set<Object> colTitles) {
		this.colTitles = colTitles;
	}

	public void setCols(List<Col> cols) {
		this.cols = cols;
	}

	public String getEmptyMsg() {
		return emptyMsg;
	}

	public void setEmptyMsg(String emptyMsg) {
		this.emptyMsg = emptyMsg;
	}

	public boolean isExportable() {
		return exportable;
	}

	public void setExportable(boolean exportable) {
		this.exportable = exportable;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public boolean isFilterable() {
		return filterable;
	}

	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getToolbarScript() {
		return toolbarScript;
	}

	public void setToolbarScript(String toolbarScript) {
		this.toolbarScript = toolbarScript;
	}

	public boolean getHasScript() {
		return StringUtils.isNotBlank(this.toolbarScript);
	}

	public static class Bar extends UIBean {

		protected Grid grid;

		public Bar(ComponentContext context) {
			super(context);
			grid = findAncestor(Grid.class);
		}

		@Override
		public boolean doEnd(Writer writer, String body) {
			grid.toolbarScript = body;
			return false;
		}

	}

	public static class Row extends IterableUIBean {

		protected Grid table;
		protected String var_index;
		protected Iterator<?> iterator;
		protected int index = -1;
		protected Object curObj;
		protected Boolean innerTr;

		public Row(ComponentContext context) {
			super(context);
			table = findAncestor(Grid.class);
			if (null != table.items) {
				Object iteratorTarget = table.items;
				iterator = IteratorUtils.getIterator(iteratorTarget);
				if (!iterator.hasNext()) {
					iterator = Collections.singleton(null).iterator();
				}
				this.var_index = table.var + "_index";
			}
		}

		@Override
		protected boolean next() {
			if (table.items != null) {
				if (iterator != null && iterator.hasNext()) {
					index++;
					curObj = iterator.next();
					return true;
				}

			} else if (table.url != null) {
				index++;
				return true;
			}
			return false;
		}
	}

	public static class Col extends UIBean {

		protected String align = "center";
		protected String halign = "center";
		protected String valign = "center";
		protected String formatter;
		protected String field;
		protected String events;
		protected String cellStyle;
		protected boolean sortable = true;
		protected String sorter;
		protected boolean switchable = true;
		protected boolean visible = true;
		protected String title;
		protected String width;

		protected Row row;

		public Col(ComponentContext context) {
			super(context);
		}

		@Override
		public boolean doStart(Writer writer) {
			row = findAncestor(Row.class);
			if (row.index == 0) row.table.addCol(this);
			return null != row.curObj || row.table.url != null;
		}

		@Override
		public boolean doEnd(Writer writer, String body) {
			if (StringUtils.isEmpty(row.table.url)) {
				try {
					writer.append("<td").append(getParameterString()).append(">");
					if (StringUtils.isNotBlank(body)) {
						writer.append(StringUtil.XHTMLEnc(body));
					} else if (null != field) {
						Object val = getValue();
						if (null != val) writer.append(StringUtil.XHTMLEnc(val.toString()));
					}
					writer.append("</td>");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			} else {
				return super.doEnd(writer, body);
			}
		}

		public Object getValue() {
			return getValue(getCurObj(), field);
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTitle() {
			if (null == title) {
				title = StringUtils.join(row.table.var, ".", field);
			}
			return getText(title);
		}

		public String getWidth() {
			return width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

		public Object getCurObj() {
			return row.curObj;
		}

		public String getAlign() {
			return align;
		}

		public void setAlign(String align) {
			this.align = align;
		}

		public String getHalign() {
			return halign;
		}

		public void setHalign(String halign) {
			this.halign = halign;
		}

		public String getValign() {
			return valign;
		}

		public void setValign(String valign) {
			this.valign = valign;
		}

		public String getFormatter() {
			return formatter;
		}

		public void setFormatter(String formatter) {
			this.formatter = formatter;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getEvents() {
			return events;
		}

		public void setEvents(String events) {
			this.events = events;
		}

		public String getCellStyle() {
			return cellStyle;
		}

		public void setCellStyle(String cellStyle) {
			this.cellStyle = cellStyle;
		}

		public boolean isSortable() {
			return sortable;
		}

		public void setSortable(boolean sortable) {
			this.sortable = sortable;
		}

		public String getSorter() {
			return sorter;
		}

		public void setSorter(String sorter) {
			this.sorter = sorter;
		}

		public boolean isSwitchable() {
			return switchable;
		}

		public void setSwitchable(boolean switchable) {
			this.switchable = switchable;
		}

		public boolean isVisible() {
			return visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		public Row getRow() {
			return row;
		}

		public void setRow(Row row) {
			this.row = row;
		}

	}

	public static class Boxcol extends Col {

		protected String type = "checkbox";

		protected String boxname = null;

		protected boolean display = true;

		protected boolean checked;

		public Boxcol(ComponentContext context) {
			super(context);
		}

		@Override
		public boolean doStart(Writer writer) {
			if (null == field) this.field = "id";
			row = findAncestor(Row.class);
			if (null == boxname) boxname = row.table.var + "." + field;
			if (row.index == 0) row.table.addCol(this);
			return null != row.curObj;
		}

		@Override
		public boolean doEnd(Writer writer, String body) {
			if (StringUtils.isEmpty(row.table.url)) {
				try {
					writer.append("<td");
					if (null != id) writer.append(" id=\"").append(id).append("\"");
					writer.append(getParameterString()).append(">");
					if (display) {
						writer.append("<input class=\"box\" name=\"").append(boxname).append("\" value=\"")
								.append(String.valueOf(getValue())).append("\" type=\"").append(type).append("\"");
						if (checked) writer.append(" checked=\"checked\"");
						writer.append("/>");
					}
					if (null != body) writer.append(body);
					writer.append("</td>");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			} else {
				return super.doEnd(writer, body);
			}
		}

		public String getType() {
			return type;
		}

		@Override
		public String getTitle() {
			return StringUtils.join(row.table.var, "_", field);
		}

		public String getBoxname() {
			return boxname;
		}

		public void setBoxname(String boxname) {
			this.boxname = boxname;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public boolean isDisplay() {
			return display;
		}

		public void setDisplay(boolean display) {
			this.display = display;
		}
	}
}

package com.alen.controller;


import com.alen.entity.Admin;
import com.alen.shiro.ShiroUtils;
import com.alen.utils.Pager;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class BaseController {
	protected static final String NODATA = "common/nodata";

	protected static class Params {
		private int flag;
		private int order;
		private String field;
		private String data;
		private String selected;
		private Map<String, String> dateMap;
		private Pager pager = new Pager();

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
			this.flag = flag;
		}

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public String getSelected() {
			return selected;
		}

		public void setSelected(String selected) {
			this.selected = selected;
		}

		public Map<String, String> getDateMap() {
			return dateMap;
		}

		public void setDateMap(Map<String, String> dateMap) {
			this.dateMap = dateMap;
		}

		public Pager getPager() {
			return pager;
		}

		public void setPager(Pager pager) {
			this.pager = pager;
		}
	}

	protected String getContextPath(HttpServletRequest request) {
		return request.getContextPath();
	}

	protected String getPath(HttpServletRequest request) {
		return request.getServletContext().getRealPath("");
	}

	protected String getPath(HttpServletRequest request, String path) {
		return request.getServletContext().getRealPath(path);
	}

	public Admin getOperator() {
		return ShiroUtils.getAdmin();
	}

	public void initPageSize(HttpServletRequest request, Pager pager,
			int pageSize) {
		if (!request.getParameterMap().containsKey("pager.pageSize"))
			pager.setPageSize(pageSize);
	}

	@ModelAttribute
	public void init(ModelMap model, HttpServletRequest request) {
		model.put("operator", getOperator());
		model.put("contextPath", getContextPath(request));
	}

	@ModelAttribute
	public Pager pager(Params p) {
		return p.getPager();
	}
}

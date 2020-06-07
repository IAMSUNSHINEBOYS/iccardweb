package com.alen.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 导出数据帮助类
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class Column {
	private Class<?> clazz;
	private String field;
	private Object object;
	public List<Column> columns = new ArrayList<Column>();
	public Set<Class<?>> clazzs = new HashSet<Class<?>>();

	public Column() {
	}

	public Column(Class<?> clazz, String field) {
		this.clazz = clazz;
		this.field = field;
	}

	public Column addColumn(Column column) {
		clazzs.add(column.clazz);
		columns.add(column);
		return this;
	}

	public Column addClass(Class<?> clazz) {
		clazzs.add(clazz);
		return this;
	}

	public Column addColumn(Class<?> clazz, String... field) {
		if (field != null && field.length > 0)
			for (String f : field)
				addColumn(new Column(clazz, f));
		return this;
	}

	public String getField() {
		return field;
	}

	public Object getObject() {
		return object;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void initColumns(Object obj, List<Column> cols) throws Exception {
		for (Column col : columns)
			cols.add(new Column(col.clazz, col.field));
		Map<Class<?>, Object> classMap = new HashMap<Class<?>, Object>();
		setObjects(obj, cols, classMap);
		for (Column col : cols)
			col.object = classMap.get(col.clazz);
	}

	public void setObjects(Object obj, List<Column> cols,
			Map<Class<?>, Object> classMap) throws Exception {
		if (obj != null) {
			if (!classMap.containsKey(obj.getClass())) {
				classMap.put(obj.getClass(), obj);
			}
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field f : fields) {
				if (clazzs.contains(f.getType())
						&& !classMap.containsKey(f.getType())) {
					String getMethod = "get"
							+ f.getName().substring(0, 1).toUpperCase()
							+ f.getName().substring(1);
					Object o = obj.getClass().getMethod(getMethod).invoke(obj);
					classMap.put(f.getType(), o);
					setObjects(o, cols, classMap);
				}
			}
		}
	}
}
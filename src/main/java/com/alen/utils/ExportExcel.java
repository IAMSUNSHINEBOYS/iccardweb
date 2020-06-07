package com.alen.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Excel导出
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public abstract class ExportExcel {
	private String path;
	private String filename;
	protected Pager pager;
	private HttpServletResponse response;

	/**
	 * 导出一个SHEET
	 */
	public ExportExcel(String path, String filename, Pager pager, HttpServletResponse response) {
		this(path, filename, pager, response, 4000);
	}

	/**
	 * 导出一个SHEET
	 */
	public ExportExcel(String path, String filename, Pager pager, HttpServletResponse response, int pageSize) {
		this.path = path;
		this.response = response;
		this.filename = filename;
		this.pager = pager;
		this.pager.setBigPageSize(pageSize);
	}

	/**
	 * 通过JAVABEAN导出
	 */
	public void toExcel(Column columns) {
		path += ".xlsx";
		toExcelXlsx(columns, 0);
	}

	/**
	 * 通过OBJECT导出
	 */
	public void toExcel() {
		path += ".xlsx";
		toExcelXlsx(new Column(), 1);
	}

	/**
	 * 导出到EXECL2010
	 */
	private void toExcelXlsx(Column columns, int type) {
		InputStream is = null;
		OutputStream os = null;
		XSSFWorkbook xwb = null;
		SXSSFWorkbook wb = null;
		try {
			os = getOutputStream(filename + ".xlsx");
			is = new BufferedInputStream(new FileInputStream(path));
			xwb = new XSSFWorkbook(is);
			wb = new SXSSFWorkbook(xwb, 100);
			Sheet xwbsh = xwb.getSheetAt(0);
			int lastRowNum = xwbsh.getLastRowNum();
			Row lastRow = xwbsh.getRow(lastRowNum);
			short height = lastRow.getHeight();
			List<CellStyle> styles = new ArrayList<CellStyle>();
			for (int cellnum = 0; cellnum < lastRow.getLastCellNum(); cellnum++) {
				styles.add(lastRow.getCell(cellnum).getCellStyle());
			}
			xwbsh.removeRow(lastRow);
			Sheet wbsh = wb.getSheetAt(0);
			int size = 0;
			List<?> list = getDataList();
			for (int page = 1; page <= pager.getTotalPage(); page++) {
				pager.setCurrentPage(page);
				lastRowNum = lastRowNum + size;
				if (page >= 2) {
					list = getDataList();
				}
				size = list.size();
				if (list == null || list.isEmpty())
					continue;
				for (int rownum = 0; rownum < size; rownum++) {
					Row row = wbsh.createRow(rownum + lastRowNum);
					row.setHeight(height);
					if (type == 0) {// JAVABEAN
						Object obj = list.get(rownum);
						List<Column> cols = new ArrayList<Column>();
						columns.initColumns(obj, cols);
						for (int i = 0; i < cols.size(); i++) {
							Cell cell = row.createCell(i);
							cell.setCellStyle(styles.get(i));
							Column c = cols.get(i);
							if (c.getObject() != null) {
								String getMethod = "get" + c.getField().substring(0, 1).toUpperCase()
										+ c.getField().substring(1);
								setCellValue(cell, c.getClazz().getMethod(getMethod).invoke(c.getObject()));
							}
						}
					} else {// OBJECT
						Object obj = list.get(rownum);
						Object[] objs;
						if (obj.getClass().isArray()) {
							objs = (Object[]) list.get(rownum);
						} else {
							objs = new Object[] { obj };
						}
						for (int i = 0; i < objs.length; i++) {
							Cell cell = row.createCell(i);
							cell.setCellStyle(styles.get(i));
							setCellValue(cell, objs[i]);
						}
					}
				}
			}
			wb.write(os);
			wb.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOClose.close(is, os);
			closeWorkbook(wb, xwb);
		}
	}

	protected abstract List<?> getDataList();

	private String encodeParam(String param) {
		try {
			return URLEncoder.encode(param, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setCellValue(Cell cell, Object obj) {
		if (obj != null) {
			if (obj instanceof Number)
				cell.setCellValue(((Number) obj).doubleValue());
			else if (obj instanceof Date)
				cell.setCellValue((Date) obj);
			else
				cell.setCellValue(obj.toString());
		}
	}

	private void closeWorkbook(Workbook... workbooks) {
		if (workbooks != null)
			for (Workbook workbook : workbooks)
				if (workbook != null)
					try {
						workbook.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
	}

	private OutputStream getOutputStream(String filename) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-disposition", "attachment; filename*=UTF-8'zh_cn'" + encodeParam(filename));
		response.setContentType("application/vnd.ms-excel");
		return response.getOutputStream();
	}
}

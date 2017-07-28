package com.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.common.base.common.BaseLogger;

/**
 * Excel工具类
 * 
 * @author: HuiJunLuo
 * @date:2016年6月2日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class ExcelUtil extends BaseLogger {

	private static ExcelUtil excelUtil = null;

	public synchronized static ExcelUtil getInstance() {
		if (excelUtil == null) {
			excelUtil = new ExcelUtil();
		}
		return excelUtil;
	}

	private ExcelUtil() {
	}
	
    /**
	 * 创建excel文档，
	 * 
	 * @param list
	 *            数据
	 * @param keys
	 *            list中map的key数组集合
	 * @param columnNames
	 *            excel的列名
	 */
	public XSSFWorkbook createWorkBook2007(List<Map<String, Object>> list, String[] keys, String columnNames[]) {
		// 创建excel工作簿
		XSSFWorkbook wb = new XSSFWorkbook();
		// 创建第一个sheet（页），并命名
		XSSFSheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for (int i = 0; i < keys.length; i++) {
			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
		}

		// 创建第一行
		XSSFRow row = sheet.createRow((short) 0);

		// 创建两种单元格格式
		XSSFCellStyle cs = wb.createCellStyle();
		XSSFCellStyle cs2 = wb.createCellStyle();

		// 创建两种字体
		XSSFFont f = wb.createFont();
		XSSFFont f2 = wb.createFont();

		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints((short) 10);
		f2.setColor(IndexedColors.BLACK.getIndex());

		// Font f3=wb.createFont();
		// f3.setFontHeightInPoints((short) 10);
		// f3.setColor(IndexedColors.RED.getIndex());

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		cs2.setBorderLeft(CellStyle.BORDER_THIN);
		cs2.setBorderRight(CellStyle.BORDER_THIN);
		cs2.setBorderTop(CellStyle.BORDER_THIN);
		cs2.setBorderBottom(CellStyle.BORDER_THIN);
		cs2.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置列名
		for (int i = 0; i < columnNames.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(cs);
		}
		// 设置每行每列的值
		for (short i = 1; i < list.size(); i++) {
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			XSSFRow row1 = sheet.createRow((short) i);
			// 在row行上创建一个方格
			for (short j = 0; j < keys.length; j++) {
				XSSFCell cell = row1.createCell(j);
				cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
				cell.setCellStyle(cs2);
			}
		}
		return wb;
	}

	/**
	 * 创建excel文档，
	 * 
	 * @param list
	 *            数据
	 * @param keys
	 *            list中map的key数组集合
	 * @param columnNames
	 *            excel的列名
	 */
	public Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String columnNames[]) {
		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for (int i = 0; i < keys.length; i++) {
			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
		}

		// 创建第一行
		Row row = sheet.createRow((short) 0);

		// 创建两种单元格格式
		CellStyle cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();

		// 创建两种字体
		Font f = wb.createFont();
		Font f2 = wb.createFont();

		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints((short) 10);
		f2.setColor(IndexedColors.BLACK.getIndex());

		// Font f3=wb.createFont();
		// f3.setFontHeightInPoints((short) 10);
		// f3.setColor(IndexedColors.RED.getIndex());

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		cs2.setBorderLeft(CellStyle.BORDER_THIN);
		cs2.setBorderRight(CellStyle.BORDER_THIN);
		cs2.setBorderTop(CellStyle.BORDER_THIN);
		cs2.setBorderBottom(CellStyle.BORDER_THIN);
		cs2.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置列名
		for (int i = 0; i < columnNames.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(cs);
		}
		// 设置每行每列的值
		for (short i = 1; i < list.size(); i++) {
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			Row row1 = sheet.createRow((short) i);
			// 在row行上创建一个方格
			for (short j = 0; j < keys.length; j++) {
				Cell cell = row1.createCell(j);
				cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
				cell.setCellStyle(cs2);
			}
		}
		return wb;
	}

	/**
	 * read the Excel file
	 * 
	 * @param path
	 *            the path of the Excel file
	 * @return
	 * @throws IOException
	 */
	public <T> List<T> readExcel(String path, Class<T> cls, Map<Integer, String> paramNameMap) throws IOException {
		List<String[]> listArray = readExcel(path);
		if (CollectionUtils.isNotEmpty(listArray)) {
			List<T> listObj = new ArrayList<>();
			for (String[] strArray : listArray) {

				try {
					T obj = cls.newInstance();
					for (int i = 0; i < strArray.length; i++) {
						try {
							AnnotationUtil.getInstance().setFieldValue(obj, paramNameMap.get(i),
									CommUtil.getInstance().replaceExcelBlank(strArray[i]), true);
						} catch (IllegalArgumentException | SecurityException e) {
							logger.error(e.getMessage(), e);
						}
					}
					listObj.add(obj);
				} catch (InstantiationException e) {
					logger.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				}

			}
			return listObj;
		}
		return null;
	}

	/**
	 * read the Excel file
	 * 
	 * @param path
	 *            the path of the Excel file
	 * @return
	 * @throws IOException
	 */
	public List<String[]> readExcel(String path) throws IOException {
		if (StringUtils.isEmpty(path)) {
			return null;
		} else {
			String suffix = StringUtils.substringAfterLast(path, ".");
			logger.debug("file suffix:" + suffix);
			if (StringUtils.isNotEmpty(suffix)) {
				if ("xls".equalsIgnoreCase(suffix)) {
					return readXls(path);
				} else if ("xlsx".equalsIgnoreCase(suffix)) {
					return readXlsx(path);
				}
			}
		}
		return null;
	}

	/**
	 * Read the Excel 2010
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return
	 * @throws IOException
	 */
	public List<String[]> readXlsx(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<String[]> list = new ArrayList<>();
		try {
			// Read the Sheet
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// Read the Row
				XSSFRow headRow = xssfSheet.getRow(0);
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						String[] strArray = new String[headRow.getPhysicalNumberOfCells()];
						for (int i = 0; i < strArray.length; i++) {
							XSSFCell cellTmp = xssfRow.getCell(i);
							strArray[i] = getValue(cellTmp);
						}
						list.add(strArray);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			xssfWorkbook.close();
			is.close();
		}
		return list;
	}

	/**
	 * Read the Excel 2003-2007
	 * 
	 * @param path
	 *            the path of the Excel
	 * @return
	 * @throws IOException
	 */
	public List<String[]> readXls(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<String[]> list = new ArrayList<>();
		try {
			// Read the Sheet
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// Read the Row
				HSSFRow headRow = hssfSheet.getRow(0);
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow != null) {

						String[] strArray = new String[headRow.getPhysicalNumberOfCells()];
						for (int i = 0; i < strArray.length; i++) {
							HSSFCell cellTmp = hssfRow.getCell(0);
							strArray[i] = getValue(cellTmp);
						}
						list.add(strArray);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			hssfWorkbook.close();
			is.close();
		}
		return list;
	}

	@SuppressWarnings("static-access")
	private String getValue(XSSFCell xssfRow) {
		if (xssfRow == null) {
			return null;
		}
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

	@SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell == null) {
			return null;
		}
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	public static void main(String[] args) {
		try {
//			String s = "分类模块.xlsx";
			// s="产品替换信息.xlsx";
//			List<String[]> list = getInstance().readExcel("E:\\Gml\\mornsun\\Svn\\mornsun\\info\\芯得用于测试的数据\\" + s);
			// for (String[] strs : list) {
			// StringBuffer buffer = new StringBuffer();
			// for (String str : strs) {
			// buffer.append(str).append(" ");
			// }
			// System.out.println(buffer);
			// }

			Map<Integer, String> map = new HashMap<>();
			map.put(0, "catalogName");
			map.put(1, "parentId");
			// List<CatalogModel> catalogModels = getInstance()
			// .readExcel("E:\\Gml\\mornsun\\Svn\\mornsun\\info\\芯得用于测试的数据\\" +
			// s, CatalogModel.class, map);
			// for (CatalogModel catalogModel : catalogModels) {
			// System.out.println(catalogModel);
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.ruoyi.hemerdinger.finance.util.excel;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Description Excel读取工具
 * 	Workbook:Excel对象 ,工作簿(sheet)集合
 * 	使用Workbook.getSheetAt(index)或者Workbook.getSheet(name)可以获取对应工作簿(sheet)
 *  	
 *  Sheet工作簿,使用sheet.getRow(rownum)获取对应的行
 *  	
 *  Row 行,使用row.getLastCellNum()获取最后一的单元格位置  row.getCell(j)获取对应单元格
 *  	
 *  Cell 单元格
 * @author LiJingXiang
 * @version 2017年4月21日
 */
public class ExcelReader {
	private static final DataFormatter DEFAULT_DATA_FORMATTER = new DataFormatter();
	private static final ExcelGeneralNumberFormat NUMBER_FORMAT = new ExcelGeneralNumberFormat(Locale.SIMPLIFIED_CHINESE);
	/**
	 * @Description: 读取第一页所有行
	 * @return: List<Row>
	 */
	public static List<Row> readExcelFirtPage(String filePath) {
		Workbook workbook = loadExcel(filePath);
		Sheet sheet = workbook.getSheetAt(0);
		return readLine(sheet);
	}

	/**
	 * @Description: 读取excel文件
	 * @param filePath:本地文件路径
	 * @return
	 * @return: Workbook
	 */
	public static Workbook loadExcel(String filePath) {
		FileInputStream inStream = null;
		Workbook workBook = null;
		try {
			inStream = new FileInputStream(new File(filePath));
			workBook = WorkbookFactory.create(inStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return workBook;
	}

	/**
	 * @Description: 获取一张表中所有的行 ,只是简单的转换为List
	 * @param sheet
	 * @return
	 * @return: List<Row>:行集合
	 */
	public static List<Row> readLine(Sheet sheet) {
		int rowNum = sheet.getLastRowNum() + 1;
		List<Row> rows = new ArrayList<Row>(rowNum);
		for (int i = 0; i < rowNum; i++) {
			Row row = sheet.getRow(i);
			rows.add(row);
		}
		return rows;
	}
	
	/**
	 * @Description: 获取一张行中所有的单元格
	 * @param row
	 * @return
	 * @return: List<Cell>
	 */
	public static List<String> readLine(Row row , DataFormatter dateFormatter) {
		int cellNum = row.getLastCellNum() + 1;
		List<String> cells = new ArrayList<String>(cellNum);
		for (int i = 0; i < cellNum; i++) {
			Cell cell = row.getCell(i);
			String data = getCellValue(cell, dateFormatter) ;
			cells.add(data);
		}
		return cells;
	}
	
	// 获取单元格的值
	public static String getCellValue(Cell cell) {
		return getCellValue(cell, DEFAULT_DATA_FORMATTER);
	}

	public static String getCellValue(Cell cell, DataFormatter dateFormatter) {
		if (dateFormatter == null) {
			dateFormatter = DEFAULT_DATA_FORMATTER;
		}
		String cellValue = "";
		if (cell != null) {
			// 判断单元格数据的类型，不同类型调用不同的方法
			switch (cell.getCellTypeEnum()) {
			// 数值类型
			case NUMERIC:
				// 进一步判断 ，单元格格式是日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = dateFormatter.formatCellValue(cell);
				} else {
					cellValue = NUMBER_FORMAT.format(cell.getNumericCellValue());
				}
				break;
			case STRING:
				cellValue = cell.getStringCellValue();
				break;
			case BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			// 判断单元格是公式格式，需要做一种特殊处理来得到相应的值
			case FORMULA: {
				try {
					cellValue = String.valueOf(cell.getNumericCellValue());
				} catch (IllegalStateException e) {
					cellValue = String.valueOf(cell.getRichStringCellValue());
				}

			}
				break;
			case BLANK:
				cellValue = "";
				break;
			case ERROR:
				cellValue = "";
				break;
			default:
				cellValue = cell.toString().trim();
				break;
			}
		}
		return cellValue.trim();
	}
}

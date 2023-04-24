package com.ruoyi.hemerdinger.finance.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class ExcelConverter {
	Integer curentRowNum = null ;
	Integer totalRowNum = null ;
	private Handler handler ;
	
	public ExcelConverter(Handler handler) {
		this.handler = handler;
	}

	public void convert(String filePath){
		List<Row> rows = ExcelReader.readExcelFirtPage(filePath);
		totalRowNum = rows.size() ;
		for (int i = 0; i < totalRowNum; i++) {
			curentRowNum = i ;
			Row row = rows.get(i);
			handler.onStartReadRow(i);
			for (int j = 0; j <= row.getLastCellNum(); j++) {
				Cell cell = row.getCell(j);
				String date = ExcelReader.getCellValue(cell);
				handler.onReadData(i,j,date);
			}
			handler.onEndReadRow(i);
		}
	}
	
	protected boolean isLastRow() {
		if (curentRowNum == null) {
			return false ;
		}
		return curentRowNum.equals(totalRowNum);
	}
	
	protected boolean isFirstRow() {
		return new Integer(0).equals(curentRowNum);
	}
	
	public interface Handler {
		void onStartReadRow(int rowNum);
		void onReadData(int rowNum, int columnNum, String data);
		void onEndReadRow(int rowNum);
	}
}


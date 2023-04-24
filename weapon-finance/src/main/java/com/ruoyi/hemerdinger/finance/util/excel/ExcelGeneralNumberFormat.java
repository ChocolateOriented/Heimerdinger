/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.ruoyi.hemerdinger.finance.util.excel;

import org.apache.poi.ss.usermodel.DataFormatter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.*;
import java.util.Locale;

public class ExcelGeneralNumberFormat extends Format {
	private static final long serialVersionUID = 1L;
	private static final MathContext TO_10_SF = new MathContext(10, RoundingMode.HALF_UP);
	private final DecimalFormatSymbols decimalSymbols;
	private final DecimalFormat integerFormat;
	private final DecimalFormat decimalFormat;
	private final DecimalFormat scientificFormat;

	public ExcelGeneralNumberFormat(Locale locale) {
		this.decimalSymbols = DecimalFormatSymbols.getInstance(locale);
		this.scientificFormat = new DecimalFormat("0.#####E0", this.decimalSymbols);
		DataFormatter.setExcelStyleRoundingMode(this.scientificFormat);
		this.integerFormat = new DecimalFormat("#", this.decimalSymbols);
		DataFormatter.setExcelStyleRoundingMode(this.integerFormat);
		this.decimalFormat = new DecimalFormat("#.##########", this.decimalSymbols);
		DataFormatter.setExcelStyleRoundingMode(this.decimalFormat);
	}

	public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
		double value;
		if (number instanceof Number) {
			System.out.println( "number instanceof Number");
			value = ((Number) number).doubleValue();
			
			if ((Double.isInfinite(value)) || (Double.isNaN(value))){
				System.out.println( "number instanceof Number");
				return this.integerFormat.format(number, toAppendTo, pos);
			}
		} else {
			System.out.println( "number not instanceof Number");
			return this.integerFormat.format(number, toAppendTo, pos);
		}
		
		double abs = Math.abs(value);
		if ((abs >= 100000000000.0D) || ((abs <= 1.0E-010D) && (abs > 0.0D))){
			System.out.println( "abs >= 100000000000.0D) || ((abs <= 1.0E-010D) && (abs > 0.0D");
			return this.scientificFormat.format(number, toAppendTo, pos);
		}
		
		if ((Math.floor(value) == value) || (abs >= 10000000000.0D)) {
			System.out.println( "Math.floor(value) == value) || (abs >= 10000000000.0D");
			return this.integerFormat.format(number, toAppendTo, pos);
		}

		double rounded = new BigDecimal(value).round(TO_10_SF).doubleValue();
		return this.decimalFormat.format(rounded, toAppendTo, pos);
	}

	public Object parseObject(String source, ParsePosition pos) {
		throw new UnsupportedOperationException();
	}
}
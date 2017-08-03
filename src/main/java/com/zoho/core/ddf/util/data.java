package com.zoho.core.ddf.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class data {
	private static XSSFSheet sheet;
	static XSSFCell cell = null;

	public static Object[][] gettestdata(FileInputStream Datafile, String testcase) throws IOException {
		// TODO Auto-generated method stub

		XSSFWorkbook W = new XSSFWorkbook(Datafile);
		sheet = W.getSheet("Sheet4");

		int startRowNum = 0;

		while (!getData(startRowNum, 0).equals(testcase)) {
			startRowNum++;
		}
		System.out.println(startRowNum);
		int colstartronum = startRowNum + 1;
		int datastartron = startRowNum + 2;
		System.out.println("data start row " + datastartron);
		// calculate total no of rows
		int row = 0;
		while (!getData(datastartron + row, 0).equals("")) {
			row++;
		}
		int totalrows = row;
		System.out.println("total row is equal " + totalrows);
		// calculate total no of colums
		int totalcol = 0;
		while ((!getData(colstartronum, totalcol).equals(""))) {
			totalcol++;
		}

		System.out.println("total colum is equal " + totalcol);
		// Object[][] data = new Object[totalrows][totalcol];
		Object[][] data = new Object[totalrows][1];
		Hashtable<String, String> table = null;
		int fixcol = datastartron - 1;
		int datarow = 0;
		for (int i = datastartron; i < datastartron + totalrows; i++) {
			table = new Hashtable<String, String>();
			for (int j = 0; j < totalcol; j++) {
				// data[datarow][j] = getData(i, j);
				String key = getData(fixcol, j);
				String value = getData(i, j);
				table.put(key, value);
			}
			data[datarow][0] = table;
			datarow++;
		}

		return data;
	}

	private static String getData(int rowNum, int col) {

		if (sheet.getRow(rowNum) == null) {
			return "";
		} else if (sheet.getRow(rowNum).getCell(col) == null) {
			return "";
		}
		else if (sheet.getRow(rowNum).getCell(col).getCellTypeEnum() == CellType.NUMERIC)
		// ||
		// (sheet.getRow(rowNum).getCell(0).getCellTypeEnum()==CellType.NUMERIC)
		{
			String cellText = String.valueOf(sheet.getRow(rowNum).getCell(col).getNumericCellValue());
			return cellText;
		}

		else {
			return sheet.getRow(rowNum).getCell(col).getStringCellValue();
		}
	}

	public boolean isrunnable(String datafile, String testase)
	{
		
		
		return false;

	}
}



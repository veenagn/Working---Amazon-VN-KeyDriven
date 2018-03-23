package com.veena.learn.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	private final String filePath;

	public ExcelUtility(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * execute all the scenarios from all the sheet in a given excel spreadsheet
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ExcelRow> executeAllScenarios() throws Exception {

		Map<String, List<ExcelRow>> sheets = getSheets(filePath);
		Set<String> keySet = sheets.keySet();

		// for each sheet get all the rows and execute each row actions
		for (String key : keySet) {
			List<ExcelRow> steps = sheets.get(key);
			return execute(steps);
		}
		return new ArrayList<>();
	}

	/**
	 * ExecuteScenarios From a given Sheet
	 * 
	 * @param sheetNamePassed
	 * @return
	 * @throws Exception
	 */
	public List<ExcelRow> executeScenariosFromSheet(String sheetNamePassed) throws Exception {
		Map<String, List<ExcelRow>> sheets = getSheets(filePath);
		// get all the keys
		Set<String> keySet = sheets.keySet();

		// for each key (sheet name) get all rows
		for (String sheetName : keySet) {
			if (sheetNamePassed.equalsIgnoreCase(sheetName)) {
				List<ExcelRow> rows = getSheetContents(sheetName);
				return execute(rows);
			}
		}
		return null;
	}
	
	public List<ExcelRow> execute(List<ExcelRow> caseSteps) {
		for (ExcelRow testCaseStep : caseSteps) {
			String actionKeyword = testCaseStep.getActionKeyword();
			String testData = testCaseStep.getTestData();
			String pageObject = testCaseStep.getPageObject();
			System.out.println("The Page Object is: " + pageObject);
			boolean runMode = testCaseStep.isRunMode();
			int index = 0;
			if (runMode) {
				try {
					System.out.println("actionKeyword : " + actionKeyword);
					ActionKeywords action = new ActionKeywords();
					Method[] method = action.getClass().getDeclaredMethods();
					for (int i = index; i < method.length; i++) {
						if (method[i].getName().equals(actionKeyword)) {
							method[i].invoke(action, testData, pageObject);
							index = index + 1;
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("===> Not executed because runMode = false");
			}
		}
		return caseSteps;
	}


	/**
	 * list rows from a given sheet
	 * 
	 * @param sheetName
	 * @return List<TestCaseStep> of steps
	 * @throws Exception
	 */
	public List<ExcelRow> getSheetContents(String sheetName) throws Exception {

		Map<String, List<ExcelRow>> sheets = getSheets(filePath);
		for (String key : sheets.keySet()) {
			if (key.equalsIgnoreCase(sheetName)) {
				return sheets.get(key);
			}
		}
		return null;
	}

	/**
	 * get all the rows from the given excel file including all the sheets
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<ExcelRow>> getSheets(String filePath) throws Exception {
		List<ExcelRow> testCaseStepList = new ArrayList<ExcelRow>();
		FileInputStream fis = null;
		Map<String, List<ExcelRow>> sheets = new HashMap<>();

		try {
			ClassLoader classLoader = getClass().getClassLoader();
			fis = new FileInputStream(new File(classLoader.getResource(filePath).getFile()));

			// Using XSSF for xlsx format, for xls use HSSF
			Workbook workbook = new XSSFWorkbook(fis);

			int numberOfSheets = workbook.getNumberOfSheets();

			// looping over each workbook sheet
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rowIterator = sheet.iterator();

				// To skip header
				rowIterator.next();

				// iterating over each row
				while (rowIterator.hasNext()) {

					ExcelRow testCaseStep = new ExcelRow();

					Row row = rowIterator.next();

					Iterator<Cell> cellIterator = row.cellIterator();

					// Iterating over each cell (column wise) in a particular row.
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();

						// first column 0, .....
						switch (cell.getColumnIndex()) {
						case 0:
							testCaseStep.setTestCaseID(getStringValue(cell));
							break;
						case 1:
							testCaseStep.setTestScenarioID(getStringValue(cell));
							break;
						case 2:
							testCaseStep.setDescription(getStringValue(cell));
							break;
						case 3:
							testCaseStep.setPageObject(getStringValue(cell));
							break;
						case 4:
							testCaseStep.setActionKeyword(getStringValue(cell));
							break;
						case 5:
							testCaseStep.setTestData(getStringValue(cell));
							break;
						case 6:
							testCaseStep.setRunMode(getBoolean(cell));
							break;
						case 7:
							testCaseStep.setResult(getStringValue(cell));
							break;
						}
					}
					// end iterating a row, add all the elements of a row in list
					testCaseStepList.add(testCaseStep);
				}
				sheets.put(sheet.getSheetName(), testCaseStepList);
			}

			fis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sheets;
	}

	private String getStringValue(Cell cell) {
		return String.valueOf(cell.getStringCellValue());
	}

	private Boolean getBoolean(Cell cell) {
		String stringCellValue = cell.getStringCellValue();
		if (null != stringCellValue) {
			if (stringCellValue.equalsIgnoreCase("Y") || stringCellValue.equalsIgnoreCase("Yes")
					|| stringCellValue.equalsIgnoreCase("True")) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}

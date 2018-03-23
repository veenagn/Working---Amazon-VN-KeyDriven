package com.veena.learn.runner;


import java.util.List;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.veena.learn.excel.ExcelRow;
import com.veena.learn.excel.ExcelUtility;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestCaseRunner {

	private static final String FILE_PATH = "DataEngine.xlsx";
	private static ExcelUtility excelHelper = new ExcelUtility(FILE_PATH);
			
	@BeforeClass
	public static void load() throws Exception {
		excelHelper = new ExcelUtility(FILE_PATH);
	}
	
	@org.junit.Test
	public void executAllTest() throws Exception {
		List<ExcelRow> result = excelHelper.executeAllScenarios();
		result.forEach(row -> {
			System.out.println("result : " + row.getResult());
		});
	}

}
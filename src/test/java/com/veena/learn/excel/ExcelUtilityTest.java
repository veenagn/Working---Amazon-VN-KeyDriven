package com.veena.learn.excel;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class ExcelUtilityTest {

	private static final String FILE_PATH = "DataEngine.xlsx";
	private static final ExcelUtility classUnderTest = new ExcelUtility(FILE_PATH);

	@Test
	public void readASheet() throws Exception {
		List<ExcelRow> sheetContent = classUnderTest.getSheetContents("Test Steps");
		assertNotNull("sheet name not found", sheetContent);
		sheetContent.forEach(action -> {
			System.out.println(action);
		});
	}
	
	@Test
	public void executeAll() throws Exception {
		List<ExcelRow> sheetContent = classUnderTest.executeAllScenarios();
		assertNotNull("sheet name not found", sheetContent);
		sheetContent.forEach(action -> {
			System.out.println("The action is: "+action);
		});
	}
//	
//	@Test
//	public void executeAllFrom_TestSteps1() {
//		List<TestCaseStep> stepsWithResponse = classUnderTest.executeScenariosFromSheet("TestSteps-1");
//		Assert.assertNotNull("sheet name not found", stepsWithResponse);
//		stepsWithResponse.forEach(result -> {
//			Assert.assertNotNull(result.getResult());
//			Assert.assertThat(result.getResult(), CoreMatchers.is("PASS"));
//		});
//	}

}

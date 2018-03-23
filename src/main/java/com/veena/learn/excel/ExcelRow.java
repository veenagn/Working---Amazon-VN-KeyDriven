package com.veena.learn.excel;

/**
 * represent the 
 * 
 * @author veena
 *
 */
public class ExcelRow {

	private String testCaseID;
	private String testScenarioID;
	private String description;
	private String pageObject;
	private String actionKeyword;
	private String testData;
	private boolean runMode;
	private String result;

	public String getTestCaseID() {
		return testCaseID;
	}

	public void setTestCaseID(String testCaseID) {
		this.testCaseID = testCaseID;
	}

	public String getTestScenarioID() {
		return testScenarioID;
	}

	public void setTestScenarioID(String testScenarioID) {
		this.testScenarioID = testScenarioID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPageObject() {
		return pageObject;
	}

	public void setPageObject(String pageObject) {
		this.pageObject = pageObject;
	}

	public String getActionKeyword() {
		return actionKeyword;
	}

	public void setActionKeyword(String actionKeyword) {
		this.actionKeyword = actionKeyword;
	}

	public String getTestData() {
		return testData;
	}

	public void setTestData(String testData) {
		this.testData = testData;
	}

	public boolean isRunMode() {
		return runMode;
	}

	public void setRunMode(boolean runMode) {
		this.runMode = runMode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return String.format(
				"TestCaseStep=%s, testScenarioID=%s, description=%s, pageObject=%s, actionKeyword=%s, testData=%s, runMode=%s result=%s",
				testCaseID, testScenarioID, description, pageObject, actionKeyword, testData, runMode, result)
				.toString();
	}
}

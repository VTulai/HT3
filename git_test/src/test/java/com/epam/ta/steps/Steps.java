package com.epam.ta.steps;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.pages.*;
import com.epam.ta.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Steps
{
	private WebDriver driver;

	private final Logger logger = LogManager.getRootLogger();

	public void openBrowser()
	{
		driver = DriverSingleton.getDriver();
	}

	public void closeBrowser()
	{
		DriverSingleton.closeDriver();
	}

	public void loginGithub(String username, String password)
	{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage();
		loginPage.login(username, password);
	}

	public String getLoggedInUserName()
	{
		LoginPage loginPage = new LoginPage(driver);
		return loginPage.getLoggedInUserName().trim().toLowerCase();
	}

	public String getUserName()
	{
		LoginPage loginPage = new LoginPage(driver);
		return loginPage.getLoggedInUserName();
	}

	public void createNewRepository(String repositoryName, String repositoryDescription)
	{
		MainPage mainPage = new MainPage(driver);
		mainPage.clickOnCreateNewRepositoryButton();
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		createNewRepositoryPage.createNewRepository(repositoryName, repositoryDescription);
	}

	public boolean currentRepositoryIsEmpty()
	{
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		return createNewRepositoryPage.isCurrentRepositoryEmpty();
	}

	public String getCurrentRepositoryName(){
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		return createNewRepositoryPage.getCurrentRepositoryName();
	}

	public String generateRandomRepositoryNameWithCharLength(int howManyChars){
		String repositoryNamePrefix = "testRepo_";
		return repositoryNamePrefix.concat(Utils.getRandomString(howManyChars));
	}

	public void openRepositoryPage(String repositoryName) {
		UserPage userPage = new UserPage(driver, getUserName());
		userPage.findRepositoryAndOpenRepositoryPage(repositoryName);
	}

	public void deleteRepository(String repositoryName, String password) {
		RepositoryPage repositoryPage = new RepositoryPage(driver, getUserName(), repositoryName);
		repositoryPage.clickOnDeleteRepository();
		repositoryPage.confirmDeleteRepositoryWithRepositoryName();
		repositoryPage.enterPassword(password);
		repositoryPage.clickConfirmPasswordButton();
	}

	public boolean isRepoExists(String repositoryName) {
		UserPage userPage = new UserPage(driver, getUserName());
		return userPage.isRepositoryExists(repositoryName);
	}

	public String getPageTitle(){
		return driver.getTitle();
	}

	public void addBio(String bio) {
		UserPage userPage = new UserPage(driver, getUserName());
		userPage.addBio(bio);
	}

	public String getBioText() {
		UserPage userPage = new UserPage(driver, getUserName());
		return userPage.getBioText();
	}
}

package com.epam.ta;

import com.epam.ta.steps.Steps;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GitHubAutomationTest
{
	private Steps steps;
	private final String USERNAME = "testautomationuser";
	private final String PASSWORD = "Time4Death!";
	private String repositoryName = "testRepo_OI2FB1";
	private final int REPOSITORY_NAME_POSTFIX_LENGTH = 6;
	private String sometext = "asd";

	@BeforeMethod(description = "Init browser")
	public void setUp()
	{
		steps = new Steps();
		steps.openBrowser();
	}

	@Test
	public void oneCanCreateProject()
	{
		steps.loginGithub(USERNAME, PASSWORD);
		String repositoryName = steps.generateRandomRepositoryNameWithCharLength(REPOSITORY_NAME_POSTFIX_LENGTH);
		steps.createNewRepository(repositoryName, "auto-generated test repo");
		Assert.assertEquals(steps.getCurrentRepositoryName(), repositoryName);
	}

	@Test(description = "Login to Github")
	public void oneCanLoginGithub()
	{
		steps.loginGithub(USERNAME, PASSWORD);
		Assert.assertEquals(USERNAME, steps.getLoggedInUserName());
	}

	@Test(description = "Find repository on github with repository name")
	public void findRepoOnGithub()
	{
		steps.loginGithub(USERNAME, PASSWORD);
		Assert.assertEquals(USERNAME, steps.getLoggedInUserName());
		try {
			steps.openRepositoryPage(repositoryName);
		} catch (NoSuchElementException a) {
			Assert.fail("Repository with this name doesn't exists");
		}
		Assert.assertEquals(steps.getPageTitle(), steps.getUserName() + "/" + repositoryName);
	}

	@Test(description = "Add a bio text")
	public void addBio()
	{
		steps.loginGithub(USERNAME, PASSWORD);
		Assert.assertEquals(USERNAME, steps.getLoggedInUserName());
		steps.addBio(sometext);
		Assert.assertEquals(steps.getBioText(), sometext, "Wrong bio text");
	}

	@Test(description = "Delete existing repository")
	public void deleteExistingRepository()
	{
		steps.loginGithub(USERNAME, PASSWORD);
		Assert.assertEquals(USERNAME, steps.getLoggedInUserName());
		steps.openRepositoryPage(repositoryName);
		steps.deleteRepository(repositoryName, PASSWORD);
		Assert.assertFalse(steps.isRepoExists(repositoryName));
	}

	@AfterMethod(description = "Stop Browser")
	public void stopBrowser()
	{
		steps.closeBrowser();
	}

}

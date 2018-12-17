package com.epam.ta.pages;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RepositoryPage extends AbstractPage {

    private String base_url;
    private final Logger logger = LogManager.getRootLogger();
    private String repositoryName;
    private String username;

    @FindBy(xpath = "//li[@class='Box-row'][3]//summary[@class='btn btn-danger boxed-action']")
    private WebElement deleteRepositoryLink;

    @FindBy(xpath = "//input[@name='verify']")
    private WebElement inputRepositoryNameField;

    @FindBy(xpath = "//form[@class='js-normalize-submit']/button[text()='I understand the consequences, delete this repository']")
    private WebElement confirmDeleteRepositoryButton;

    @FindBy(xpath = "//div[@class='auth-form-body mt-3']/input[@type='password']")
    private WebElement inputPasswordField;

    @FindBy(xpath = "//div[@class='auth-form-body mt-3']/button[@type='submit']")
    private WebElement confirmPasswordButton;

    public RepositoryPage(WebDriver driver, String username, String repositoryName)
    {
        super(driver);
        PageFactory.initElements(this.driver, this);
        this.username = username;
        this.repositoryName = repositoryName;
        base_url = "http://www.github.com/" + username + "/" + repositoryName;
    }

    public void clickOnDeleteRepository(){
        driver.findElement(By.xpath("//nav[@aria-label='Repository']/a[@href='/"+ username +"/" + repositoryName + "/settings']")).click();
        deleteRepositoryLink.click();
    }

    public void confirmDeleteRepositoryWithRepositoryName(){
        inputRepositoryNameField.sendKeys(repositoryName);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmDeleteRepositoryButton);
    }

    //для подтверждения удаления
    public void enterPassword(String password){
        inputPasswordField.sendKeys(password);
    }

    public void clickConfirmPasswordButton() {
        confirmPasswordButton.click();
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    @Override
    public void openPage()
    {
        driver.navigate().to(base_url);
    }
}



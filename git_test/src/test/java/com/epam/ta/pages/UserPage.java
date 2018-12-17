package com.epam.ta.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserPage extends AbstractPage{

    private String base_url;
    private String username;

    @FindBy(xpath = "//div[@role='search']/input[@data-query-name='q']")
    private WebElement inputFindRepositoryField;

    @FindBy(xpath = "//div[@class='p-note user-profile-bio js-user-profile-bio mb-3']/button[@class='btn width-full js-user-profile-bio-toggle']")
    private WebElement addEditBioButton;

    @FindBy(xpath = "//textarea[@aria-label='Add a bio']")
    private WebElement addBioTextArea;

    @FindBy(xpath = "//button[text()='Save']")
    private WebElement saveBioButton;

//    @FindBy(xpath = "//div[@class='p-note user-profile-bio js-user-profile-bio mb-3']/button[text()='Edit bio']")
//    private WebElement editBioButton;

    @FindBy(xpath = "//div[@class='d-inline-block mb-3 js-user-profile-bio-contents']")
    private WebElement bioTextField;

    public UserPage(WebDriver driver, String username)
    {
        super(driver);
        PageFactory.initElements(this.driver, this);
        this.username = username;
        base_url = "http://www.github.com/" + username;
    }

    public void findRepositoryAndOpenRepositoryPage(String repositoryName) {
        inputFindRepositoryField.sendKeys(repositoryName);
        try {
            System.out.println(username + "/" + repositoryName);
            driver.findElement(By.xpath("//div[@class='Box-body']//a[@href='/" + username + "/" + repositoryName + "']")).click();
        } catch (NoSuchElementException a) {
            throw new NoSuchElementException("No such repo");
        }
    }

    public boolean isRepositoryExists(String repositoryName) {
        inputFindRepositoryField.sendKeys(repositoryName);
        try {
            driver.findElement(By.xpath("//div[@class='Box-body']//a[@href='/" + username + "/" + repositoryName + "']")).click();
        } catch (NoSuchElementException a) {
            return false;
        }
        return true;
    }

    public void addBio(String bio) {
        openPage();
        try{
            getBioText();
        } catch (NoSuchElementException a) {
            addEditBioButton.click();
            addBioTextArea.sendKeys(bio);
            saveBioButton.click();
        }
        addEditBioButton.click();
        addBioTextArea.clear();
        addBioTextArea.sendKeys(bio);
        saveBioButton.click();
    }

    public String getBioText() {
        return bioTextField.getText();
    }

    @Override
    public void openPage()
    {
        driver.navigate().to(base_url);
    }

}

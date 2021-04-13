package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class ProjectCreationCRMTest {
    private static WebDriver driver;
    private static final String LOGIN_PAGE_URL = "https://crm.geekbrains.space/user/login";
    private static final String STUDENT_LOGIN = "Applanatest";
    private static final String STUDENT_PASSWORD = "Student2020!";


    @BeforeAll
    public static void SetDriver(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void verifyAuthorization(){

        driver.get(LOGIN_PAGE_URL);

        WebElement loginTextInput = driver.findElement(By.id("prependedInput"));
        loginTextInput.sendKeys(STUDENT_LOGIN);

        WebElement passwordTextInput = driver.findElement(By.id("prependedInput2"));
        passwordTextInput.sendKeys(STUDENT_PASSWORD);

        // JSExecutor
        WebElement loginButton = driver.findElement(By.id("_submit"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click();", loginButton);

        String titleText = driver.findElement(By.xpath("//a[contains(text(),'Geekbrains')]")).getText() ;
        Assertions.assertEquals("Geekbrains",(titleText)); //заменить на страницу???
    }

    @Test
    public void goToProject(){
        Actions action = new Actions(driver);
        WebElement menuButton = driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a"));
        WebElement menuDropButton = driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/ul/li[4]/a"));
        action.moveToElement(menuButton).moveToElement(menuDropButton).click().build().perform();

        WebElement createButton = driver.findElement(By.xpath("//div[@id='container']//a[@href='/project/create/']"));
        createButton.click();

        String titleText = driver.findElement(By.xpath("//div[@id='container']//a[@href='/project/create/']")).getText() ;
        Assertions.assertEquals("Создать проект",(titleText));
    }

    @Test
    public void formFilling(){
        WebElement NameInput = driver.findElement(By.name("crm_project[name]"));
        NameInput.sendKeys("test");

        Actions action = new Actions(driver);
        WebElement companyButton = new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='company-container']//a[1]")));
        WebElement companyInput = driver.findElement(By.cssSelector("input.select2-input"));
        action.click(companyButton).sendKeys(companyInput, "1234").build().perform();

        WebElement companyDropButton = new WebDriverWait(driver,10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='select2-drop']/ul[2]/li/div")));
        companyDropButton.click();

        WebElement contactButton = new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='s2id_autogen3']/preceding-sibling::a")));
        WebElement contactInput = driver.findElement(By.cssSelector("input.select2-input"));
        action.click(contactButton).sendKeys(contactInput, "123 123").build().perform();

        WebElement contactDropButton = new WebDriverWait(driver,10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='select2-drop']/ul[2]/li/div")));
        contactDropButton.click();

        Select businessUnitDropDown = new Select(driver.findElement(By.name("crm_project[businessUnit]")));
        businessUnitDropDown.selectByValue("1");

        Select curatorDropDown = new Select(driver.findElement(By.name("crm_project[curator]")));
        curatorDropDown.selectByValue("115");

        Select projectManagerDropDown = new Select(driver.findElement(By.name("crm_project[rp]")));
        projectManagerDropDown.selectByValue("58");

        Select administratorDropDown = new Select(driver.findElement(By.name("crm_project[administrator]")));
        administratorDropDown.selectByValue("6");

        Select managerDropDown = new Select(driver.findElement(By.name("crm_project[manager]")));
        managerDropDown.selectByValue("5");

        WebElement saveButton = driver.findElement(By.xpath("(//button[@type='submit'])[4]"));
        saveButton.click();
    }



    @AfterAll
    public static void closeDriver() {
        driver.close();
    }
}
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class DiaryDeleteEntry {
    private static WebDriver driver;
    private static final String LOGIN_PAGE_URL = "https://diary.ru/user/login";
    private static final String STUDENT_LOGIN = "testUser2021";
    private static final String STUDENT_PASSWORD = "SCbW4It5Mp";

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get(LOGIN_PAGE_URL);

        WebElement loginTextInput = driver.findElement(By.id("loginform-username"));
        loginTextInput.sendKeys(STUDENT_LOGIN);

        WebElement passwordTextInput = driver.findElement(By.id("loginform-password"));
        passwordTextInput.sendKeys(STUDENT_PASSWORD);

        WebElement loginButton = driver.findElement(By.name("login-button"));
        loginButton.click();

        WebElement menuButton = driver.findElement(By.id("myCommunityLink"));
        menuButton.click();

        WebElement delButton = driver.findElement(By.xpath("//span[2]/a[3]/span"));
        delButton.click();

        WebElement confirmButton = driver.findElement(By.xpath("//div[2]/div[2]/button"));
        confirmButton.click();

        driver.quit();
    }
}
package tau.lab5.selenium;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.common.io.Files;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import tau.lab5.selenium.LoginPage;
import tau.lab5.selenium.StartPage;

import static org.junit.Assert.*;

public class SeleniumTest {
    private static WebDriver driver;
    WebElement element;
    private StartPage startPage;
    private LoginPage loginPage;
    String chromeLocalComputerAddress = "C:/WebDrivers/chromedriver_win32/chromedriver.exe";
    private static String chromeWebdriverInProject = "WebDrivers/chromedriver_win32/chromedriver.exe";

    @BeforeClass
    public static void driverSetup() {
        System.setProperty("webdriver.chrome.driver", chromeWebdriverInProject);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MICROSECONDS);
        driver.manage().window().setSize(new Dimension(300, 700));
    }

    @Before
    public void before() {
        startPage = new StartPage(driver);
        loginPage = new LoginPage(driver);

    }

    @Test
    public void homePage() throws IOException {
        driver.get("http://automationpractice.com");
        driver.findElement(By.cssSelector(".login")).click();

        assertEquals("Sign in", driver.findElement(By.cssSelector("#SubmitLogin")).getText());
        if (driver instanceof TakesScreenshot) {
            File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(f,
						new File("build/homePage.png"));
		}
	}

	@Test
	public void bestSellersCount() {
        startPage.open();
	    startPage.clickBestSellers();
	    assertEquals(7,
                startPage.getProducts().size());
    }

    @Test
    public void loginIncorrect() {
        loginPage.open();
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
    }

	@AfterClass
	public static void cleanp() {
		driver.quit();
	}

}
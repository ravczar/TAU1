package tau.lab5.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;


public class SeleniumTest {
    private static WebDriver driver;
    String chromeLocalComputerAddress = "C:/WebDrivers/chromedriver_win32/chromedriver.exe";
    private static String chromeWebdriverInProject = "WebDrivers/chromedriver_win32/chromedriver.exe";

    @BeforeClass
    public static void driverSetup(){
        System.setProperty(
            "webdriver.chrome.driver",
            chromeWebdriverInProject);  
        WebDriver driver = new ChromeDriver();
    }

    @Test
    public void firstTest() {
        System.out.println("Test 1 odpalono");
        assertTrue(true);
    }

    @Test
    public void secondTest() {
        System.out.println("Test 2 odpalono");
        assertTrue(true);
    }

}
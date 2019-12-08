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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tau.lab5.selenium.LoginPage;
import tau.lab5.selenium.StartPage;

import static org.junit.Assert.*;

public class SeleniumTest {
    private static WebDriver driver;
    WebElement element;
    private StartPage startPage;
    private LoginPage loginPage;
    private final String existingAccountEmail = "admin@admin.pl";
    private final String existingAccountPass = "admin";
    String chromeLocalComputerAddress = "C:/WebDrivers/chromedriver_win32/chromedriver.exe";
    String firefoxLocalComputerAddress = "C:/WebDrivers/geckodriver-v0.26/geckodriver.exe";
    private static String chromeWebdriverInProject = "WebDrivers/chromedriver_win32/chromedriver.exe";
    //private static String firefoxWebdriverInProject = "WebDrivers/geckodriver-v0.26/geckodriver.exe";

    // login Testcase
    private final String emailAndPasswordNotEnteredAtAll = "An email address required.";
    private final String wrongEmailOrPasswordPropmpt = "Authentication failed.";
    private final String emailEnteredButPasswordFieldEmpty = "Password is required.";
    private final String passwordEnteredButEmailFieldEmpty = "An email address required.";
    private final String invalidEmailEntered = "Invalid email address.";

    /*
     * Show where
     */
    @BeforeClass
    public static void driverSetup() {
        System.setProperty("webdriver.chrome.driver", chromeWebdriverInProject);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MICROSECONDS);
        driver.manage().window().setSize(new Dimension(300, 700));
    }

    /*
    *   Create two instances of website / 1) front page 2) login page
    */
    @Before
    public void before() {
        startPage = new StartPage(driver);
        loginPage = new LoginPage(driver);

    }

    @AfterClass
	public static void cleanp() {
		driver.quit();
	}

    /*
    *
    * Test Division
    *
    */

    /* 
    * Selenium tries to open new Chrome tab and locate a Button with class ".login",
    * than click it and wait for a while, 
    * after await, it locates a button to Sign in and check if it contains text 'Sign in'
    * if succeded take screeenshot and dump it into 'build' folder
    */
    @Test
    public void testIfRedirectionToLoginPage_fromStartPage_WorksAndLoginPageContainsButtonLogin() throws IOException {
        final String loginButtonOnLoginPageTextValue = "Sign in";
        startPage.open();
        startPage.clickLoginButton(); //driver.findElement(By.cssSelector(".login")).click();
        assertEquals("Przycisk logowania na stronie logowania nie ma tekstu 'Sign in'",
        loginButtonOnLoginPageTextValue, 
            //driver.findElement(By.cssSelector("#SubmitLogin")).getText()
            startPage.findElementAndReturnItsText("#SubmitLogin")
            );
        if (driver instanceof TakesScreenshot) {
            final File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(f,
						new File("build/homePage.png"));
		}
    }
    
    /*
    *   From StartPage, try switching to login page and locate CreateAccount button. 
    *   If create new user button is located click on it and question enetering registration form,
    *   by simply finding it's submit button
    */
    @Test
    public void testIfRedirectionToLoginPage_fromStartPage_WorksAndIfClickingCreateAnAccountDisplayFormWithSubmitButton() throws IOException {
        final String someEmailAddress = "email27@wp.pl";
        final String registerButtonOnLoginPageTextValue = "Create an account";
        startPage.open();
        startPage.clickLoginButton();
        startPage.waitUntilElemntWithGivenCssSelectorIsVisible("#SubmitCreate");
        assertEquals("Przycisk rejestracji na stronie logowania nie ma tekstu 'Create an account'",
            registerButtonOnLoginPageTextValue, 
            startPage.findElementAndReturnItsText("#SubmitCreate")
            );
        startPage.setCreateEmailAndPressEnter(someEmailAddress);
        startPage.clickCreateButton();
        startPage.waitUntilElemntWithGivenCssSelectorIsVisible("#submitAccount");
        String buttonContainText = driver.findElement(By.id("submitAccount")).getText();
        String buttonShouldContain = "Register";
        assertEquals("Button does not contain text: " + buttonShouldContain ,
            buttonShouldContain, 
            buttonContainText
            );
    }

    /*
    *  1) Starting with LoginPage, test enters new_user e-mail address int input field, than presses enter,
    *  2) test clicks 'Create an Account' button
    *  3) test awaits presence of registration form
    *  4) test confirms that button for submiting registration form is present on website. 
    */
    @Test
    public void testIfClickingCreateAnAccountButtonDisplaysNewAccountCreationFormAndSubmitButton() {
        String email = "morawiecki@wp.pl";
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement createAccountButton = driver.findElement(By.id("SubmitCreate"));
        assertEquals("Create an account",createAccountButton.getText() );
        WebElement emailInputForm = driver.findElement(By.id("email_create")); 
        emailInputForm.sendKeys(email);
        emailInputForm.sendKeys(Keys.RETURN);
        createAccountButton.click();
        WebDriverWait waitTime = new WebDriverWait(driver, 5);
        waitTime.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#customer_firstname")));
        waitTime.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#submitAccount")));
        assertEquals("W przycisku submit account nie ma tekstu 'Register' .", "Register", driver.findElement(By.cssSelector("#submitAccount > span")).getText());   
    }

    /*
    *   1) Passing pre registration form an e-mail that already exists
    */
    @Test
    public void testPassingAlreadyRegisteredEmailToPreRegistrationForm(){
        loginPage.open();
        loginPage.setCreateEmail(existingAccountEmail);
        loginPage.setCreateEmailPressEnter(Keys.RETURN);
        loginPage.pressCreateAccountButton();
    }

    /*
    *   Passing incomplete/invalid email to pre registration form
    */
    @Test
    public void testPassingInvalidEmailToPreRegistrationForm(){
        //driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
    }

    /*
    *   Test counting number of elemnts displayed in 'Best Seller section'
    */

	@Test
	public void bestSellersCount() {
        startPage.open();
	    startPage.clickBestSellers();
	    assertEquals(7,
                startPage.getProducts().size());
    }

    // Test supposed to Succeed when given no email and no password. Prompt: "An email address required."
    @Test
    public void loginIncorrect_NoDataEnteredIntoInputFields() {
        loginPage.open();
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ emailAndPasswordNotEnteredAtAll
            ,emailAndPasswordNotEnteredAtAll, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }

    // Test supposed to Succed when given 1) wrong Email, 2) wrong Password. Checking if Error prompt apears
    @Test
    public void loginIncorrect_WrongEmial_And_Or_WrongPasswordEntered() {
        String email = "fake_email@wp.pl";
        String password = "fake_password";
        loginPage.open();
        loginPage.setLoginEmail(email);
        loginPage.setLoginPassword(password);
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ wrongEmailOrPasswordPropmpt
            ,wrongEmailOrPasswordPropmpt, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }

    // Test supposed to Succed if giving wrong Email and left password field empty, thus we receive Prompt "Password is required."
    @Test
    public void loginIncorrect_JustEmail_And_NoPasswordEnterd() {
        String email = "fake_email@wp.pl";
        String password = "";

        loginPage.open();
        loginPage.setLoginEmail(email);
        loginPage.setLoginPassword(password);
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ emailEnteredButPasswordFieldEmpty
            ,emailEnteredButPasswordFieldEmpty, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }
    // Test supposed to Succeed if giving NO Email and entered only password, thus we receive Prompt "An email address required."
    @Test
    public void loginIncorrect_EmptyEmail_And_SomePasswordEnterd() {
        String email = "";
        String password = "fake_password";

        loginPage.open();
        loginPage.setLoginEmail(email);
        loginPage.setLoginPassword(password);
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ passwordEnteredButEmailFieldEmpty
            ,passwordEnteredButEmailFieldEmpty, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }
    @Test
    public void loginIncorrect_WrongEMailFormatEntered_And_SomePasswordEnterd() {
        String email = "fake_email_no_at_and_domain";
        String password = "fake_password";

        loginPage.open();
        loginPage.setLoginEmail(email);
        loginPage.setLoginPassword(password);
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ invalidEmailEntered
            ,invalidEmailEntered, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }

    @Test
    public void loginIncorrect_WrongEMailFormatEntered_And_NoPasswordEnterd() {
        String email = "fake_email_no_at_and_domain";
        String password = "";

        loginPage.open();
        loginPage.setLoginEmail(email);
        loginPage.setLoginPassword(password);
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ invalidEmailEntered
            ,invalidEmailEntered, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }










}
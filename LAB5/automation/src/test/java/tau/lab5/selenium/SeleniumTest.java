package tau.lab5.selenium;
// https://selenium.dev/documentation/en/webdriver/waits/
// https://testelka.pl/implicit-oraz-explicit-wait/
// https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
// testpro.pl/selenium-ide-testy-automatyczne-dla-poczatkujacych-tutorial/


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.common.io.Files;

import org.junit.After;
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
    private final String invalidEmailAddress = "Rafal Czarnecki";
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
     * Show where is your driver located and open new instance of ChromeDriver
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

    @After
    public void after() {
        driver.quit();
        driverSetup();
    }

    @AfterClass
	public static void cleanup() {
		driver.quit();
	}

    /*
    *
    * Test Division
    *
    */

    /* 
    * 11) Selenium tries to open new Chrome tab and locate a Button with class ".login",
    * than click it and wait for a while, 
    * after await, it locates a button to Sign in and check if it contains text 'Sign in'
    * if succeded take screeenshot and dump it into 'build' folder
    */
    @Test
    public void testIfRedirectionToLoginPage_fromStartPage_WorksAndLoginPageContainsButtonLogin() throws IOException {
        final String loginButtonOnLoginPageTextValue = "Sign in";
        final String loginSiteUrl = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
        startPage.open();
        startPage.clickLoginButton();
        startPage.waitUntilElemntWithGivenCssSelectorIsVisible("#SubmitLogin");
        assertEquals("Przycisk logowania na stronie logowania nie ma tekstu 'Sign in'",
        loginButtonOnLoginPageTextValue, 
            //driver.findElement(By.cssSelector("#SubmitLogin")).getText()
            startPage.findElementAndReturnItsText("#SubmitLogin")
            );
        if (driver instanceof TakesScreenshot) {
            final File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(f,
						new File("build/loginPage.png"));
        }
        assertEquals(loginSiteUrl, startPage.getCurrentSiteUrl());
    }
    
    /*
    *   12) From StartPage, try switching to login page and locate CreateAccount button. 
    *   If create new user button is located click on it and question enetering registration form,
    *   by simply finding it's submit button
    */
    @Test
    public void testIfRedirectionToLoginPage_fromStartPage_WorksAndIfClickingCreateAnAccountDisplayFormWithSubmitButton() throws IOException {
        final String someEmailAddress = "fake_email@wp.pl";
        final String registerButtonOnLoginPageTextValue = "Create an account";
        startPage.open();
        startPage.clickLoginButton();
        startPage.waitUntilElemntWithGivenCssSelectorIsPresent("#SubmitCreate");
        assertEquals("Przycisk rejestracji na stronie logowania nie ma tekstu 'Create an account'",
            registerButtonOnLoginPageTextValue, 
            startPage.findElementAndReturnItsText("#SubmitCreate")
            );
        startPage.setCreateEmailAndPressEnter(someEmailAddress);
        startPage.clickCreateButton();
        startPage.waitUntilElemntWithGivenCssSelectorIsPresent("#submitAccount");
        String buttonContainText = driver.findElement(By.id("submitAccount")).getText();
        String buttonShouldContain = "Register";
        assertEquals("Button does not contain text: " + buttonShouldContain ,
            buttonShouldContain, 
            buttonContainText
            );
    }

    /*
    * 13) Same test as test 12, but in different manner
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
    *   1) Passing pre registration form an e-mail that already exists should return certain error message (failureMsg.val())
    */
    @Test
    public void testPassingAlreadyRegisteredEmailToPreRegistrationForm(){
        String failureMsg = "An account using this email address has already been registered. Please enter a valid password or request a new one.";
        String urlShouldBeWhenFinished = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
        loginPage.open();
        loginPage.setCreateEmail(existingAccountEmail);
        loginPage.setCreateEmailPressEnter(Keys.RETURN);
        loginPage.pressCreateAccountButton();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible("#create_account_error");
        assertEquals("Given Email already exist, message mismatch!  please check test variable 'failureMsg'! ", failureMsg, loginPage.getCreateAccountErrorMessageText());
        assertEquals("Url should remain same as it was at the start of test",urlShouldBeWhenFinished, loginPage.getCurrentSiteUrl() );
    }

    /*
    *  2) Passing incomplete/invalid email to pre registration form should return certain error message (failureMsg.val())
    */
    @Test
    public void testPassingInvalidEmailToPreRegistrationForm(){
        String failureMsg = "Invalid email address.";
        String urlShouldBeWhenFinished = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
        loginPage.open();
        loginPage.setCreateEmail(invalidEmailAddress);
        loginPage.setCreateEmailPressEnter(Keys.RETURN);
        loginPage.pressCreateAccountButton();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible("#create_account_error");
        assertEquals("Given Email is invalid, message mismatch!  please check test variable 'failureMsg'! ", failureMsg, loginPage.getCreateAccountErrorMessageText());
        assertEquals("Url should remain same as it was at the start of test",urlShouldBeWhenFinished, loginPage.getCurrentSiteUrl() );
    }

    /*
    *   3) Passing empty email input to pre registration form should return certain error message (failureMsg.val())
    */
    @Test
    public void testPassingEmptyEmailToPreRegistrationForm(){
        String failureMsg = "Invalid email address.";
        String empty = "";
        String urlShouldBeWhenFinished = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
        loginPage.open();
        loginPage.setCreateEmail(empty);
        loginPage.setCreateEmailPressEnter(Keys.RETURN);
        loginPage.pressCreateAccountButton();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible("#create_account_error");
        assertEquals("Given Email is invalid, message mismatch!  please check test variable 'failureMsg'! ", failureMsg, loginPage.getCreateAccountErrorMessageText());
        assertEquals("Url should remain same as it was at the start of test",urlShouldBeWhenFinished, loginPage.getCurrentSiteUrl() );
    }

    /*
    *   Test counting number of elemnts displayed in 'Best Seller section' with resolution 300x700
    */

	@Test
	public void bestSellersCount() {
        startPage.open();
	    startPage.clickBestSellers();
	    assertEquals(7,
                startPage.getProducts().size());
    }

    /*
    * 4a) Test supposed to Succeed when given no email and no password. Prompt: "An email address required."
    */
    @Test
    public void loginIncorrect_NoDataEnteredIntoInputFields() {
        loginPage.open();
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ emailAndPasswordNotEnteredAtAll
            ,emailAndPasswordNotEnteredAtAll, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }

    /*
    * 4b) Test supposed to Succeed when given no email and no password. Prompt: "An email address required."
    *       Test with changed window size to 1920 x 800
    */
    @Test
    public void loginIncorrect_NoDataEnteredIntoInputFieldsWindowSize1920x800() {
        loginPage.open();
        loginPage.setWindowSize(1920, 800);
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ emailAndPasswordNotEnteredAtAll
            ,emailAndPasswordNotEnteredAtAll, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }

    /*
    *  5) Test supposed to Succed when given 1) wrong Email, 2) wrong Password. Checking if Error prompt apears
    */
    @Test
    public void loginIncorrect_WrongEmail_And_Or_WrongPasswordEntered() {
        String fakeEmail = "fake_email@wp.pl";
        String fakePassword = "fake_password";
        loginPage.open();
        loginPage.waitUntilElemntWithGivenCssSelectorIsPresent("#SubmitLogin");
        loginPage.setLoginEmail(fakeEmail);
        loginPage.setLoginPassword(fakePassword);
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ wrongEmailOrPasswordPropmpt
            ,wrongEmailOrPasswordPropmpt, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }

    /*
    *  6) Test supposed to Succed when given 1) correct Email, 2) wrong Password. Checking if Error prompt apears
    */
    @Test
    public void loginIncorrect_CorrectEmail_And_Or_WrongPasswordEntered() {
        String fakePassword = "fake_password";
        loginPage.open();
        loginPage.setLoginEmail(existingAccountEmail);
        loginPage.setLoginPassword(fakePassword);
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ wrongEmailOrPasswordPropmpt
            ,wrongEmailOrPasswordPropmpt, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }

    /*
    * 7) Test supposed to Succed if giving wrong Email and left password field empty, thus we receive Prompt "Password is required."
    */ 
    @Test
    public void loginIncorrect_JustEmail_And_NoPasswordEnterd() {
        String email = "fake_email@wp.pl";
        String password = "";

        loginPage.open();
        loginPage.waitUntilElemntWithGivenCssSelectorIsPresent("#SubmitLogin");
        loginPage.setLoginEmail(email);
        loginPage.setLoginPassword(password);
        loginPage.login();
        loginPage.waitUntilElemntWithGivenCssSelectorIsPresent(".alert-danger > p");
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ emailEnteredButPasswordFieldEmpty
            ,emailEnteredButPasswordFieldEmpty, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }
    /*
    * 8) Test supposed to Succeed if giving NO Email and entered only password, thus we receive Prompt "An email address required."
    */
    @Test
    public void loginIncorrect_EmptyEmail_And_SomePasswordEnterd() {
        String email = "";
        String password = "fake_password";

        loginPage.open();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible("#SubmitLogin");
        loginPage.setLoginEmail(email);
        loginPage.setLoginPassword(password);
        loginPage.login();
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ passwordEnteredButEmailFieldEmpty
            ,passwordEnteredButEmailFieldEmpty, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }

    /*
    * 9) Test supposed to Succeed if giving wrong format Email and entered fake password, thus we receive Prompt "Invalid email address."
    */
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
    /*
    * 10) Test supposed to Succeed if giving wrong format Email and NO password entered, thus we receive Prompt "Invalid email address."
    */

    @Test
    public void loginIncorrect_WrongEMailFormatEntered_And_NoPasswordEnterd() {
        String email = "fake_email_no_at_and_domain";
        String password = "";

        loginPage.open();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible("#email");
        loginPage.setLoginEmail(email);
        loginPage.setLoginPassword(password);
        loginPage.login();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible("div.alert-danger");
        assertFalse(loginPage.isLoginSuccessful());
        assertEquals("Recived different promt than expected, should read: "+ invalidEmailEntered
            ,invalidEmailEntered, loginPage.readSiteLoginFailedAuthentitcationPrompt()
            );
    }

    /*
    *   14) Test if existing_user can successfully Sign_in, when login check if specific elemnts exist
    */
    @Test
    public void testIfUserCanLogInCheckWebElements() {
        String expectedButtonText = "Home";
        String expectedUserNameInMenu = "admin admin";
        String expectedSiteUrl = "http://automationpractice.com/index.php?controller=my-account";
        loginPage.open();
        loginPage.setLoginEmail(existingAccountEmail);
        loginPage.setLoginPassword(existingAccountPass);
        loginPage.login();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(".myaccount-link-list");
        String textInElementThatAppeared = loginPage.findElementAndReturnItsText("ul.footer_links > li > a > span");
        String nameInMenuButton = loginPage.findElementAndReturnItsText("a.account > span");
        assertEquals(expectedButtonText, textInElementThatAppeared);
        assertEquals(expectedUserNameInMenu , nameInMenuButton);
        assertEquals(expectedSiteUrl, loginPage.getCurrentSiteUrl());
    }

    /*
    *   15) Test if existing_user can successfully Sign_in, when logged in check if cookie for user created!
    *   Helpful site: https://www.guru99.com/handling-cookies-selenium-webdriver.html
    */
    @Test
    public void testIfUserCanLogInCheckCookie() {
        // user admin@admin.com 
        String expectedCookieName = "PrestaShop-a30a9934ef476d11b6cc3c983616e364";
        String expectedSiteUrl = "http://automationpractice.com/index.php?controller=my-account";
        loginPage.open();
        loginPage.deleteAllCookies();
        assertSame("Cookie set size is != 0. ", 0, loginPage.getAllCookies().size());
        loginPage.setLoginEmail(existingAccountEmail);
        loginPage.setLoginPassword(existingAccountPass);
        loginPage.login();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(".myaccount-link-list");
        // Page will contain only one cookie, weather user logged in or not, but name of cookie differs
        assertSame("Cookie set size is != 1 . ", 1, loginPage.getAllCookies().size());
        // Cookie name after login should be spcific to user name
        assertEquals(expectedCookieName, loginPage.getTextContainedInSpecificCookieName("PrestaShop-a30a9934ef476d11b6cc3c983616e364"));
        assertEquals(expectedSiteUrl, loginPage.getCurrentSiteUrl());
    }

    /*
    *   16) Successful registration test with random email address, after registration redirect to user account profile
    */
    @Test
    public void successNewUserRegistrationTest() {
        String uniqueEmailAddress = loginPage.generateUniqueEmail();
        String expectedUrlAddress = "http://automationpractice.com/index.php?controller=my-account";
        loginPage.open();
        loginPage.setCreateEmail(uniqueEmailAddress);
        loginPage.pressCreateAccountButton();
        loginPage.waitUntilElemntWithGivenCssSelectorIsPresent("#submitAccount");
        String buttonContainText = driver.findElement(By.id("submitAccount")).getText();
        String buttonShouldContain = "Register";
        // checking if submit form button appears at the bottom of site
        assertEquals("Button does not contain text: " + buttonShouldContain ,
            buttonShouldContain, 
            buttonContainText
            );
        // check if proper input field automaticly inherit email address that is correct.
        assertEquals(uniqueEmailAddress, loginPage.getRegistrationFormEmailInputValue());
        // Fill all neccessary fields.
        String name = "Jan";
        String surname = "Maria";
        loginPage.setFirstName(name);
        loginPage.setLastName(surname);
        loginPage.setPassword("admin");
        loginPage.setFirstNamePrim("Jan");
        loginPage.setLastNamePrim("Maria");
        loginPage.setAddres("Dlugie Ogrody");
        loginPage.setCity("Gdańsk");
        loginPage.setState("Alabama");
        loginPage.setPostCode("80765");
        loginPage.setCountry("United States");
        loginPage.setMobilePhone("123456789");
        loginPage.setAlias("Superb");
        // submit form
        loginPage.submitCreateUserForm();
        // Wait until page loads up
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(".myaccount-link-list");
        // check if button appeared on website
        assertEquals("Element - button Home cannot be found !","Home", loginPage.findElementAndReturnItsText("ul.footer_links > li > a > span"));
        // Check if proper cookie created
        String expectedUserNameInMenu = name + " " + surname;
        String nameInMenuButton = loginPage.findElementAndReturnItsText("a.account > span");
        assertEquals(expectedUserNameInMenu , nameInMenuButton);
        assertEquals(expectedUrlAddress, loginPage.getCurrentSiteUrl());
    }

    /*
    *   17) Successful registration test with random email address, after registration redirect to user account profile (cookies version)
    */
    @Test
    public void successNewUserRegistrationTestEvaluateByCookies() {
        String expectedCookieName = "PrestaShop-a30a9934ef476d11b6cc3c983616e364";
        String expectedSiteUrl = "http://automationpractice.com/index.php?controller=my-account";
        String uniqueEmailAddress = loginPage.generateUniqueEmail();
        String cookieName = "PrestaShop-a30a9934ef476d11b6cc3c983616e364";
        loginPage.open();
        Integer cookieValueBeforeAccountCreation = loginPage.getTextContainedInSpecificCookieValue(cookieName).length();
        loginPage.setCreateEmail(uniqueEmailAddress);
        loginPage.pressCreateAccountButton();
        loginPage.waitUntilElemntWithGivenCssSelectorIsPresent("#submitAccount");
        String buttonContainText = driver.findElement(By.id("submitAccount")).getText();
        String buttonShouldContain = "Register";
        // checking if submit form button appears at the bottom of site
        assertEquals("Button does not contain text: " + buttonShouldContain ,
            buttonShouldContain, 
            buttonContainText
            );
        // check if proper input field automaticly inherit email address that is correct.
        assertEquals(uniqueEmailAddress, loginPage.getRegistrationFormEmailInputValue());
        // Fill all neccessary fields.
        String name = "Jan";
        String surname = "Maria";
        loginPage.setFirstName(name);
        loginPage.setLastName(surname);
        loginPage.setPassword("admin");
        loginPage.setFirstNamePrim("Jan");
        loginPage.setLastNamePrim("Maria");
        loginPage.setAddres("Dlugie Ogrody");
        loginPage.setCity("Gdańsk");
        loginPage.setState("Alabama");
        loginPage.setPostCode("80765");
        loginPage.setCountry("United States");
        loginPage.setMobilePhone("123456789");
        loginPage.setAlias("Superb");
        // submit form
        loginPage.submitCreateUserForm();
        // Wait until page loads up
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(".myaccount-link-list");
        // check cookie names and values.length() - if user createt cookie.value() will be bigger than before
        assertSame("Cookie set size is != 1 . ", 1, loginPage.getAllCookies().size());
        Integer cookieValueAfterAccountCreation = loginPage.getTextContainedInSpecificCookieValue(cookieName).length();
        assertEquals(expectedCookieName, loginPage.getTextContainedInSpecificCookieName(cookieName));
        assertNotEquals(cookieValueBeforeAccountCreation, cookieValueAfterAccountCreation);
        assertEquals(expectedSiteUrl, loginPage.getCurrentSiteUrl());  
    }

    /*
    *   18) Test complex user_registration_form input_field validation
    */
    @Test
    public void testObligatoryFieldsInRegistrationForm() {
        String uniqueEmailAddress = loginPage.generateUniqueEmail();
        String errorListSelector = "div.alert > ol >li";
        loginPage.open();
        loginPage.setCreateEmail(uniqueEmailAddress);
        loginPage.pressCreateAccountButton();
        loginPage.waitUntilElemntWithGivenCssSelectorIsPresent("#submitAccount");
        String buttonContainText = driver.findElement(By.id("submitAccount")).getText();
        String buttonShouldContain = "Register";
        // checking if submit form button appears at the bottom of site (so that we see registration properform)
        assertEquals("Button does not contain text: " + buttonShouldContain ,
            buttonShouldContain, 
            buttonContainText
            );
        
        String filerFirstName = "firstname";
        String filerLastName = "lastname";
        String filterPasswd  = "passwd";
        String filterAddress = "address1";
        String filterCity = "city";
        String filterPostCode = "Zip/Postal";
        String filterState = "country";
        String filterPhone = "phone";

        /* check First Name */
        loginPage.setFirstName("12345");
        loginPage.submitCreateUserForm();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(errorListSelector);
        ArrayList<String> arrayOfErrors = loginPage.getRegistrationErrorMessageArray();
        Boolean firstNameNotValid = loginPage.checkIfTypeOfErrorOnTheListOfErrors(arrayOfErrors, filerFirstName);
        assertEquals("Firstname is valid", true , firstNameNotValid);

        /* check Last Name */
        loginPage.setLastName("12345");
        loginPage.submitCreateUserForm();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(errorListSelector);
        arrayOfErrors = loginPage.getRegistrationErrorMessageArray();
        Boolean lastNameNotValid = loginPage.checkIfTypeOfErrorOnTheListOfErrors(arrayOfErrors, filerLastName);
        assertEquals("Lastname is valid", true , lastNameNotValid);

        /* check Last Name */
        loginPage.setPassword("");
        loginPage.submitCreateUserForm();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(errorListSelector);
        arrayOfErrors = loginPage.getRegistrationErrorMessageArray();
        Boolean passwordNotValid = loginPage.checkIfTypeOfErrorOnTheListOfErrors(arrayOfErrors, filterPasswd);
        assertEquals("Password is valid", true , passwordNotValid);

        /* check Address */
        loginPage.setAddres("");
        loginPage.submitCreateUserForm();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(errorListSelector);
        arrayOfErrors = loginPage.getRegistrationErrorMessageArray();
        Boolean addressNotValid = loginPage.checkIfTypeOfErrorOnTheListOfErrors(arrayOfErrors, filterAddress);
        assertEquals("Address is valid", true , addressNotValid);

        /* check City */
        loginPage.setCity("");
        loginPage.submitCreateUserForm();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(errorListSelector);
        arrayOfErrors = loginPage.getRegistrationErrorMessageArray();
        Boolean cityNotValid = loginPage.checkIfTypeOfErrorOnTheListOfErrors(arrayOfErrors, filterCity);
        assertEquals("City is valid", true , cityNotValid);

        /* check Post/ZIP */
        loginPage.setPostCode("99999999999");
        loginPage.submitCreateUserForm();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(errorListSelector);
        arrayOfErrors = loginPage.getRegistrationErrorMessageArray();
        Boolean postCodeNotValid = loginPage.checkIfTypeOfErrorOnTheListOfErrors(arrayOfErrors, filterPostCode);
        assertEquals("PostCode is valid", true , postCodeNotValid);

        /* check State */
        loginPage.setState("-");
        loginPage.submitCreateUserForm();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(errorListSelector);
        arrayOfErrors = loginPage.getRegistrationErrorMessageArray();
        Boolean stateNotValid = loginPage.checkIfTypeOfErrorOnTheListOfErrors(arrayOfErrors, filterState);
        assertEquals("State is valid", true , stateNotValid);

        /* check Phone */
        loginPage.setMobilePhone("abcde");
        loginPage.submitCreateUserForm();
        loginPage.waitUntilElemntWithGivenCssSelectorIsVisible(errorListSelector);
        arrayOfErrors = loginPage.getRegistrationErrorMessageArray();
        Boolean phoneNotValid = loginPage.checkIfTypeOfErrorOnTheListOfErrors(arrayOfErrors, filterPhone);
        assertEquals("MobilePhone is valid", true , phoneNotValid);

    }



}
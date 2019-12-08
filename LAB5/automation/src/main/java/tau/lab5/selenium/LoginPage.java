package tau.lab5.selenium;
import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {
    private final WebDriver driver;

    @FindBy(id = "email")
    WebElement loginInput;

    @FindBy(id = "email_create")
    WebElement createEmailInput;

    @FindBy(id = "SubmitCreate")
    WebElement createAccountButton;

    @FindBy(id = "submitAccount")
    WebElement submitCreateButton;

    @FindBy(css = "#create_account_error > ol > li")
    WebElement createAccountErrorMsg;

    @FindBy(css = "#passwd")
    WebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"SubmitLogin\"]")
    WebElement loginButton;

    @FindBy(css = ".alert-danger > ol > li")
    WebElement siteLoginFailedAuthentitcationPrompt;

    // Complex registration form fields
    @FindBy(css = "input#customer_firstname")
    WebElement firstName;

    @FindBy(css = "input#customer_lastname")
    WebElement lastName;

    @FindBy(css = "input#passwd")
    WebElement password;

    @FindBy(css = "input#firstname")
    WebElement firstNamePrim;

    @FindBy(css = "input#lastname")
    WebElement lastNamePrim;

    @FindBy(css = "input#address1")
    WebElement address;

    @FindBy(css = "input#city")
    WebElement city;
    
    @FindBy(css = "select#id_state")
    WebElement state;
        
    @FindBy(css = "input#postcode")
    WebElement postCode;

    @FindBy(css = "select#id_country")
    WebElement country;

    @FindBy(css = "input#phone_mobile")
    WebElement mobilePhone;
    
    @FindBy(css = "input#alias")
    WebElement alias;

    // Complex form end.

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        PageFactory.initElements(driver, this);
    }

    public void login() {
        loginButton.click();
    }

    public void pressCreateAccountButton() {
        createAccountButton.click();
    }

    public void waitUntilElemntWithGivenCssSelectorIsPresent(String selector) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
    }

    public void waitUntilElemntWithGivenCssSelectorIsVisible(String selector) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
    }

    public String getTextContainedInSpecificCookieName(String cookieName){
        return driver.manage().getCookieNamed(cookieName).getName(); 
    }

    public String getTextContainedInSpecificCookieValue(String cookieName){
        return driver.manage().getCookieNamed(cookieName).getValue();
    }

    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies(); 
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public String getRegistrationFormEmailInputValue() {
        return loginInput.getAttribute("value");
    }

    public String generateUniqueEmail () {
        String a = "karmazynowyMsciciel";
        String b = "";
        String c = "@wp.pl";
        Integer x = (int)(Math.random() * ((1000 - 0) + 1)) + 0;
        b = x.toString();

        return a+b+c;
    }

    public void submitCreateUserForm() {
        submitCreateButton.click();
    }

    public ArrayList<String> getRegistrationErrorMessageArray() {
        ArrayList<String> errorList = new ArrayList<>();
        //driver.findElements(By.cssSelector("div.alert > ol >li"));
        for(WebElement element : driver.findElements(By.cssSelector("div.alert > ol >li")))							
            {	
                errorList.add(element.getText());
                //System.out.println(element.getText() );    
            }			
        return errorList;
    }

    public boolean checkIfTypeOfErrorOnTheListOfErrors (ArrayList<String> list, String filter) {
        Boolean errorExist = false;
        for (String error : list) {
            if (error.contains(filter)) {
                System.out.println("This pass the filter: " + error);
                errorExist = true;
            }
        }
        return errorExist;
    }

    
    public void setWindowSize(Integer X, Integer Y) {
        driver.manage().window().setSize(new Dimension(X, Y));
    }




    /* GETTERS */
    public WebElement getLoginButton() {
        return loginButton;
    }

    public WebElement getLoginInput() {
        return loginInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public boolean isLoginSuccessful() {
        return driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/ol/li")) == null;
    }

    public String readSiteLoginFailedAuthentitcationPrompt() {
        return siteLoginFailedAuthentitcationPrompt.getText();
    }

    public String findElementAndReturnItsText(String element) {
        return driver.findElement(By.cssSelector(element)).getText();
    }

    public WebElement getCreateAccountButton() {
        return createAccountButton;
    }

    public String getCreateAccountErrorMessageText() {
        return createAccountErrorMsg.getText();
    }

    public String getCurrentSiteUrl() {
        return driver.getCurrentUrl();
    }

    // Complex reg form
    public WebElement getFirstName() {
        return firstName;
    }
    public WebElement getlastName() {
        return lastName;
    }
    public WebElement getPassword() {
        return password;
    }
    public WebElement getFirstNamePrim() {
        return firstNamePrim;
    }
    public WebElement getLastNamePrim() {
        return lastNamePrim;
    }
    public WebElement getAddres() {
        return address;
    }
    public WebElement getCity() {
        return city;
    }
    public WebElement getState() {
        return state;
    }
    public WebElement getPostCode() {
        return postCode;
    }
    public WebElement getCountry() {
        return country;
    }
    public WebElement getMobilePhone() {
        return mobilePhone;
    }
    public WebElement getAlias() {
        return alias;
    }


    /* SETTERS */
    public void setLoginEmail(String email) {
        loginInput.sendKeys(email);
        //loginInput.sendKeys(Keys.RETURN);
    }

    public void setLoginPassword(String password) {
        passwordInput.sendKeys(password);
        //loginInput.sendKeys(Keys.RETURN);
    }

    public void setCreateEmail (String email){
        createEmailInput.sendKeys(email);
    }

    public void setCreateEmailPressEnter (Keys key){
        createEmailInput.sendKeys(key);
    }
    // Complex reg form setters
    public void setFirstName(String FirstName) {
        firstName.sendKeys(FirstName);
    }
    public void setLastName(String LastName) {
        lastName.sendKeys(LastName);
    }
    public void setPassword(String Password) {
        password.sendKeys(Password);
    }
    public void setFirstNamePrim(String FirstNamePrim) {
        firstNamePrim.sendKeys(FirstNamePrim);
    }
    public void setLastNamePrim(String LastNamePrim) {
        lastNamePrim.sendKeys(LastNamePrim);
    }
    public void setAddres(String Address) {
        address.sendKeys(Address);
    }
    public void setCity(String City) {
        city.sendKeys(City);
    }
    public void setState(CharSequence State) {
        state.sendKeys(State);
    }
    public void setPostCode(String PostCode) {
        postCode.sendKeys(PostCode);
    }
    public void setCountry(CharSequence Country) {
        country.sendKeys(Country);
    }
    public void setMobilePhone(String PhoneNumber) {
        mobilePhone.sendKeys(PhoneNumber);
    }
    public void setAlias(String Alias) {
        alias.sendKeys(Alias);
    }



}

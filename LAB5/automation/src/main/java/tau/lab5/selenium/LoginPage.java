package tau.lab5.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage {
    private final WebDriver driver;

    @FindBy(id = "email")
    WebElement loginInput;

    @FindBy(id = "email_create")
    WebElement createEmailInput;

    @FindBy(id = "SubmitCreate")
    WebElement createAccountButton;

    @FindBy(css = "#passwd")
    WebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"SubmitLogin\"]")
    WebElement loginButton;

    @FindBy(css = ".alert-danger > ol > li")
    WebElement siteLoginFailedAuthentitcationPrompt;

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

    /* SETTERS */
    public void setLoginEmail(String email) {
        loginInput.sendKeys(email);
    }

    public void setLoginPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void setCreateEmail (String email){
        createEmailInput.sendKeys(email);
    }
    public void setCreateEmailPressEnter (Keys key){
        createEmailInput.sendKeys(key);
    }

}

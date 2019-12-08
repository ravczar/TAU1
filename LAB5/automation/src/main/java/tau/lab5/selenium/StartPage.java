package tau.lab5.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;


public class StartPage {
    private final WebDriver driver;

    @FindBy(xpath = "//*[@id=\"home-page-tabs\"]/li[2]/a")
    WebElement bestSellersLink;

    @FindBy(id = "email_create")
    WebElement createInput;

    public StartPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getBestSellers() {
        return bestSellersLink;
    }

    public void clickBestSellers() {
        getBestSellers().click();
    }

    public void clickLoginButton() {
        this.driver.findElement(By.cssSelector(".login")).click();
    }

    public void clickCreateButton() {
        this.driver.findElement(By.id("SubmitCreate")).click();
    }

    public List<WebElement> getProducts() {
        return driver.findElement(By.cssSelector("#blockbestsellers")).findElements(By.tagName("li"));
    }

    public void open() {
        driver.get("http://automationpractice.com");
        PageFactory.initElements(driver, this);
    }

    public String findElementAndReturnItsText(String element) {
        String text = driver.findElement(By.cssSelector(element)).getText();
        return text;
    }

    public String getCurrentAddressOfTheDriver() {
        return driver.getCurrentUrl();
    }

    public void setCreateEmailAndPressEnter(String email) {
        createInput.sendKeys(email);
        createInput.sendKeys(Keys.RETURN);
    }

    public void waitUntilElemntWithGivenCssSelectorIsPresent(String selector) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
    }

    public void waitUntilElemntWithGivenCssSelectorIsVisible(String selector) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
    }

    public String getCurrentSiteUrl() {
        return driver.getCurrentUrl();
    }

}

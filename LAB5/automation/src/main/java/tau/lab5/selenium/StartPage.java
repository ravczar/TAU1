package tau.lab5.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class StartPage {
    private final WebDriver driver;

    @FindBy(xpath = "//*[@id=\"home-page-tabs\"]/li[2]/a")
    WebElement bestSellersLink;

    public StartPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getBestSellers() {
       return bestSellersLink;
    }
    public void clickBestSellers() {
        getBestSellers().click();
    }

    public List<WebElement> getProducts() {
        // UWAGA: Warto zweryfikować, czy faktycznie elementy są wyświetlane
        // Można to zrobić na przykład za pomocą .isDisplayed(); 
        return driver.findElement(By.cssSelector("#blockbestsellers")).findElements(By.tagName("li"));
    }

    public void open() {
        driver.get("http://automationpractice.com");
        PageFactory.initElements(driver, this);
    }
}

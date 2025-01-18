package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;

    // Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // Initialize web elements
    }

    // Reusable for clicking
    public void click(WebElement element) {
        element.click();
    }

    // Reusable for entering text
    public void enterText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    // Reusable for getting text
    public String getText(WebElement element) {
        return element.getText();
    }
}
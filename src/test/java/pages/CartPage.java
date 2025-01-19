package pages;

import org.openqa.selenium.By;
// import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class CartPage extends BasePage {
    @FindBy(id = "sub_total")
    private WebElement cartPagePriceTotal;

    @FindBy(css = ".button.plus")
    private WebElement plusButton;

    @FindBy(css = ".button.minus")
    private WebElement minusButton;

    @FindBy(css = ".input-number.text-center")
    private WebElement inputField;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickPlusButton() {
        plusButton.click();
    }

    public void clickMinusButton() {
        minusButton.click();
    }

    public void setProductQuantity(String quantity) {
        // ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", inputField, quantity); This code bypasses alert
        inputField.sendKeys("\b"); // Initial value is single digit
        inputField.sendKeys(quantity);
        inputField.sendKeys("\n"); // Simulate enter key to trigger change
    }


    // Remove Button
    @FindBy(className ="btn-cart-remove")
    private WebElement removeButton;
    public void clickRemoveButton() {
        removeButton.click();
    }

    public boolean isRemoveButtonPresent() {
        try {
            WebElement button = driver.findElement(By.className("btn-cart-remove"));
            return button.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getTotalPrice() {
        String currencyString = cartPagePriceTotal.getText();
        String cleanedString = currencyString.replace("Rp", "").replace(",", "").trim();
        return Integer.parseInt(cleanedString);
    }
}


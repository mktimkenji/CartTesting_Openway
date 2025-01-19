package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends BasePage {
    private final By productRows = By.className("row-cart-product");
    private final By subTotalPrice = By.xpath("//li[contains(text(), 'Sub-Total')]/span");

    @FindBy(xpath = "//div[@class='dropdown-cart-header']/span")
    private WebElement cartItemCount;

    @FindBy(xpath = "//div[@class='total']//span[@class='total-amount']")
    private WebElement cartTotalAmount;

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

    public List<WebElement> getProductRows() {
        return driver.findElements(productRows);
    }

    public String getSubTotalPrice() {
        return driver.findElement(subTotalPrice).getText();
    }

    public String getCartItemCount() {
        return cartItemCount.getText();
    }

    public String getCartTotalAmount() {
        return cartTotalAmount.getText();
    }

    public String getProductName(WebElement productRow) {
        return productRow.findElement(By.cssSelector("p.product-name a")).getText();
    }

    public int getProductPrice(WebElement productRow) {
        String priceText = productRow.findElement(By.xpath(".//div[contains(text(), 'Rp')]")).getText();
        String priceOnly = priceText.split("or")[0].replace("Rp", "").replace(",", "").trim();
        return Integer.parseInt(priceOnly);
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

    public void removeProduct(WebElement productRow) {
        productRow.findElement(By.cssSelector("a.btn-cart-remove")).click();
    }

    // Placeholder for remove button on ProductPageTest
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
}


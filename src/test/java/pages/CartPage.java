package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends BasePage {
    private final By productRows = By.className("row-cart-product");
    private final By subTotalPrice = By.xpath("//li[contains(text(), 'Sub-Total')]/span");

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Get all product rows
    public List<WebElement> getProductRows() {
        return driver.findElements(productRows);
    }

    // Get the subtotal price as displayed
    public String getSubTotalPrice() {
        return driver.findElement(subTotalPrice).getText();
    }

    // Extract product name from a product row
    public String getProductName(WebElement productRow) {
        return productRow.findElement(By.cssSelector("p.product-name a")).getText();
    }

    // Extract product price from a product row and convert it to a usable number
    public int getProductPrice(WebElement productRow) {
        String priceText = productRow.findElement(By.xpath(".//div[contains(text(), 'Rp')]")).getText();
        // Split to remove 'or points' and parse the number
        String priceOnly = priceText.split("or")[0].replace("Rp", "").replace(",", "").trim();
        return Integer.parseInt(priceOnly);
    }

    // Click the plus button to increment quantity
    public void clickPlusButton(WebElement productRow) {
        productRow.findElement(By.xpath(".//button[@data-type='plus']")).click();
    }

    // Click the minus button to decrement quantity
    public void clickMinusButton(WebElement productRow) {
        productRow.findElement(By.xpath(".//button[@data-type='minus']")).click();
    }

    // Get the current quantity of a product
    public int getProductQuantity(WebElement productRow) {
        String quantityValue = productRow.findElement(By.xpath(".//input[@class='input-number']")).getAttribute("value");
        return (quantityValue != null && !quantityValue.isEmpty()) ? Integer.parseInt(quantityValue) : 0;
    }

    // Remove a product from the cart
    public void removeProduct(WebElement productRow) {
        productRow.findElement(By.cssSelector("a.btn-cart-remove")).click();
    }

    // Placeholder for remove button on ProductPageTest
    @FindBy(className ="btn-cart-remove")
    private WebElement removeButton;
    public void clickRemoveButton() {
        removeButton.click();
    }
}

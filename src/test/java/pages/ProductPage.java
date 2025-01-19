package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends BasePage {
    @FindBy(tagName = "h2")
    private WebElement productName;

    @FindBy(css = "div.quickview-price span.special")
    private WebElement productPrice;

    @FindBy(className = "btn-add-to-cart")
    private WebElement addToCartButton;

    @FindBy(id = "cart_total")
    private WebElement cartTotalElement;

    @FindBy(id = "show-your-cart")
    private WebElement showCartButton;

    @FindBy(className = "btn-product-plus")
    private WebElement productPlusButton;

    @FindBy(className = "btn-product-minus")
    private WebElement productMinusButton;

    @FindBy(className = "special")
    private WebElement price;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void decrementProduct(){
        productMinusButton.click();
    }

    public void incrementProduct(){
        productPlusButton.click();
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public void gotoCart(){
        click(showCartButton);
    }

    public int getPrice() {
        String currencyString = price.getText();
        String cleanedString = currencyString.replace("Rp", "").replace(",", "").trim();
        return Integer.parseInt(cleanedString);
    }

    public int getCartTotal() {
        String cartTotalText = cartTotalElement.getText();
        try {
            return Integer.parseInt(cartTotalText);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing cart total: " + e.getMessage());
            return 0;
        }
    }
}

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

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
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

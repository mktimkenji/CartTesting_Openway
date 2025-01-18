package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
    @FindBy(id = "nav-signin-text")
    private WebElement loginLink;

    @FindBy(id = "filter_name")
    private WebElement searchBar;

    @FindBy(css = "button.btnn i.ti-search")
    private WebElement searchButton;

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Login page navigation
    public void clickLogin() {
        click(loginLink);
    }

    // Search function
    public void searchProduct(String productName) {
        enterText(searchBar, productName);
        click(searchButton);
    }
}

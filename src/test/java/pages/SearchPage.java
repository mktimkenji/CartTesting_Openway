package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage extends BasePage {
    @FindBy(id = "filter_name")
    WebElement searchBar;

    @FindBy(css = "button.btnn i.ti-search")
    WebElement searchButton;

    // Constructor
    public SearchPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterSearchText(String searchTerm) {
        searchBar.sendKeys(searchTerm);
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public void searchForProduct(String searchTerm) {
        enterSearchText(searchTerm);
        clickSearchButton();
    }
}
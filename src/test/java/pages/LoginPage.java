package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(id = "ps")
    private WebElement passwordField;

    @FindBy(id = "button-login")
    private WebElement loginButton;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Login method
    public void login(String username, String password) {
        enterText(emailField, username);
        enterText(passwordField, password);
        click(loginButton);
    }
}


package tests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import java.time.Duration;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class CartTest extends BaseTest {
    @BeforeClass
    public void setUpCart() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Log in
        homePage.clickLogin();
        loginPage.login("carttestingopenway@gmail.com", "KiYiFwUn$MDZ6eZ");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        // Ensure cart starts out empty
        productPage.gotoCart();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        while(cartPage.isRemoveButtonPresent()){
            cartPage.clickRemoveButton();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        }
    }

    @Test // Test Case #1: Adding and Removing Product from Cart
    public void testAddAndRemoveProduct() {
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        String productName = "Fahrenheit 451";
        searchPage.searchForProduct(productName);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        searchPage.clickFirstProduct();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        productPage.addToCart();

        // Wait for pop-up message
        try {
            // Wait for 3 seconds to allow the pop-up to disappear
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }

        // Verify cart total increases
        int cartTotal = productPage.getCartTotal();
        Assert.assertTrue(cartTotal > 0, "Cart total should increase after adding a product.");

        // Navigate to cart and remove product
        productPage.gotoCart();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        cartPage.clickRemoveButton();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        cartTotal = productPage.getCartTotal();
        assertEquals("Cart total should decrease back to 0 after removing product", 0, cartTotal);

    }

    @Test // Test Case #2: Adding or Removing Product Quantity with the Plus Minus Button on the Cart Page
    public void testIncrementAndDecrementQuantity() {
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        String productName = "The Lord of the Rings";
        searchPage.searchForProduct(productName);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        searchPage.clickFirstProduct();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        productPage.addToCart();
        try {
            // Wait for 3 seconds to allow the pop-up to disappear
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }

        productPage.gotoCart();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        // Increment quantity
        cartPage.clickPlusButton();
        try {
            // Wait for 1 second for update
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }

        int cartTotal = productPage.getCartTotal();
        assertEquals("Cart total should increase to 2", 2, cartTotal); // Check the cart quantity number

        // Decrement quantity
        cartPage.clickMinusButton();
        try {
            // Wait for 1 second for update
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }

        cartTotal = productPage.getCartTotal();
        assertEquals("Cart total should decrease to 1", 1, cartTotal);

        cartPage.clickRemoveButton();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
    }

    @Test // Test Case #3: Manually Updating Quantity and Invalid Quantity
    public void testInputInvalidQuantity() {
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        String productName = "The Curious Incident of the Dog in the Night-Time";
        searchPage.searchForProduct(productName);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        searchPage.clickFirstProduct();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        productPage.addToCart();
        try {
            // Wait for 3 seconds to allow the pop-up to disappear
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }

        productPage.gotoCart();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        List<WebElement> productRows = cartPage.getProductRows();
        WebElement firstProduct = productRows.getFirst();
        cartPage.setProductQuantity("5");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        int cartTotal = productPage.getCartTotal();
        assertEquals("Cart total should be 5", 5, cartTotal);

        // Edge case invalid value 0
        cartPage.setProductQuantity("0");
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        alert.dismiss();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        // Verify quantity doesn't change to invalid value and remains the previous value
        cartTotal = productPage.getCartTotal();
        assertEquals("Cart total should be 5", 5, cartTotal);

        // Edge case invalid value > 500
        cartPage.setProductQuantity("700");
        wait.until(ExpectedConditions.alertIsPresent());
        alert = driver.switchTo().alert();
        alert.dismiss();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        // Verify again
        cartTotal = productPage.getCartTotal();
        assertEquals("Cart total should be 5", 5, cartTotal);

        cartPage.clickRemoveButton();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
    }
}

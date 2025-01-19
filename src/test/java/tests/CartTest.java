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

    @Test // Test Case #4: Cart Persistence Across Sessions
    public void testCartPersistence(){
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        SearchPage searchPage = new SearchPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        CartPage cartPage = new CartPage(driver);

        searchPage.searchForProduct("A Scanner Darkly");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        searchPage.clickFirstProduct();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        productPage.addToCart();
        try {
            // Wait for 5 seconds to allow the pop-up to disappear
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }
        int cartTotal = productPage.getCartTotal();

        driver.get("http://www.periplus.com/_index_/Logout"); // Log out
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        homePage.clickLogin();
        loginPage.login("carttestingopenway@gmail.com", "KiYiFwUn$MDZ6eZ");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        int cartTotal2 = productPage.getCartTotal();
        assertEquals("Cart total should be the same", cartTotal, cartTotal2);

        // Clean up cart
        productPage.gotoCart();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        cartPage.clickRemoveButton();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

    }

    @Test // Test Case #5: Adding Unavailable Items
    public void testUnavailableItems(){
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        String productName = "Flowers for Algernon";
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
        Assert.assertEquals(cartTotal, 0, "Cart total should remain at 0");
    }

    @Test // Test Case #6: Adding an Existing Product, Different Products, and Verifying Price
    public void addExistingProduct() {
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
        try {
            // Wait for 3 seconds to allow the pop-up to disappear
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }

        int cartTotal = productPage.getCartTotal();
        int itemQuantity = 7;

        for (int i=1; i<itemQuantity; i++) productPage.incrementProduct(); // Verifying overwriting behavior of adding an existing product with bigger quantity
        productPage.addToCart();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }
        cartTotal = productPage.getCartTotal();
        Assert.assertEquals(cartTotal, 7, "Cart total should be 7");

        for (int i=0; i<3; i++) productPage.decrementProduct(); // Verifying overwriting behavior of adding an existing product with lower quantity
        productPage.addToCart();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }
        cartTotal = productPage.getCartTotal();
        itemQuantity = 4;
        Assert.assertEquals(cartTotal, itemQuantity, "Cart total should be 7 - 3(times minus was clicked) = 4");

        int cartPriceTotal = productPage.getPrice()*itemQuantity; // Storing price manually

        // Adding a different product
        productName = "The Lord of the Rings";
        searchPage.searchForProduct(productName);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        searchPage.clickFirstProduct();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        itemQuantity = 5;
        for(int i=1; i<itemQuantity; i++) productPage.incrementProduct();
        productPage.addToCart();
        try {
            // Wait for 3 seconds to allow the pop-up to disappear
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }
        cartTotal = productPage.getCartTotal();
        Assert.assertEquals(cartTotal, 9, "Cart total should be 4 + 5(times plus was clicked) = 9");

        cartPriceTotal += productPage.getPrice()*itemQuantity; // Adding stored price total

        productPage.gotoCart();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        int cartPagePriceTotal = cartPage.getTotalPrice(); // Get displayed total price
        assertEquals("Cart total price must be equal with calculation", cartPagePriceTotal, cartPriceTotal); // Ensuring stored price and displayed price are equal

        // Test Cleanup
        while(cartPage.isRemoveButtonPresent()){
            cartPage.clickRemoveButton();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        }
    }
}

/*
Future improvements would be to store cart items in cart page as an array for better scalability and maintainability
This would also enable automated price calculation for verification on the cart page itself
A natural integration of verifying the price system for each test case as a function
Improve logic to merge adding existing product test with base addition and removal test,
currently not feasible due to test format not having a way to verify existence of a specific product in cart.
 */

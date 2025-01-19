package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.time.Duration;

import static org.testng.AssertJUnit.assertEquals;

public class ProductPageTest extends BaseTest{

    // Original automation test task of adding a product to the cart and verifying it
    @Test
    public void testProductPage() {

        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Log in
        homePage.clickLogin();
        loginPage.login("carttestingopenway@gmail.com", "KiYiFwUn$MDZ6eZ");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        // Search for a product
        String productName = "Fahrenheit 451";
        searchPage.searchForProduct(productName);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        // Click on the first product in the search results
        searchPage.clickFirstProduct();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        // Verify the product name and price
        String productTitle = productPage.getProductName();
        Assert.assertEquals(productTitle, productName, "Product title does not match.");

        // Verify product price (special price)
        String productPrice = productPage.getProductPrice();
        Assert.assertNotNull(productPrice, "Product price is not displayed.");

        // Initial cart value
        int initialCartTotal = productPage.getCartTotal();
        System.out.println("Initial Cart Total: " + initialCartTotal);

        // Add the product to the cart
        productPage.addToCart();

        // Wait for pop-up message
        try {
            // Wait for 3 seconds to allow the pop-up to disappear
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Error during sleep: " + e.getMessage());
        }

        // Verify the product is added to the cart
        int updatedCartTotal = productPage.getCartTotal();
        System.out.println("Updated Cart Total: " + updatedCartTotal);

        // Verify that the cart total increased by 1
        assertEquals("Cart total should increase by 1 after adding a product", initialCartTotal + 1, updatedCartTotal);


        // Remove existing product for consequent test to be valid
        productPage.gotoCart();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        cartPage.clickRemoveButton();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
    }
}

// Test passed when chosen product have not been previously added to the cart
// This test structure will fail when trying to add an existing product
// Periplus website will update the cart item quantity to the quantity added on the product page for existing products
// This means cart quantity may also decrease if the new amount added is lower than the previous cart quantity amount
// For testing purposes, this would require a different test suite/class with a different logic to verify the cart amount

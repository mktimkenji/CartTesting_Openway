package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;

import java.time.Duration;

public class SearchTest extends BaseTest {

    @Test
    public void testSearch() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);

        homePage.searchProduct("flowers for algernon");
        // Searches for the book flowers for algernon

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        searchPage.clickFirstProduct();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

    }
}

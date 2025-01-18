package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CartFunctionalityTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");

        // Initialize the WebDriver (ChromeDriver)
        driver = new ChromeDriver();

        // Open the desired website (replace with your app's URL)
        driver.get("https://www.periplus.com");

        // Maximize the browser window
        driver.manage().window().maximize();
    }

    @Test
    public void testCartFunctionality() {
        // Add your test logic for cart functionality here
        System.out.println("Testing cart functionality...");
        // Example: Assert that the website loaded correctly (replace with actual test code)
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
    }

    @AfterClass
    public void tearDown() {
        // Close the browser and WebDriver instance
        if (driver != null) {
            driver.quit();
        }
    }
}
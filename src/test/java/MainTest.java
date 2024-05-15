// import org.junit.Test;
import org.junit.Assert;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class MainTest {
    @Test
    @Order(1)
    public void testFacebookLoad() {
        System.setProperty("webdriver.gecko.driver", "./driver/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.facebook.com");
        Assert.assertEquals("https://www.facebook.com/", driver.getCurrentUrl());
        driver.quit();
    }

    @Test
    @Order(2)
    public void login() {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("webdriver.gecko.driver", "./driver/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.facebook.com");

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("pass"));
        WebElement loginButton = driver.findElement(By.name("login"));

        emailField.sendKeys(dotenv.get("FB_EMAIL"));
        passwordField.sendKeys(dotenv.get("FB_PASSWORD"));
        loginButton.click();

        // Wait for the page to load
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("url ::::" + driver.getCurrentUrl());

        Assert.assertEquals("https://www.facebook.com/", driver.getCurrentUrl());

        driver.quit();
    }
}
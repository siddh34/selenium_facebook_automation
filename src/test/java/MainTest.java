import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Disabled;

@TestMethodOrder(OrderAnnotation.class)
public class MainTest {
    @Test
    @Order(1)
    @Disabled
    public void testFacebookLoad() {
        System.setProperty("webdriver.gecko.driver", "./driver/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.facebook.com");
        Assert.assertEquals("https://www.facebook.com/", driver.getCurrentUrl());
        driver.quit();
    }

    @Test
    @Order(2)
    @Disabled
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

    @Test
    @Order(3)
    @Disabled
    public void createPost(){
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

        String fbName = dotenv.get("FB_NAME");
        WebElement createPost = driver
                .findElement(By.xpath("//span[contains(text(),\"What's on your mind, " + fbName + "?\")]"));
        createPost.click();

        WebDriverWait wait = new WebDriverWait(driver, 40); // wait for up to 20 seconds
        WebElement postField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='textbox']")));

        // tag
        String message1 = "@";
        for (char c : message1.toCharArray()) {
            try {
                postField.sendKeys(String.valueOf(c));
                Thread.sleep(500);
                postField.sendKeys(Keys.TAB);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        postField.sendKeys("\n");

        // adds message
        String message2 = "Hello Guys how are you?";
        for (char c : message2.toCharArray()) {
            postField.sendKeys(String.valueOf(c));
        }

        WebElement postButton = driver.findElement(By.xpath("//div[@aria-label='Post']"));
        postButton.click();

        // Wait for the page to load
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("https://www.facebook.com/", driver.getCurrentUrl());

        driver.quit();
    }


    @Test
    @Order(4)
    public void deletePost(){
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

        // go to profile
        driver.get("https://www.facebook.com/profile");

        WebDriverWait wait = new WebDriverWait(driver, 10); // wait for up to 10 seconds
        // find post with text
        String message2 = "@highlight";
        WebElement post = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//span[contains(text(),\"" + message2 + "\")]/..")));

        // find post options within the same parent as the post
        WebElement postOptions = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(".//div[@aria-label='Actions for this post']")));

        // click on post options
        postOptions.click();

        // find delete post
        WebElement deletePostOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Move to bin')]")));
        deletePostOption.click();

        // click on delete post

        // handle the confirmation pop-up
        WebElement deletePostOptionfinalButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Move')]")));
        deletePostOptionfinalButton.click();

        // Wait for the page to load
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertNotEquals("https://www.facebook.com/", driver.getCurrentUrl());

        driver.quit();
    }
}
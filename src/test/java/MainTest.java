import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.cdimascio.dotenv.Dotenv;

@TestMethodOrder(OrderAnnotation.class)
public class MainTest {
    private static WebDriver driver;
    private Dotenv dotenv;

    MainTest() {
        driver = new FirefoxDriver();
        this.dotenv = Dotenv.load();
    }

    @BeforeAll
    public static void setup() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "./driver/geckodriver");
        Thread.sleep(5000);
        driver.get("https://www.facebook.com");
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
        System.clearProperty("webdriver.gecko.driver");
    }

    @Test
    @Order(1)
    public void testFacebookLoad() {
        Assert.assertEquals("https://www.facebook.com/", driver.getCurrentUrl());
    }

    @Test
    @Order(2)
    public void login() {
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("pass"));
        WebElement loginButton = driver.findElement(By.name("login"));

        emailField.sendKeys(dotenv.get("FB_EMAIL"));
        passwordField.sendKeys(dotenv.get("FB_PASSWORD"));
        loginButton.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("https://www.facebook.com/", driver.getCurrentUrl());
    }

    @Test
    @Order(3)
    public void createPost(){
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

    }


    @Test
    @Order(4)
    public void deletePost(){
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

        WebDriverWait wait = new WebDriverWait(driver, 20); // wait for up to 10 seconds
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
        WebElement deletePostOptionfinalButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/div/div/div/div[3]/div/div/div/div/div[1]/div/div")));
        deletePostOptionfinalButton.click();

        // Wait for the page to load
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertNotEquals("https://www.facebook.com/", driver.getCurrentUrl());
    }
}
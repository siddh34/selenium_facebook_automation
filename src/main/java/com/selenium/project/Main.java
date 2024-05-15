package com.selenium.project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "./driver/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.facebook.com");
        driver.quit();
    }
}

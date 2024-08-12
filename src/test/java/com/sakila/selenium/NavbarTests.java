package com.sakila.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NavbarTests {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.out.println("Starting test setup");

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        String url = "http://localhost:5174/";
        driver.get(url);
    }

    @Test
    public void testHomeLink() {
        WebElement homeLink = driver.findElement(By.linkText("Home"));
        homeLink.click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:5174/");
    }

    @Test
    public void testActorsLink() {
        WebElement actorsLink = driver.findElement(By.linkText("Actors"));
        actorsLink.click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:5174/actors");
    }

    @Test
    public void testFilmsLink() {
        WebElement filmsLink = driver.findElement(By.linkText("Films"));
        filmsLink.click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:5174/films");
    }

    @Test
    public void testAddActorsLink() {
        WebElement addActorsLink = driver.findElement(By.linkText("Add Actors"));
        addActorsLink.click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:5174/add-actor");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void Sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

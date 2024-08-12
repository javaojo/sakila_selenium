package com.sakila.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddActorTests {

    private WebDriver driver;


    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("http://localhost:5174/add-actor");
    }


    @Test
    public void testFormSubmissionSuccess() {

        WebElement firstNameInput = driver.findElement(By.id("firstname"));
        WebElement lastNameInput = driver.findElement(By.id("lastname"));
        WebElement submitButton = driver.findElement(By.id("submit-actor"));


        firstNameInput.sendKeys("John");
        lastNameInput.sendKeys("Doe");

        submitButton.click();

        //Sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(10));
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-message")));

        Assert.assertEquals(messageElement.getText(), "Actor added successfully!");


    }


    @Test
    public void testFormSubmissionError() {


        WebElement firstNameInput = driver.findElement(By.id("firstname"));
        WebElement lastNameInput = driver.findElement(By.id("lastname"));
        WebElement submitButton = driver.findElement(By.id("submit-actor"));


        firstNameInput.sendKeys("J");
        lastNameInput.sendKeys("D");
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(10));
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message p")));
        Assert.assertEquals(messageElement.getText(), "An error occurred while adding the actor.");
    }



    @AfterMethod
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

package com.sakila.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class UpdateActorTests {

    private WebDriver driver;



    @BeforeMethod
    public void Setup(){

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Adding an actor for testing
        driver.get("http://localhost:5174/add-actor");

        WebElement firstNameInput = driver.findElement(By.id("firstname"));
        WebElement lastNameInput = driver.findElement(By.id("lastname"));
        WebElement submitButton = driver.findElement(By.id("submit-actor"));

        firstNameInput.sendKeys("ActorToUpdate");
        lastNameInput.sendKeys("John Doe");
        submitButton.click();

        driver.get("http://localhost:5174/actors");
        }



    @Test
    public void testUpdateActorDetailsLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement lastActorLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".actor-grid .actor-card-link:last-of-type")));
        lastActorLink.click();

        // navigate to the update page
        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Update Actor")));
        updateButton.click();

        // check if the details are loaded in the fields
        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname-form")));
        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastname-form")));

        assertEquals(firstNameInput.getAttribute("value"), "ActorToUpdate");
        assertEquals(lastNameInput.getAttribute("value"), "John Doe");
    }


    @Test
    public void testUpdateActorAndVerifyChanges() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // get the last actor link and click on it
        WebElement lastActorLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".actor-grid .actor-card-link:last-of-type")));
        lastActorLink.click();

        //go to the update page
        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Update Actor")));
        updateButton.click();

        // update the actor details
        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname-form")));
        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastname-form")));
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));

        firstNameInput.clear();
        firstNameInput.sendKeys("UpdatedFirstName");
        lastNameInput.clear();
        lastNameInput.sendKeys("UpdatedLastName");
        submitButton.click();

        WebElement updatedFirstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".actor-details div h2")));
        assertTrue(updatedFirstName.getText().contains("UpdatedFirstName UpdatedLastName"));
    }


    // this test will fail
    @Test
    public void testUpdateActorWithOneLetterNameShowsError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement lastActorLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".actor-grid .actor-card-link:last-of-type")));
        lastActorLink.click();

        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Update Actor")));
        updateButton.click();

        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname-form")));
        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastname-form")));
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));

        firstNameInput.clear();
        firstNameInput.sendKeys("A");
        lastNameInput.clear();
        lastNameInput.sendKeys("B");
        submitButton.click();

        // // ERROR MESSAGE NEEDS TO BE ADDED TO REACT APP
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error-message")));
        assertTrue(errorMessage.getText().contains("Name must be at least 2 characters long"));
    }



    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}

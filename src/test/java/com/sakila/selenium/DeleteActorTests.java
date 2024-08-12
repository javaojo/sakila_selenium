package com.sakila.selenium;

import org.openqa.selenium.Alert;
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
import java.util.List;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class DeleteActorTests {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {

        // first add an actor
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

    }

    @Test
    public void testDeleteAnExistingActor() {

        driver.get("http://localhost:5174/add-actor");

        WebElement firstNameInput = driver.findElement(By.id("firstname"));
        WebElement lastNameInput = driver.findElement(By.id("lastname"));
        WebElement submitButton = driver.findElement(By.id("submit-actor"));

        firstNameInput.sendKeys("ActorToDelete");
        lastNameInput.sendKeys("John Doe");
        submitButton.click();

        driver.get("http://localhost:5174/actors");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //get last actor link and click on it. Store it for comparison
        WebElement lastActorLinkBefore = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".actor-grid .actor-card-link:last-of-type")));
        lastActorLinkBefore.click();

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".actor-actions button")));
        deleteButton.click();

        Alert alert = driver.switchTo().alert();
        alert.accept();

        //check if last item on actor-card link != new actor-card link
        WebElement lastActorLinkAfter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".actor-grid .actor-card-link:last-of-type")));

        assertNotEquals(lastActorLinkBefore, lastActorLinkAfter, "The actor was not deleted successfully");

    }

    @Test
    public void testDeleteAnNonExistentActor() {

        driver.get("http://localhost:5174/actors");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String nonExistentActorId = "999999";
        driver.get("http://localhost:5174/actors/" + nonExistentActorId);


        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        assertTrue(buttons.isEmpty(), "The page should not contain any buttons.");

    }


        @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}

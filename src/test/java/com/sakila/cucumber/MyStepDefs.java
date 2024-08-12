package com.sakila.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.*;

public class MyStepDefs {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the user is on the add actor page")
    public void userOnTheAddActorPage() {
        driver.get("http://localhost:5174/add-actor");
    }

    @When("the user enters {string} into the first name field")
    public void enterIntoTheFirstNameField(String firstName) {

        WebElement firstNameInput = driver.findElement(By.id("firstname"));
        firstNameInput.sendKeys(firstName);
    }

    @When("( the user )enters {string} into the last name field")
    public void enterIntoTheLastNameField(String lastName) {
        WebElement lastNameInput = driver.findElement(By.id("lastname"));
        lastNameInput.sendKeys(lastName);
    }

    @When("( the user )clicks the submit button")
    public void clickTheSubmitButton() {
        WebElement submitButton = driver.findElement(By.id("submit-actor"));
        submitButton.click();
    }

    @Then("a message {string} should display")
    public void userShouldSeeMessage(String expectedMessage) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement messageElement;

        if (expectedMessage.equals("Actor added successfully!")) {
            messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-message")));
        } else {
            messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message p")));
        }
        assertEquals(messageElement.getText(), expectedMessage);
    }

    // update actors
    @Given("the user is on actor's detail page with the URL {string}")
    public void theUserIsOnActorsDetailPageWithTheURL(String url) {
        driver.get(url);
    }

    @When("the user inputs first name {string} into the update actor form")
    public void theUserInputsFirstNameIntoTheUpdateActorForm(String firstName) {
        WebElement firstNameInput = driver.findElement(By.id("firstname-form"));
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    @When("the user inputs last name {string} into the update actor form")
    public void theUserInputsLastNameIntoTheUpdateActorForm(String lastName) {
        WebElement lastNameInput = driver.findElement(By.id("lastname-form"));
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    @When("the user submits the update actor form")
    public void theUserSubmitsTheUpdateActorForm() {
        WebElement submitButton = driver.findElement(By.cssSelector("form button"));
        submitButton.click();
        Sleep(100);
    }

    @Then("the update actor URL should be {string}")
    public void theUpdateActorURLShouldBe(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl);
    }

    // test navbar links
    @Given("the user is on the homepage")
    public void theUserIsOnTheHomepage() {
        driver.get("http://localhost:5174/");
    }

    @When("user clicks the {string} link")
    public void userClicksTheLink(String linkText) {
        WebElement navbar = driver.findElement(By.cssSelector(".navbar .nav"));
        WebElement weblink = navbar.findElement(By.linkText(linkText));
        weblink.click();
    }

    @Then("the user should be directed to the {string} URL")
    public void theUserShouldBeDirectedToANewPage(String expectedUrl) {
        assertEquals(driver.getCurrentUrl(), expectedUrl);
    }


    // delete an existing actor

    @When("the user adds an actor with the first name {string} and last name {string}")
    public void theUserAddsAnActorWithTheFirstNameAndLastName(String firstName, String lastName) {
        WebElement firstNameInput = driver.findElement(By.id("firstname"));
        firstNameInput.sendKeys(firstName);
        WebElement lastNameInput = driver.findElement(By.id("lastname"));
        lastNameInput.sendKeys(lastName);

        WebElement submitButton = driver.findElement(By.id("submit-actor"));
        submitButton.click();

    }

    @And("the user navigates to the actors page")
    public void theUserNavigatesToTheActorsPage() {
        driver.get("http://localhost:5174/actors");

    }

    @And("click the actor they have added in the list")
    public void clickTheActorTheyHaveAddedInTheList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement lastActorLinkBefore = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".actor-grid .actor-card-link:last-of-type")));
        lastActorLinkBefore.click();

    }

    @And("delete the actor")
    public void deleteTheActor() {
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".actor-actions button")));
        deleteButton.click();

        Alert alert = driver.switchTo().alert();
        alert.accept();

    }

    @Then("the actor should not be present in the list")
    public void theActorShouldNotBePresentInTheList() {
        WebElement lastActorLinkAfter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".actor-grid .actor-card-link:last-of-type")));
        assertNotEquals(lastActorLinkAfter, "ActorToDelete John Doe");

    }

    // non-existent actor deletion
    @When("the user tries to navigate to a non-existent actor page with ID {string}")
    public void theUserTriesToNavigateToANonExistentActorPageWithID(String arg0) {
        String nonExistentActorId = "999999";
        driver.get("http://localhost:5174/actors/" + nonExistentActorId);

    }

    @Then("the user should not be able to see any delete button")
    public void theUserShouldNotBeAbleToSeeAnyDeleteButton() {
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        assertTrue(buttons.isEmpty(), "The page should not contain any buttons.");

    }

    private void Sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

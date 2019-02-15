package test;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import sun.security.util.PendingException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;


public class StepDefinitions {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void before() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @Given("^We navigate to NCS homepage$")
    public void we_navigate_to_NCS_homepage() {
        driver.get("http://localhost/");
        Assert.assertEquals(driver.getCurrentUrl(),"http://localhost/");
        System.out.printf("%s \n", driver.getCurrentUrl());
        System.out.printf("%s \n", driver.getTitle());
    }

    @When("^We click on the button ANMELDEN$")
    public void we_click_on_the_button_ANMELDEN() {
        driver.findElement(By.linkText("Anmeldens")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(driver.findElement(By.id("login")));

        /*Wait<WebDriver> wait= new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("login"));
            }
        });
        */

    }

    @When("^Enter the Benutzername \"([^\"]*)\" with the related Passwort \"([^\"]*)\"$")
    public void enter_the_Benutzername_with_the_related_Passwort(String arg1, String arg2)  {
        driver.findElement(By.id("user_login")).click();
        driver.findElement(By.id("user_login")).sendKeys(arg1);
        driver.findElement(By.id("user_pass")).click();
        driver.findElement(By.id("user_pass")).sendKeys(arg2);

    }

    @Then("^The login is successful and the Dashboard will be shown$")
    public void the_login_is_successful_and_the_Dashboard_will_be_shown()  {

        Wait<WebDriver> wait= new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("login"));
            }
        });

    }

}
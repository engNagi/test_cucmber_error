package test;

import cucumber.api.Argument;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import io.cucumber.datatable.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.File;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;

public class PrimaryDefinitions {

    public static WebDriver driver;
    public String scenario_name;

    @Before
    public void before(Scenario scenario) {
        if ( System.getenv("MAVEN_CMD_LINE_ARGS").contains("DBROWSER=chrome") ) {
            if ( System.getenv("MAVEN_CMD_LINE_ARGS").contains("DHEADLESS=true") ){
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
                driver.manage().window().setSize(new Dimension(1280, 800));
                //driver.manage().deleteAllCookies();;
            } else {
                driver = new ChromeDriver();
                driver.manage().window().setSize(new Dimension(1280, 800));
                //driver.manage().deleteAllCookies();
            }
        } else if ( System.getenv("MAVEN_CMD_LINE_ARGS").contains("DBROWSER=firefox") ){
            if ( System.getenv("MAVEN_CMD_LINE_ARGS").contains("DHEADLESS=true") ){
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                driver = new FirefoxDriver(firefoxOptions);
                driver.manage().window().setSize(new Dimension(1280, 800));
            } else {
                driver = new FirefoxDriver();
                driver.manage().window().setSize(new Dimension(1280, 800));
                //driver.manage().deleteAllCookies();
            }
        } else {
            driver = new ChromeDriver();
            driver.manage().window().setSize(new Dimension(1280, 800));
            driver.manage().deleteAllCookies();
        }

        scenario_name = scenario.getName();

    }

    @After ("@LoginAdmin")
    public void after(Scenario scenario) {
        if (!scenario.isFailed()) {
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.id("wp-admin-bar-my-account"))).perform();

            WebElement e_abmelden = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.linkText("Abmelden"));
                }
            });

            e_abmelden.click();

            WebElement e_message = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.className("message"));
                }
            });

            Assert.assertEquals(e_message.getText().contains("Du hast dich erfolgreich abgemeldet."), true);

            //byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            //scenario.embed(screenshot, "image/png");

        }
    }

    @AfterStep
    public void afterStep (Scenario scenario) {
        if ( System.getenv("MAVEN_CMD_LINE_ARGS").contains("SCREENSHOT=true") ) {
            File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("MMddyy_HHmmss");
            String strDate = formatter.format(date);

            try {
                FileUtils.copyFile(src, new File("/home/testautomation/Documents/picture_"+scenario_name+"_"+strDate+".png"));
            } catch (IOException e){
            }
        }
    }

}
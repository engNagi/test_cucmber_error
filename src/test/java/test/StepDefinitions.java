package test;

import cucumber.api.Argument;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import io.cucumber.datatable.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.junit.Assert;
import static org.junit.Assert.fail;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StepDefinitions {
    PrimaryDefinitions pd = new PrimaryDefinitions();
    static WebDriver driver = PrimaryDefinitions.driver;
    String username;
    String userid;
    boolean error;
    boolean usernok;

    @Given("^We navigate to NCS homepage$")
    public void we_navigate_to_NCS_homepage() {
        driver.get("http://localhost/");
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost/");
        //System.out.printf("%s \n", driver.getCurrentUrl());
        //System.out.printf("%s \n", driver.getTitle());
    }

    @When("^We click on the button ANMELDEN$")
    public void we_click_on_the_button_ANMELDEN() {
        driver.findElement(By.linkText("Anmelden")).click();

        WebElement e_login = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("login"));
            }
        });
    }

    @When("^Enter the Benutzername \"([^\"]*)\" with the related Passwort \"([^\"]*)\"$")
    public void enter_the_Benutzername_with_the_related_Passwort(String arg1, String arg2) {
        driver.findElement(By.id("user_login")).click();
        driver.findElement(By.id("user_login")).sendKeys(arg1);
        driver.findElement(By.id("user_pass")).click();
        driver.findElement(By.id("user_pass")).sendKeys(arg2);
    }

    @Then("^The login is successful and the Dashboard will be shown$")
    public void the_login_is_successful_and_the_Dashboard_will_be_shown() {
        driver.findElement(By.id("wp-submit")).click();

        WebElement e_usermenu = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("wp-admin-bar-my-account"));
            }
        });

        Assert.assertEquals(e_usermenu.getText().contains("Willkommen"), true);
    }

    @And("^Enter an existing Benutzername \"([^\"]*)\" with a wrong Passwort \"([^\"]*)\"$")
    public void enterAnExistingBenutzernameWithAWrongPasswort(String username, String userpwd) {
        driver.findElement(By.id("user_login")).click();
        driver.findElement(By.id("user_login")).sendKeys(username);
        driver.findElement(By.id("user_pass")).click();
        driver.findElement(By.id("user_pass")).sendKeys(userpwd);
    }

    @Then("^An error message will shown that the password for the user is incorrect$")
    public void anErrorMessageWillShownThatThePasswordForTheUserIsIncorrect() {
        driver.findElement(By.id("loginform")).submit();

        WebElement e_errmessage = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("login_error"));
            }
        });

        //Assert.assertEquals(e_errmessage.getText().contains("FEHLER: Das Passwort, das du für den Benutzernamen %s eingegeben hast, ist nicht korrekt", username), true);
    }

    @And("^Enter an unknown Benutzername \"([^\"]*)\" with a correct Passwort \"([^\"]*)\"$")
    public void enterAnUnknownBenutzernameWithACorrectPasswort(String arg0, String arg1) {
        driver.findElement(By.id("user_login")).click();
        driver.findElement(By.id("user_login")).sendKeys(arg0);
        driver.findElement(By.id("user_pass")).click();
        driver.findElement(By.id("user_pass")).sendKeys(arg1);
    }

    @Then("^An error message will shown that the user is not known in the system$")
    public void anErrorMessageWillShownThatTheUserIsNotKnownInTheSystem() {
        driver.findElement(By.id("loginform")).submit();

        WebElement e_errmessage = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("login_error"));
            }
        });

        Assert.assertEquals(e_errmessage.getText().contains("FEHLER: Ungültiger Benutzername."), true);
    }

    @Given("^We navigate to NCS homepage and login as admin$")
    public void weNavigateToNCSHomepageAndLoginAsAdmin() {
        we_navigate_to_NCS_homepage();
        we_click_on_the_button_ANMELDEN();
        enterAnUnknownBenutzernameWithACorrectPasswort("admin", "!NCS2019");
        the_login_is_successful_and_the_Dashboard_will_be_shown();
    }

    @When("^We enter the all required information for the new user$")
    public void weEnterTheAllRequiredInformationForTheNewUser(DataTable usercredentials) {

        List<Map<String, String>> list = usercredentials.asMaps(String.class, String.class);
        for(int i=0; i<list.size(); i++) {

            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.xpath("//*[@class = 'wp-menu-image dashicons-before dashicons-admin-users']"))).perform();

            WebElement e_link = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.linkText("Neu hinzufügen"));
                }
            });

            e_link .click();

            driver.findElement(By.id("user_login")).click();
            driver.findElement(By.id("user_login")).sendKeys(list.get(i).get("Benutzername"));
            driver.findElement(By.id("email")).click();
            driver.findElement(By.id("email")).sendKeys(list.get(i).get("EMail"));
            driver.findElement(By.id("first_name")).click();
            driver.findElement(By.id("first_name")).sendKeys(list.get(i).get("Vorname"));
            driver.findElement(By.id("last_name")).click();
            driver.findElement(By.id("last_name")).sendKeys(list.get(i).get("Nachname"));

            driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
            driver.findElement(By.id("pass1-text")).click();
            driver.findElement(By.id("pass1-text")).clear();
            driver.findElement(By.id("pass1-text")).sendKeys(list.get(i).get("Passwort").replaceAll("^(.)(.*)", "$1"));
            driver.findElement(By.id("pass1-text")).sendKeys(list.get(i).get("Passwort").replaceAll("^(.)(.*)", "$2"));

            if ( driver.findElement(By.className("pw-checkbox")).isDisplayed() ){
                driver.findElement(By.className("pw-checkbox")).click();
            }

            driver.findElement(By.id("send_user_notification")).click();

            new Select(driver.findElement(By.id("role"))).selectByVisibleText(list.get(i).get("Rolle"));
            driver.findElement(By.id("role")).click();

            driver.findElement(By.id("createusersub")).click();
        }
    }

    @Then("^Creation of all users are ok$")
    public void creationOfAllUsersAreOk() {
        System.out.println("Creation of all users are successfully");
    }

    @And("^User enters Benutzername \"([^\"]*)\" and Passwort \"([^\"]*)\"$")
    public void userEntersBenutzernameAndPasswort(String arg0, String arg1) {
        driver.findElement(By.id("user_login")).click();
        driver.findElement(By.id("user_login")).sendKeys(arg0);
        driver.findElement(By.id("user_pass")).click();
        driver.findElement(By.id("user_pass")).sendKeys(arg1);
    }

    @And("^The user will be logged out successful at the end$")
    public void theUserWillBeLoggedOutSuccessfulAtTheEnd() {
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
    }

    @Given("^We click on the button Benutzer$")
    public void weClickOnTheButtonBenutzer() {
        driver.findElement(By.xpath("//*[@class = 'wp-menu-image dashicons-before dashicons-admin-users']")).click();

        WebElement e_benutzer = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.className("wp-heading-inline"));
            }
        });

        Assert.assertEquals(e_benutzer.getText().contains("Benutzer"), true);
    }

    @When("^we click on Löschen for the following user$")
    public void weClickOnLöschenForTheFollowingUser(DataTable userdel) {
        List<List<String>> list = userdel.asLists();
        String failuremessage = "";

        for(int i=0; i<list.get(0).size(); i++) {
            username = list.get(0).get(i);

            if ( !driver.findElements(By.className("first-page")).isEmpty() ){
                driver.findElement(By.className("first-page")).click();
            } else if ( !driver.findElements(By.className("prev-page")).isEmpty() ){
                driver.findElement(By.className("prev-page")).click();
            }

            do {
                usernok = false;
                try {
                    userid = driver.findElement(By.linkText(username)).getAttribute("href").replaceAll("^.*user_id=(\\d*).*$", "$1");

                    Actions action = new Actions(driver);
                    action.moveToElement(driver.findElement(By.linkText(username))).perform();
                    break;
                } catch (Exception errlog) {
                    if (errlog.getMessage().contains("Unable to locate element") && !driver.findElements(By.className("next-page")).isEmpty()) {http://localhost/wp-admin/users.php?paged=2
                    driver.findElement(By.className("next-page")).click();
                    } else if (errlog.getMessage().contains("is out of bounds of viewport")) {
                        WebElement e_scrollinto = driver.findElement(By.linkText(username));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e_scrollinto);
                        Actions action = new Actions(driver);
                        action.moveToElement(driver.findElement(By.linkText(username))).perform();
                        break;
                    } else {
                        String array[] = errlog.getMessage().split("\n");
                        //System.out.println(array[0]);

                        failuremessage = failuremessage + "\n" + array[0];

                        error = true;
                        usernok = true;

                        // Implement screenshot for reporting function

                        break;
                    }
                }
            } while (true);

            if ( usernok ) {
                continue;
            }

            WebElement e_userdel = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath(".//a[contains(@href,'users.php?action=delete&user="+userid+"')]"));
                }
            });

            e_userdel .click();

            WebElement e_userdelinfo = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.className("wrap"));
                }
            });

            Assert.assertEquals(e_userdelinfo.getText().startsWith("Benutzer löschen"), true);

            if ( !driver.findElements(By.id("delete_option0")).isEmpty() ) {
                driver.findElement(By.id("delete_option0")).click();
            }

            driver.findElement(By.id("submit")).click();

            WebElement e_message = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.id("message"));
                }
            });

            Assert.assertEquals(e_message.getText().startsWith("Benutzer gelöscht"), true);

        }

        if ( error ) {
            fail(failuremessage);
            //System.out.println(failuremessage);
        }
    }

    @Then("^Deletion is ok$")
    public void deletionIsOk() {
        System.out.println("User has been deleted");
    }
}
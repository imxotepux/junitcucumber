package Mail.ru;

import Database.DataBaseConnection;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LoginPage {
    private final String LOGIN_FIELD = xPathWraper("email-input svelte-1da0ifw");
    private static final String LOGIN = DataBaseConnection.getDataBaseValue("login");
    private final String INPUT_PASS = xPathWraper("button svelte-1da0ifw");
    private static final String PASSWORD_FIELD = xPathWraper("password-input svelte-1da0ifw");
    private static final String PASSWORD = DataBaseConnection.getDataBaseValue("password");
    private static final String INPUT_BUTTON = xPathWraper("second-button svelte-1da0ifw");
    private static final String MAIN_URL = "https://mail.ru";
    private static final String MY_PROFILE = xPathWraper("ph-project ph-project__account svelte-1hiqrvn " +
                                                                                                     "ph-project-any");
    private static final String LOGOUT = xPathWraper("ph-text svelte-1vf03eq");
    private WebDriver webDriver;
    Logger log = Logger.getLogger(DataBaseConnection.class.getName());
    public static String xPathWraper(String element) {
        return "//*[@class=\"" + element + "\"]";
    }

    public void LoginSteps() {
        log.info( "Test started");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\jbUser\\Downloads\\chromedriver_win32\\chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Given("^I on mail.ru page$")
    public void loginIntoMailBox() {
        LoginSteps();
        webDriver.manage().window().maximize();
        webDriver.get(MAIN_URL);
        log.info( "Browser started with " + MAIN_URL);
    }

    @When("^I input valid credentials$")
    public void inputCredentials() {
        checkThatDisplayed(LOGIN_FIELD);
        sendKey(LOGIN_FIELD, LOGIN);
        findClickNoWait(INPUT_PASS);
        findClickNoWait(PASSWORD_FIELD);
        sendKey(PASSWORD_FIELD, PASSWORD);
        findClickNoWait(INPUT_BUTTON);
    }

    @Then("^I see logout link$")
    public void Logout() {
        checkThatDisplayed(MY_PROFILE);
        log.info( "Login completed");
        findClick(MY_PROFILE);
        findClick(LOGOUT);
    }

    @After("@Login")
    public void afterClass() {
        webDriver.quit();
        log.info( "Test finished");
    }

    public void sendKey(String xpath, String value) {
        webDriver.findElement(By.xpath(xpath)).sendKeys(value);
    }

    public void findClickNoWait(String element) {
        webDriver.findElement(By.xpath(element)).click();
    }

    public void checkThatDisplayed(String xpath) {
        WebElement checkThatDisplayed = (new WebDriverWait(webDriver, 15)).until((ExpectedConditions.
                elementToBeClickable(By.xpath(xpath))));
    }

    public void findClick(String xpath) {
        WebElement findClick = (new WebDriverWait(webDriver, 15)).until((ExpectedConditions.
                elementToBeClickable(By.xpath(xpath))));
        findClick.click();
    }
}
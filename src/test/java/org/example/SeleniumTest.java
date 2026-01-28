package org.example;

import io.cucumber.java.en.Given;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.*;
import java.time.Duration;
import java.util.Set;

public class SeleniumTest {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumTest.class);
    private static WebDriver driver;

    public SeleniumTest() {
        System.out.println("Selenium constructor...");
        driver = HooksTest.getDriver();
    }

    public void waiting(int seconds) throws InterruptedException {
        synchronized (driver) {
            driver.wait(seconds * 1000);
        }

    }

    @Given("Prerequisites to run test cases {string}")
    public void prerequisitesToRunTestCases(String testCaseId) {
        System.out.println("Test Case Id :" + testCaseId);
        MDC.put("testCaseId", testCaseId);
    }

    @Given("Login to Instagram userId = {string} and password = {string}")
    public void instagramLogin(String userId,String secret) throws IOException {

        String userName =  System.getProperty("username",userId);
        String password = System.getProperty("password",secret);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Wait for up to 15 seconds

        driver.get("https://www.instagram.com/accounts/login/?hl=en");

        By inputEmail = By.xpath("//input[@name='email']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));
        driver.findElement(inputEmail).sendKeys(userName + Keys.ENTER);

        WebElement inputPassword = driver.findElement(By.xpath("//input[@name='pass']"));
        inputPassword.sendKeys(password + Keys.ENTER);

        By notNowBtn = By.xpath("//div[@role='button' and .//text()[contains(.,'Not now')]]");
        wait.until(ExpectedConditions.elementToBeClickable(notNowBtn)).click();

        Set<Cookie> cookies = driver.manage().getCookies();

        ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream("insta_cookies.data"));
        oos.writeObject(cookies);
        oos.close();
    }

    @Given("Send message {string} to user {string} on instagram")
    public void instagramSendingMessages(String message, String userId) throws InterruptedException, IOException, ClassNotFoundException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Wait for up to 15 seconds
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://www.instagram.com/");
        Thread.sleep(3000);
        // Load cookies
        ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream("insta_cookies.data"));
        Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
        ois.close();
        // Add cookies
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
        driver.navigate().refresh();

        By svg = By.xpath("//*[name()='svg' and @aria-label='Messages']");
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(svg))
                .findElement(By.xpath("./ancestor::div[@role='button' or @aria-selected]"));
        new Actions(driver)
                .moveToElement(button)
                .pause(Duration.ofMillis(300))
                .click()
                .perform();

        WebElement notNow = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='Not Now']")
                )
        );
        notNow.click();
        waiting(5);

        By searchInput = By.xpath("//input[@name='searchInput' and @type='text' and @placeholder='Search']");

        String value = "";
        for (int i = 0; i < userId.length(); i++) {
            WebElement searchBox = wait.until(
                    ExpectedConditions.presenceOfElementLocated(searchInput)
            );
            value += userId.charAt(i);
            js.executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                    searchBox, value);
        }

        // 1. Locate username text
        WebElement nameSpan = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//span[@title='" + userId + "']")
                )
        );
        // 2. Move up to clickable div
        WebElement chatRow = nameSpan.findElement(
                By.xpath("./ancestor::div[@role='button']")
        );
        // 3. Click using JS (BEST for Instagram)
        js.executeScript("arguments[0].click();", chatRow);

        WebElement messageBox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[@contenteditable='true' and @role='textbox']")
                )
        );
        js.executeScript("arguments[0].focus();", messageBox);
        messageBox.sendKeys(message);
        messageBox.sendKeys(Keys.ENTER);

    }

    @Test
    @Given("Run selenium facebook test case")
    public void function1() throws InterruptedException {
        driver.get("https://www.facebook.com");
        logger.info("error becuase of wait");
        String str = driver.getTitle();
        logger.info(str);
        logger.debug("title equal :");
        logger.warn("warn");
        logger.trace(str);
    }

}

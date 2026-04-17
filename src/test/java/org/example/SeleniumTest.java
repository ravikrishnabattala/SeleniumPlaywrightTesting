package org.example;

import com.google.gson.internal.bind.util.ISO8601Utils;
import com.microsoft.playwright.Keyboard;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.bytebuddy.asm.Advice;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SeleniumTest {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumTest.class);
    private static WebDriver driver;
    WebDriverWait wait;// Wait for up to 15 seconds
    JavascriptExecutor js;

    public SeleniumTest() {
        System.out.println("Selenium constructor...");
        driver = HooksTest.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    public void waiting(int seconds) {
        try {
            synchronized (driver) {
                driver.wait(seconds * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Given("Prerequisites to run test cases {string}")
    public void prerequisitesToRunTestCases(String testCaseId) {
        System.out.println("Test Case Id :" + testCaseId);
        MDC.put("testCaseId", testCaseId);
    }

    @Given("Login to Instagram userId = {string} and password = {string}")
    public void instagramLogin(String userId, String secret) throws IOException {

        String userName = System.getProperty("username", userId);
        String password = System.getProperty("password", secret);

        driver.get("https://www.instagram.com/accounts/login/?hl=en");

        By inputEmail = By.xpath("//input[@name='email']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));
        driver.findElement(inputEmail).sendKeys(userName + Keys.ENTER);

        WebElement inputPassword = driver.findElement(By.xpath("//input[@name='pass']"));
        inputPassword.sendKeys(password + Keys.ENTER);

        waiting(30);

        By notNowBtn = By.xpath("//div[@role='button' and .//text()[contains(.,'Not now')]]");
        wait.until(ExpectedConditions.elementToBeClickable(notNowBtn)).click();

        Set<Cookie> cookies = driver.manage().getCookies();

        ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream("insta_cookies.data"));
        oos.writeObject(cookies);
        oos.close();
    }

    @Given("Login to Instagram")
    public void instagramLogin() throws InterruptedException, IOException, ClassNotFoundException {

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
    }

    @Given("Send message {string} to user {string} on instagram")
    public void instagramSendingMessages(String message, String userId) throws InterruptedException, IOException, ClassNotFoundException {

        WebElement notNow = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='Not Now']")
                )
        );
        notNow.click();

        By svg = By.xpath("//*[name()='svg' and @aria-label='Messages']");
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(svg))
                .findElement(By.xpath("./ancestor::div[@role='button' or @aria-selected]"));

        new Actions(driver)
                .moveToElement(button)
                .pause(Duration.ofMillis(300))
                .click()
                .perform();

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

    @Then("Scroll reels per {int} minutes")
    public void scrollReelsPerTimeMinutes(int time) {

        By notNowBtn = By.xpath("//div[@role='dialog']//button[text()='Not Now']");
        WebElement notNow = wait.until(ExpectedConditions.visibilityOfElementLocated(notNowBtn));
        js.executeScript("arguments[0].click();", notNow);

        WebElement reelsLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@href,'/reels')]")
                )
        );
        js.executeScript("arguments[0].click();", reelsLink);

        WebElement audioButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[@aria-label='Video player']//div[@role='presentation']//div[@role='button']//div[@role='button']")
                )
        );
        js.executeScript("arguments[0].click();", audioButton);

        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime timeout = dateTime.plusMinutes(time);
        int k = (int) (time / 0.5);
        int i = 0;
        do {
            WebElement nextReel = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@aria-label='Navigate to next Reel']")
                    )
            );
            js.executeScript("arguments[0].scrollIntoView(true)", nextReel);
            nextReel.click();
            waiting(10);
            i++;
        } while (i <= k);

        WebElement previousReel = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[@aria-label='Navigate to previous Reel']")
                )
        );
        js.executeScript("arguments[0].scrollIntoView(true)", previousReel);
        previousReel.click();
    }

    @Then("Watch all stories")
    public void watchAllStories() {

        By notNowBtn = By.xpath("//div[@role='dialog']//button[text()='Not Now']");
        WebElement notNow = wait.until(ExpectedConditions.visibilityOfElementLocated(notNowBtn));
        js.executeScript("arguments[0].click();", notNow);

        By firstUnseenStory = By.xpath(
                "//div[@role='button' and contains(@aria-label,'Story by') and contains(@aria-label,'not seen')]"
        );
        WebElement story = wait.until(
                ExpectedConditions.elementToBeClickable(firstUnseenStory)
        );
        story.click();

        WebElement audioToggle = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[@aria-label='Toggle audio' and @role='button']")
                )
        );

        if (audioToggle.isDisplayed()) {
            js.executeScript("arguments[0].click()", audioToggle);
        }
        waiting(60);
    }

    @Given("Run selenium facebook test case")
    public void function1() {
        driver.get("https://www.facebook.com");
        logger.info("error becuase of wait");
        String str = driver.getTitle();
        logger.info(str);
        logger.debug("title equal :");
        logger.warn("warn");
        logger.trace(str);
    }

    @Then("Comment {string} post with {string}")
    public void commentUserPostWithMessage(String userId, String message) {

        By notNowBtn = By.xpath("//div[@role='dialog']//button[text()='Not Now']");
        WebElement notNow = wait.until(ExpectedConditions.visibilityOfElementLocated(notNowBtn));
        js.executeScript("arguments[0].click()", notNow);


        By index = By.xpath("//div[@data-visualcompletion='ignore-dynamic']");
        Actions mouse = new Actions(driver).moveToElement(driver.findElement(index));
        mouse.perform();

        WebElement searchButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@role='link']//*[name()='svg' and @aria-label='Search']/ancestor::a")
                )
        );
        searchButton.click();

        By searchInput = By.xpath("//input[@aria-label='Search input' and @type='text' and @placeholder='Search']");

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

        By profileLink = By.xpath("//a[@role='link' and contains(@href," + userId + ")]");

        WebElement profile = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        profileLink
                )
        );
        js.executeScript("arguments[0].click();", profile);

        String profileUrl = "https://www.instagram.com/" + userId;
        driver.navigate().to(profileUrl);

        By postLink = By.xpath("//a[contains(@href,'/" + userId + "/p/')]");
        List<WebElement> postLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(postLink));

        for (int i = 0; i < postLinks.size(); i++) {
            postLinks.get(i).click();

            String text = String.valueOf(message.charAt(i));
            By textArea = By.xpath("//textarea[@aria-label='Add a comment…']");
            WebElement comment = wait.until(ExpectedConditions.presenceOfElementLocated(textArea));
            js.executeScript("arguments[0].scrollIntoView()", comment);

            Actions actions = new Actions(driver);
            int width = 20 * text.length(); // adjust spacing
            int height = 20;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();

            // background
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            // text
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(text, 2, 15);

            StringBuilder fullText = new StringBuilder();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    fullText.append(pixel == Color.BLACK.getRGB() ? "*" : "-");
                }
                fullText.append("\n"); // newline
            }
            List<String> keys = List.of(fullText.toString().split("\n"));

            keys.forEach(System.out::println);
            actions.moveToElement(comment)
                    .click()
//                    .sendKeys(keys.get(0)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
//                    .sendKeys(keys.get(1)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(2)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(3)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(4)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(5)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(6)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(7)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(8)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(9)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(10)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(11)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(12)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(13)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(keys.get(14)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
//                    .sendKeys(keys.get(15)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
//                    .sendKeys(keys.get(16)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
//                    .sendKeys(keys.get(17)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
//                    .sendKeys(keys.get(18)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
//                    .sendKeys(keys.get(19)).keyDown(Keys.SHIFT).sendKeys(Keys.ENTER).keyUp(Keys.SHIFT)
                    .sendKeys(Keys.ENTER)
                    .perform();

            By closeBtn = By.xpath("//div[@role='button' and .//text()='Close']");
            WebElement close = wait.until(ExpectedConditions.visibilityOfElementLocated(closeBtn));
            wait.until(ExpectedConditions.elementToBeClickable(close));
            close.click();
        }
    }
}
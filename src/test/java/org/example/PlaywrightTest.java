package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;


public class PlaywrightTest {

    private static Browser browser;
    private static BrowserContext browserContext;
    private static Page page = null;
    private static final Logger logger = LoggerFactory.getLogger(PlaywrightTest.class);

    public PlaywrightTest() {
        page = HooksTest.getPage();
        browserContext = HooksTest.getContext();
        browser = HooksTest.getBrowser();
        System.out.println("Playwright constructor...");
        if (page == null) {
            throw new RuntimeException("Page is not initialized!");
        }
    }

    private void waiting(int seconds){
        try {
            synchronized(page){
                page.wait(seconds * 1000L);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Description("Locator for Google Search Box")
    private Locator getSearchBox() {
        return page.locator("textarea[name='q']");
    }

    @Description("Locator for Google Search Button")
    private Locator getSearchButton() {
        return page.locator("input[name='btnK']");
    }


    @Given("Login to Instagram userId = {string} and password = {string} playwright")
    public void loginToInstagram(String userId,String secret) throws IOException {

        String userName = System.getProperty("username", userId);
        String password = System.getProperty("password", secret);

        page.navigate("https://www.instagram.com/accounts/login/?hl=en");
        page.locator("//input[@name='email']").fill(userName);
        page.locator("//input[@name='pass']").fill(password);
        page.keyboard().press("Enter");
        page.locator("//div[@role='button' and .//text()[contains(.,'Not now')]]").click();

       browserContext.storageState(
               new BrowserContext.StorageStateOptions()
                       .setPath(Paths.get("insta_state.json"))
       );
    }

    @Given("Login to Instagram playwright")
    public void loginToInstagramPlaywright() {
        page.navigate("https://www.instagram.com/");
        waiting(10);
    }

    @Then("Send message {string} to user {string} on instagram playwright")
    public void sendMessageMessageToUserUserIdOnInstagramPlaywright(String message,String userId) {

        Locator messagesButton = page.locator(
                "xpath=//*[name()='svg' and @aria-label='Messages']" +
                        "/ancestor::div[@role='button']"
        );

        messagesButton.first().click(new Locator.ClickOptions().setForce(true));

        Locator notNowBtn = page.locator("xpath=//button[normalize-space()='Not Now']");
        if (notNowBtn.isVisible()) {
            notNowBtn.click();
        }

        Locator searchInput = page.locator(
                "xpath=//input[@name='searchInput' and @placeholder='Search']"
        );
        searchInput.fill(userId);

        Locator userButton = page.locator(
                "xpath=//span[@title='" + userId + "']/ancestor::div[@role='button']"
        );
        userButton.first().click(new Locator.ClickOptions().setForce(true));

        Locator messageBox = page.locator(
                "xpath=//div[@contenteditable='true' and @role='textbox']"
        );
        messageBox.fill(message);

        page.keyboard().press("Enter");

    }
}

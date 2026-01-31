package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import jdk.jfr.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;


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

}

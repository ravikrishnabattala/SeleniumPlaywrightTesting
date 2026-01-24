package org.example;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class PlaywrightTest {

    private static Page page = null;
    private static final Logger logger = LoggerFactory.getLogger(PlaywrightTest.class);


    public PlaywrightTest() {
        page = HooksTest.getPage();
        System.out.println("Playwright constructor...");
        if (page == null) {
            throw new RuntimeException("Page is not initialized!");
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

    @Test
    @DisplayName("It is a playwright test case")
    @Given("Run playwright test case")
    public void testing() {
        page.navigate("https://www.google.com/");
        randomDelay();

        getSearchBox().fill("ravikrishnabattala.netlify.app");
        getSearchBox().press("Enter");
        randomDelay();

        page.locator("h3").first();
        randomDelay(); // Add a random delay

        // Check for CAPTCHA
        logger.info("google browsing...");
        if (page.locator("text=Please show you're not a robot").isVisible()) {
            System.out.println("CAPTCHA detected. Manual intervention required.");
        }
    }

    public void randomDelay() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(1000) + 2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

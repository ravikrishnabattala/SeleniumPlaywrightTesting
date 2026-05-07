package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.an.E;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jdk.jfr.Description;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.nio.file.Paths;


public class PlaywrightTest {

    private static Browser browser;
    private static BrowserContext browserContext;
    private static Page page = null;
    private static List<String> easyApplies;
    private static final Logger logger = LoggerFactory.getLogger(PlaywrightTest.class);

    public PlaywrightTest() {
        page = HooksTest.getPage();
        browserContext = HooksTest.getContext();
        browser = HooksTest.getBrowser();
        easyApplies = new ArrayList<>();
        System.out.println("Playwright constructor...");
        if (page == null) {
            throw new RuntimeException("Page is not initialized!");
        }
    }

    private void waiting(int seconds) {
        try {
            synchronized (page) {
                page.wait(seconds * 1000L);
            }
        } catch (Exception e) {
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
    public void loginToInstagram(String userId, String secret) throws IOException {

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
    }

    @Given("Login to Linkedin username={string} & password={string}")
    public void loginToLinkedin(String username, String passcode) throws InterruptedException {
        page.navigate("https://www.linkedin.com/jobs/collections/recommended");
        Locator userName = page.locator("#username");
        userName.fill(username);
        Locator password = page.locator("#password");
        password.fill(passcode);
        page.locator("button[aria-label='Sign in']").click();
        page.waitForTimeout(10000);
//        page.locator("button[aria-label='Submit pin']").click();
        browserContext.storageState(
                new BrowserContext.StorageStateOptions()
                        .setPath(Paths.get("linkedin_cookies.json"))
        );
    }

    @Given("Apply recommended jobs")
    public void applyJobs() {
        page.navigate("https://www.linkedin.com/jobs/collections/recommended");
        List jobIds = (List) page.locator("[data-occludable-job-id]")
                .evaluateAll("els => els.map(e => e.getAttribute('data-occludable-job-id'))");

        Locator nextPage = page.locator("button[aria-label='View next page']");
        while (nextPage.isVisible()) {
            nextPage.click();
            List nextJobIds = (List) page.locator("[data-occludable-job-id]")
                    .evaluateAll("els => els.map(e => e.getAttribute('data-occludable-job-id'))");
            jobIds.addAll(nextJobIds);
        }
        for (int i = jobIds.size() - 1; i >= 0; i--) {
            String jobId = (String) jobIds.get(i);
            Page newPage = browserContext.newPage();
            try {
                newPage.navigate("https://www.linkedin.com/jobs/collections/recommended/?currentJobId=" + jobId);
                Locator job = newPage.locator("#jobs-apply-button-id").first();
                waitUntilVisibleOrSkip(job);
                continueToNext("button[aria-label='Continue to next step']", newPage);
                easyApplies.add(jobId);
                System.out.println(easyApplies);
                Locator review = newPage.locator("button[aria-label='Review your application']");
                waitUntilVisibleOrSkip(review);
                Locator submit = newPage.locator("button[aria-label='Submit application']");
                waitUntilVisibleOrSkip(submit);
                newPage.waitForTimeout(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Page> pages = browserContext.pages();
            for (Page p : pages) {
                p.close();
            }
        }
    }

    public void continueToNext(String xpath, Page newPage) {
        try {
            Locator continueNext = newPage.locator(xpath).first();
            continueNext.waitFor(new Locator.WaitForOptions().setTimeout(3000));
            int k = 0;
            while (continueNext.isVisible()) {
                k++;
                continueNext.click();
                continueNext.waitFor(new Locator.WaitForOptions().setTimeout(3000));
                if (k > 5) {
                    break;
                }
            }

        } catch (PlaywrightException p) {
            p.printStackTrace();
        }
    }

    public void waitUntilVisibleOrSkip(Locator locator) {
        if (locator.isVisible()) {
            locator.click();
        }
        try {
            locator.waitFor(new Locator.WaitForOptions().setTimeout(3000));
            if (locator.isVisible()) locator.click();
        } catch (PlaywrightException e) {
        }
    }

    @Then("Send message {string} to user {string} on instagram playwright")
    public void sendMessageMessageToUserUserIdOnInstagramPlaywright(String message, String userId) {
        page.locator("a[role='link'][href='/direct/inbox/']").click();
        page.click("text=Not Now");
        page.locator("[placeholder='Search']").waitFor();
        String value = "";
        for (int i = 0; i < userId.length(); i++) {
            value += userId.charAt(i);
            page.locator("[placeholder='Search']").fill(value);

        }


//        <a class="x1i10hfl xjbqb8w x1ejq31n x18oe1m7 x1sy0etr xstzfhl x972fbf x10w94by x1qh" +
//                "h985 x14e42zd x9f619 x1ypdohk xt0psk2 x3ct3a4 xdj266r x14z9mp xat24cr x1lzi" +
//                "wak xexx8yu xyri2b x18d9i69 x1c1uobl x16tdsg8 x1hl2dhg xggy1nq x1a2a7pz x4gyw5p" +
//                " _a6hd" href="/reels/" role="link" tabindex="0"><div class="x9f619 x3nfvp2 x1obq294 x5a5i1n xde0f50 x15x8krk xr9ek0c xjpr12u xo237n4 x6pnmvc x7nr27j x12dmmrz xz9dl7a xpdmqnj xsag5q8 x1g0dm76 x80pfx3 x159b3zp"><div><div class="html-div xdj266r x14z9mp xat24cr x1lziwak xexx8yu xyri2b x18d9i69 x1c1uobl x9f619 xjbqb8w x78zum5 x15mokao x1ga7v0g x16uus16 xbiv7yw x1n2onr6 x1plvlek xryxfnj x1c4vz4f x2lah0s xdt5ytf xqjyukv x1qjc9v5 x1oa3qoh x1nhvcw1"><div aria-selected="false" class="x9f619 xxk0z11 xii2z7h x11xpdln x19c4wfv xvy4d1p"><svg aria-label="Reels" class="x1lliihq x1n2onr6 x5n08af" fill="currentColor" height="24" role="img" viewBox="0 0 24 24" width="24"><title>Reels</title><path d="M22.935 7.468c-.063-1.36-.307-2.142-.512-2.67a5.341 5.341 0 0 0-1.27-1.95 5.345 5.345 0 0 0-1.95-1.27c-.53-.206-1.311-.45-2.672-.513C15.333 1.012 14.976 1 12 1s-3.333.012-4.532.065c-1.36.063-2.142.307-2.67.512-.77.298-1.371.69-1.95 1.27a5.36 5.36 0 0 0-1.27 1.95c-.206.53-.45 1.311-.513 2.672C1.012 8.667 1 9.024 1 12s.012 3.333.065 4.532c.063 1.36.307 2.142.512 2.67.297.77.69 1.372 1.27 1.95.58.581 1.181.974 1.95 1.27.53.206 1.311.45 2.672.513C8.667 22.988 9.024 23 12 23s3.333-.012 4.532-.065c1.36-.063 2.142-.307 2.67-.512a5.33 5.33 0 0 0 1.95-1.27 5.356 5.356 0 0 0 1.27-1.95c.206-.53.45-1.311.513-2.672.053-1.198.065-1.555.065-4.531s-.012-3.333-.065-4.532Zm-1.998 8.972c-.05 1.07-.228 1.652-.38 2.04-.197.51-.434.874-.82 1.258a3.362 3.362 0 0 1-1.258.82c-.387.151-.97.33-2.038.379-1.162.052-1.51.063-4.441.063s-3.28-.01-4.44-.063c-1.07-.05-1.652-.228-2.04-.38a3.354 3.354 0 0 1-1.258-.82 3.362 3.362 0 0 1-.82-1.258c-.151-.387-.33-.97-.379-2.038C3.011 15.28 3 14.931 3 12s.01-3.28.063-4.44c.05-1.07.228-1.652.38-2.04.197-.51.434-.875.82-1.26a3.372 3.372 0 0 1 1.258-.819c.387-.15.97-.329 2.038-.378C8.72 3.011 9.069 3 12 3s3.28.01 4.44.063c1.07.05 1.652.228 2.04.38.51.197.874.433 1.258.82.385.382.622.747.82 1.258.151.387.33.97.379 2.038C20.989 8.72 21 9.069 21 12s-.01 3.28-.063 4.44Zm-4.584-6.828-5.25-3a2.725 2.725 0 0 0-2.745.01A2.722 2.722 0 0 0 6.988 9v6c0 .992.512 1.88 1.37 2.379.432.25.906.376 1.38.376.468 0 .937-.123 1.365-.367l5.25-3c.868-.496 1.385-1.389 1.385-2.388s-.517-1.892-1.385-2.388Zm-.993 3.04-5.25 3a.74.74 0 0 1-.748-.003.74.74 0 0 1-.374-.649V9a.74.74 0 0 1 .374-.65.737.737 0 0 1 .748-.002l5.25 3c.341.196.378.521.378.652s-.037.456-.378.651Z"></path></svg></div></div></div><div class="x6s0dn4 x9f619 xxk0z11 x6ikm8r xeq5yr9 xf7dkkf x78zum5" style="display: none; opacity: 0; transform: translateX(-16px);"><div style="width: 100%;"><div class="" style="width: 100%;"><span class="x1lliihq x1plvlek xryxfnj x1n2onr6 xyejjpt x15dsfln x193iq5w xeuugli x1fj9vlw x13faqbe x1vvkbs x1s928wv xhkezso x1gmr53x x1cpjm7i x1fgarty x1943h6x x1i0vuye xl565be xo1l8bm x5n08af x1tu3fi x3x7a5m x10wh9bi xpm28yp x8viiok x1o7cslx" dir="auto" style="--x---base-line-clamp-line-height: 20px; --x-lineHeight: 20px;">
//            <span class="x1lliihq x193iq5w x6ikm8r x10wlt62 xlyipyv xuxw1ft">Reels</span></span></div></div></div></div></a>

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


    @Given("Login to Naukri userId = {string} and password = {string}")
    public void loginToNaukri(String username, String passcode) throws InterruptedException {
        page.navigate("https://www.naukri.com/mnjuser/recommendedjobs");
        Locator userName = page.locator("#usernameField");
        userName.fill(username);
        Locator password = page.locator("#passwordField");
        password.fill(passcode);
        page.locator("button[type='Submit']").first().click();
        page.waitForTimeout(10000);
        browserContext.storageState(
                new BrowserContext.StorageStateOptions()
                        .setPath(Paths.get("naukri_cookies.json"))
        );
    }

    @Given("Apply naukri jobs")
    public void applyNaukriJobs() {
        page.navigate("https://www.naukri.com/mnjuser/recommendedjobs");
        List jobIds = (List) page.locator("[data-job-id]")
                .evaluateAll("els => els.map(e => e.getAttribute('data-job-id'))");

        for (int i = 0; i < jobIds.size(); i++) {
            String jobId = (String) jobIds.get(i);
            Page newPage = browserContext.waitForPage(() -> {
                page.locator("article[data-job-id='" + jobId + "']").click();
            });

            continueToNext("#apply-button", newPage);
            newPage.close();
        }
    }

    @Given("Share Interest to Job")
    public void shareInterest() {
        page.navigate("https://www.naukri.com/mnjuser/recommended-earjobs");
        Locator shareInterestBtn = page.locator("text=Share interest");
        while (shareInterestBtn.count() > 0) {
            shareInterestBtn.first().click();
            shareInterestBtn = page.locator("text=Share interest");
        }

    }

}

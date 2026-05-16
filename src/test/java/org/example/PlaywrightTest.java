package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.RequestOptions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class PlaywrightTest {

    private Browser browser;
    private BrowserContext browserContext;
    private Page page = null;
    private List<String> easyApplies;
    private final Logger logger = LoggerFactory.getLogger(PlaywrightTest.class);

    public PlaywrightTest() {
        this.page = HooksTest.getPage();
        this.browserContext = HooksTest.getContext();
        this.browser = HooksTest.getBrowser();
        this.easyApplies = new ArrayList<>();
        System.out.println("Playwright constructor...");
        if (this.page == null) {
            throw new RuntimeException("Page is not initialized!");
        }
    }

    private void waiting(int seconds) {
        try {
            synchronized (page) {
                page.wait(seconds * 1000L);
            }
            page.waitForTimeout(3000);
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
        page.navigate("https://www.linkedin.com/jobs/collections/easy-apply", new Page.NavigateOptions().setTimeout(60000));
        List jobIds = (List) page.locator("[data-occludable-job-id]")
                .evaluateAll("els => els.map(e => e.getAttribute('data-occludable-job-id'))");

        Locator nextPage = page.locator("button[aria-label='View next page']");
        while (nextPage.isVisible()) {
            nextPage.click();
            List nextJobIds = (List) page.locator("[data-occludable-job-id]")
                    .evaluateAll("els => els.map(e => e.getAttribute('data-occludable-job-id'))");
            jobIds.addAll(nextJobIds);
        }
        System.out.println("Job Ids :" + jobIds);
        for (int i = 0; i <= jobIds.size(); i++) {
            String jobId = (String) jobIds.get(i);
            Page newPage = browserContext.newPage();
            try {
                newPage.navigate("https://www.linkedin.com/jobs/collections/easy-apply/?currentJobId=" + jobId);
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
                page.waitForTimeout(3000);
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
        Page newPage = browserContext.waitForPage(() -> {
            Selector selector = (Selector) page.locator("#id");
            page.click(selector.toString());
        });

        List<Page> pages = browserContext.pages();

        Locator locator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("button"));
        Locator locator1 = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("tr").setExact(true));

        APIRequestContext apiRequestContext = new HooksTest().getPlaywright().request().newContext();
        APIResponse apiResponse = apiRequestContext.get("r");
        apiResponse.status();
        apiRequestContext.put("", RequestOptions.create().setData(""));

        page.screenshot(new Page.ScreenshotOptions().setFullPage(true).setTimeout(1000).setPath(Paths.get("")));
        page.getByTestId("");
        Playwright playwright = new HooksTest().getPlaywright();
        playwright.selectors().setTestIdAttribute("ir-tt");
        Locator locator2 = page.locator("");
        locator2.click(new Locator.ClickOptions().setForce(true));
        Assertions.assertTrue(locator2.isVisible());
        Page newpage = browserContext.waitForPage(
                () -> {
                    page.locator("").click();
                }
        );
        page.locator("li").filter(new Locator.FilterOptions().setHasText("name")).first().click();
        page.keyboard().type("ty", new Keyboard.TypeOptions().setDelay(1000));
    }
}

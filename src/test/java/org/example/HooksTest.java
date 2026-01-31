package org.example;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;


public class HooksTest {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    private static WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(HooksTest.class);

    @BeforeAll
    @io.cucumber.java.BeforeAll
    public static void beforeAllHook() {
        System.out.println("Junit & Cucumber Before All run...");
    }

    @BeforeEach
    public void junitBeforeHook() {
        System.out.println("Junit before run...");
        initiateBrowsers();
        TestInfo testInfo = null;
        setUp(testInfo);
    }

    @Before
    public void cucumberBeforeHook(Scenario scenario) {
        System.out.println("Cucumber before run...");
        initiateBrowsers();
        TestInfo testInfo = null;
//        setUp(testInfo);
    }

    public static void initiateBrowsers() {
        logger.info("Initializing Playwright...");
        try {
            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
            launchOptions.setHeadless(false);
            launchOptions.setSlowMo(100);
            playwright = Playwright.create();
            browser = playwright.chromium().launch(launchOptions);

            Map headers = new HashMap();
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            headers.put("Accept-Language", "en-US,en;q=0.9");

            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
            contextOptions.setExtraHTTPHeaders(headers);
            contextOptions.setViewportSize(null);
            contextOptions.setStorageStatePath(Paths.get("insta_state.json"));

            context = browser.newContext(contextOptions);
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true));
            page = context.newPage();
            logger.info("Playwright initialized successfully.");
        } catch (Exception e) {
            logger.error("Error initializing Playwright", e);
            e.printStackTrace();
            throw new RuntimeException("Playwright initialization failed", e);
        }
    }

    public void setUp(TestInfo testInfo) {
        logger.info("Initializing Selenium WebDriver...");
        if (driver != null) {
            driver.quit();
        }
        try {
            ChromeOptions options = new ChromeOptions();
//        options.setBrowserVersion("latest");
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            Duration duration = Duration.of(2, ChronoUnit.SECONDS);
//        Proxy proxy = new Proxy();
//        proxy.setHttpProxy("https");
//        options.setProxy(proxy);
            options.setScriptTimeout(duration);
            driver = new ChromeDriver(options);
            driver.manage().window().setPosition(new Point(0, 0));
            driver.manage().window().setSize(new Dimension(1920, 1080));
            logger.info("Selenium WebDriver started. Window Handle: {}", driver.getWindowHandle());
//            ((JavascriptExecutor) driver).executeScript("window.open();");
//            driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(1));
//        ((JavascriptExecutor) driver).executeScript("window.open('', '_blank', 'width=800,height=600');");
            if (testInfo != null && !testInfo.getTags().isEmpty()) {
                MDC.put("testCaseId", testInfo.getTags().iterator().next());
            } else {
                MDC.put("testCaseId", "UnknownTestCase");
            }

//            MDC.put("testCaseId", (String) testInfo.getTags().toArray()[0]);
        } catch (Exception e) {
            logger.error("Error initializing WebDriver", e);
            throw new RuntimeException("WebDriver initialization failed", e);
        }
    }

    public static Page getPage() {
        return page;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static BrowserContext getContext() {
        return context;
    }

    public static Browser getBrowser() {
        return browser;
    }

    public Playwright getPlaywright() {
        return playwright;
    }

    @AfterEach
    @After
    public void afterExecuteHook() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("Cucumber & Junit After each run... " + MDC.get("testCaseId"));
        MDC.clear();
        if (driver != null) {
            driver.quit();
            logger.info("Quiting Driver!!!");
            driver = null;
        }
    }

    @AfterAll
    @io.cucumber.java.AfterAll
    public static void shutDownBrowsersHook() {
        System.out.println("Cucumber & Junit After all run...");
        logger.info("Web Driver Removed");
        page.close();
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
        context.close();
        browser.close();
        playwright.close();
    }
}

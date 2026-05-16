package org.example;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.http.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;


public class HooksTest {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(HooksTest.class);

    @Before
    public void cucumberBeforeHook(Scenario scenario) {
        System.out.println("Cucumber before run...");
//        initiateBrowsers();
        TestInfo testInfo = null;
        setUp(testInfo);
    }

    public static void initiateBrowsers() {
        logger.info("Initializing Playwright...");
        try {
            System.out.println(
                    "Launching Browser On Thread -> "
                            + Thread.currentThread().getName()
            );
            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
            launchOptions.setHeadless(false);
            launchOptions.setSlowMo(100);
//            launchOptions.setChannel("chrome");
            playwright.set(Playwright.create());
            browser.set(
                    playwright.get()
                            .chromium()
                            .launch(launchOptions)
            );

            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            headers.put("Accept-Language", "en-US,en;q=0.9");

            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
            contextOptions.setExtraHTTPHeaders(headers);
            contextOptions.setViewportSize(null);
//            contextOptions.setStorageStatePath(Paths.get("insta_state.json"));
            contextOptions.setStorageStatePath(Paths.get("naukri_cookies.json"));
//            contextOptions.setStorageStatePath(Paths.get("linkedin_cookies.json"));
            context.set(
                    browser.get().newContext(contextOptions)
            );
            context.get().tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true));
            page.set(
                    context.get().newPage()
            );
            logger.info("Playwright initialized successfully.");
        } catch (Exception e) {
            logger.error("Error initializing Playwright", e);
            e.printStackTrace();
            throw new RuntimeException("Playwright initialization failed", e);
        }
    }

    public void setUp(TestInfo testInfo) {
        logger.info("Initializing Selenium WebDriver...");
        System.out.println(
                "Launching Browser On Thread -> "
                        + Thread.currentThread().getName()
        );
        if (driver.get() != null) {
            driver.get().quit();
        }
        try {
            ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless=new");
//            options.addArguments("--incognito");
            ChromeDriverService service = new ChromeDriverService.Builder().usingAnyFreePort().build();
//        options.setBrowserVersion("latest");
            options.setAcceptInsecureCerts(true);
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            Duration duration = Duration.of(2, ChronoUnit.SECONDS);
//        Proxy proxy = new Proxy();
//        proxy.setHttpProxy("https");
//        options.setProxy(proxy);
            ClientConfig config = ClientConfig.defaultConfig();
            options.setScriptTimeout(duration);
            driver.set(
                    new ChromeDriver(service, options, config)
            );
            driver.get().manage().window().setPosition(new Point(0, 0));
            driver.get().manage().window().setSize(new Dimension(1920, 1080));
            logger.info("Selenium WebDriver started. Window Handle: {}", driver.get().getWindowHandle());
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
        return page.get();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static BrowserContext getContext() {
        return context.get();
    }

    public static Browser getBrowser() {
        return browser.get();
    }

    public static Playwright getPlaywright() {
        return playwright.get();
    }

    @After
    public void afterExecuteHook(Scenario scenario) {
        try {
            System.out.println(
                    "Closing Thread -> "
                            + Thread.currentThread().getName()
            );
            MDC.clear();
            Paths.get("traces").toFile().mkdirs();
            String traceName =
                    scenario.getName()
                            .replace(" ", "_")
                            + "_"
                            + System.currentTimeMillis()
                            + ".zip";
            if (page.get() != null) {
                page.get().close();
            }
            if (context.get() != null) {
                context.get().tracing().stop(
                        new Tracing.StopOptions()
                                .setPath(
                                        Paths.get("traces/" + traceName)
                                )
                );
                context.get().close();
            }
            if (browser.get() != null) {
                browser.get().close();
            }
            if (playwright.get() != null) {
                playwright.get().close();
            }
            if (driver.get() != null) {
                driver.get().quit();
            }
        } finally {
            page.remove();
            context.remove();
            browser.remove();
            playwright.remove();
            driver.remove();
        }
    }
}

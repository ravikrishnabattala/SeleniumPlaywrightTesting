package org.example;

import io.cucumber.java.en.Given;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.Duration;

public class SeleniumTest {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumTest.class);
    private static WebDriver driver;

    public SeleniumTest() {
        System.out.println("Selenium constructor...");
        driver = HooksTest.getDriver();
    }

    @Given("Prerequisites to run test cases {string}")
    public void prerequisitesToRunTestCases(String testCaseId) {
        System.out.println("Test Case Id :" + testCaseId);
        MDC.put("testCaseId", testCaseId);
    }

    @Test
    @Given("Run selenium google test case")
    public void function() throws InterruptedException {
        driver.get("https://www.google.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait for up to 10 seconds
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q"))); // Wait for search box to be present
        WebElement searchQuery = driver.findElement(By.xpath("//textarea[@name='q']"));
        searchQuery.sendKeys("Hello world!!!" + Keys.ENTER);
        synchronized (driver) {
            driver.wait(4000);
        }
        String str = driver.getTitle();
//        assertEquals(str, "Google");
//        logger.error("Search box found after wait");
//        logger.info(str);
//        logger.debug("title equal :"+str);
//        logger.warn("warn");
//        logger.trace(str);
        By ele = RelativeLocator.with(By.className("s")).near(By.className("s"));
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

package ru.swaglabs.tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.swaglabs.pages.LoginPage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080", "--disable-gpu", "--no-sandbox");
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (!result.isSuccess() && driver != null) {
            saveScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    private void saveScreenshot(String testName) {
        try {
            Path directory = Path.of("target", "screenshots");
            Files.createDirectories(directory);
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), directory.resolve(testName + ".png"));
        } catch (Exception ignored) {
        }
    }
}
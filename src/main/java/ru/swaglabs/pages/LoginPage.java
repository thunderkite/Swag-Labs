package ru.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.swaglabs.config.TestConfig;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.TIMEOUT_SECONDS));
    }

    public LoginPage open() {
        driver.get(TestConfig.BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(username));
        return this;
    }

    public InventoryPage loginAs(String login, String userPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).sendKeys(login);
        driver.findElement(password).sendKeys(userPassword);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new InventoryPage(driver);
    }

    public LoginPage loginExpectingError(String login, String userPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).sendKeys(login);
        driver.findElement(password).sendKeys(userPassword);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return this;
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
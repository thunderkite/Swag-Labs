package ru.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.swaglabs.config.TestConfig;

import java.time.Duration;

public class CheckoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.cssSelector("[data-test='continue']");
    private final By finishButton = By.cssSelector("[data-test='finish']");
    private final By completeHeader = By.cssSelector("[data-test='complete-header']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.TIMEOUT_SECONDS));
    }

    public CheckoutPage fillCustomerData(String first, String last, String postal) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(first);
        driver.findElement(lastName).sendKeys(last);
        driver.findElement(postalCode).sendKeys(postal);
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        return this;
    }

    public CheckoutPage finish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
        return this;
    }

    public String getCompleteHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader)).getText();
    }
}
package ru.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.swaglabs.config.TestConfig;

import java.time.Duration;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By checkoutButton = By.cssSelector("[data-test='checkout']");
    private final By itemName = By.cssSelector("[data-test='inventory-item-name']");
    private final By itemPrice = By.cssSelector("[data-test='inventory-item-price']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.TIMEOUT_SECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
    }

    public String getItemName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemName)).getText();
    }

    public String getItemPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemPrice)).getText();
    }

    public CheckoutPage checkout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        return new CheckoutPage(driver);
    }
}
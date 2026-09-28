package ru.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.swaglabs.config.TestConfig;

import java.time.Duration;
import java.util.List;

public class InventoryPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By title = By.cssSelector("[data-test='title']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By sortSelect = By.cssSelector("[data-test='product-sort-container']");
    private final By productNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPrices = By.cssSelector("[data-test='inventory-item-price']");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.TIMEOUT_SECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(title));
    }

    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    public InventoryPage addProduct(String productName) {
        By addButton = By.cssSelector("[data-test='add-to-cart-" + productName.toLowerCase().replace(" ", "-") + "']");
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        return this;
    }

    public String getCartCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public InventoryPage sortBy(String visibleText) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(sortSelect))).selectByVisibleText(visibleText);
        return this;
    }

    public List<String> getProductNames() {
        return driver.findElements(productNames).stream().map(element -> element.getText()).toList();
    }

    public List<String> getProductPrices() {
        return driver.findElements(productPrices).stream().map(element -> element.getText()).toList();
    }

    public CartPage openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        return new CartPage(driver);
    }
}
package ru.swaglabs.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.swaglabs.config.TestConfig;
import ru.swaglabs.pages.CartPage;
import ru.swaglabs.pages.CheckoutPage;
import ru.swaglabs.pages.InventoryPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwagLabsTest extends BaseTest {
    @Test
    public void standardUserCanLogIn() {
        InventoryPage inventoryPage = loginPage.open().loginAs(TestConfig.STANDARD_USER, TestConfig.PASSWORD);

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "Ожидался переход на страницу товаров");
        Assert.assertEquals(inventoryPage.getTitle(), "Products", "Ожидался заголовок Products");
    }

    @Test
    public void lockedOutUserSeesLoginError() {
        loginPage.open().loginExpectingError(TestConfig.LOCKED_OUT_USER, TestConfig.PASSWORD);

        Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface"), "Ожидалось сообщение о заблокированном пользователе");
    }

    @Test
    public void userCanAddProductToCart() {
        InventoryPage inventoryPage = loginPage.open().loginAs(TestConfig.STANDARD_USER, TestConfig.PASSWORD);
        CartPage cartPage = inventoryPage.addProduct("Sauce Labs Backpack").openCart();

        Assert.assertEquals(cartPage.getItemName(), "Sauce Labs Backpack", "В корзине ожидался выбранный товар");
        Assert.assertEquals(cartPage.getItemPrice(), "$29.99", "Цена товара в корзине не совпала с ожидаемой");
    }

    @Test
    public void userCanCompleteCheckout() {
        CheckoutPage checkoutPage = loginPage.open()
                .loginAs(TestConfig.STANDARD_USER, TestConfig.PASSWORD)
                .addProduct("Sauce Labs Backpack")
                .openCart()
                .checkout()
                .fillCustomerData("Иван", "Иванов", "101000");

        Assert.assertEquals(checkoutPage.finish().getCompleteHeader(), "Thank you for your order!", "Ожидалось подтверждение заказа");
    }

    @Test
    public void productsCanBeSortedByPriceAscending() {
        InventoryPage inventoryPage = loginPage.open().loginAs(TestConfig.STANDARD_USER, TestConfig.PASSWORD);
        inventoryPage.sortBy("Price (low to high)");

        List<Double> actualPrices = inventoryPage.getProductPrices().stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .toList();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        Assert.assertEquals(actualPrices, expectedPrices, "Товары должны быть отсортированы по возрастанию цены");
    }
}
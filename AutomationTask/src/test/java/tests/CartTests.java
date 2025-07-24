package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

public class CartTests extends BaseTest {
    @Test
    public void addRemoveProductFromCart() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");
        ProductsPage products = new ProductsPage(driver);
        products.addProductToCart("Sauce Labs Backpack");
        products.openCart();

        CartPage cart = new CartPage(driver);
        cart.removeProduct("Sauce Labs Backpack");

        Assert.assertTrue(driver.findElements(By.className("cart_item")).isEmpty());
    }
}


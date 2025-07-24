package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.ProductsPage;

public class ProductFilterTests extends BaseTest {
    @Test
    public void sortProductsLowToHigh() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");
        ProductsPage products = new ProductsPage(driver);
        products.sortByLowToHigh();
        Assert.assertTrue(products.isSortedLowToHigh());
    }
}

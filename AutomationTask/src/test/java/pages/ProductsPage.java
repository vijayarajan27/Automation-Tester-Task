package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductsPage {

	private WebDriver driver;
    private By sortDropdown = By.className("product_sort_container");
    private By productPrices = By.className("inventory_item_price");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void sortByLowToHigh() {
        driver.findElement(sortDropdown).sendKeys("Price (low to high)");
    }

    public boolean isSortedLowToHigh() {
        List<WebElement> prices = driver.findElements(productPrices);
        List<Double> actual = new ArrayList<>();
        for (WebElement price : prices) {
            actual.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        List<Double> sorted = new ArrayList<>(actual);
        Collections.sort(sorted);
        return actual.equals(sorted);
    }

    public void addProductToCart(String productName) {
        driver.findElement(By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button")).click();
    }

    public void openCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }
}

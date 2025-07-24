package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
	private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void removeProduct(String productName) {
        driver.findElement(By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='cart_item']//button"))
                .click();
    }

    public void proceedToCheckout() {
        driver.findElement(By.id("checkout")).click();
    }

}

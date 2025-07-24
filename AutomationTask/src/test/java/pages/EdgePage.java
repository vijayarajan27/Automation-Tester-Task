package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class EdgePage {
    private WebDriver driver;

    public EdgePage(WebDriver driver) {
        this.driver = driver;
    }

    public void resizeWindow(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
    }

    public boolean isLoginButtonVisible() {
        return driver.findElement(By.id("login-button")).isDisplayed();
    }
}

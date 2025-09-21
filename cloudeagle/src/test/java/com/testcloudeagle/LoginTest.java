package com.testcloudeagle;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pagecloudeagle.LoginPage;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test(description = "Valid login flow with explicit waits")
    public void testValidLogin() {
        // Navigate to login page
        driver.get("https://sandbox20.cloudeagle.info/app/application");

        // Perform login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("support+sandbox20@cloudeagle.ai", "[x1nkS6]8~f]A#U;");

        // Wait for the page to redirect to /dashboard or /application
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/app/dashboard"),
                ExpectedConditions.urlContains("/app/application")
        ));

        // Assert login success
        boolean loggedIn = driver.getCurrentUrl().contains("/app/dashboard")
                || driver.getCurrentUrl().contains("/app/application");
        Assert.assertTrue(loggedIn, "Login failed or did not redirect to dashboard/application page");

        System.out.println("Login successful! Current URL: " + driver.getCurrentUrl());
    }
}

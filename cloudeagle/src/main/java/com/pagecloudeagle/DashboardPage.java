package com.pagecloudeagle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By loader = By.xpath("//div[contains(@class,'loader_loaderContainer')]");
    private By menuIcon = By.xpath("//button[contains(@class,'style_variant-button2__HekAU')]");
    private By dashboardAppLink = By.xpath("//a[@href='/app/dashboard']//div[contains(@class,'apphome_options__dtn1u')]");
    private By managedApplicationsCard = By.xpath("//div[@id='dashboard-hello-test']//div[contains(@class,'dashboard_dashboardCardWrapper1__dfRFs')]/div[1]/div[1]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void waitForLoaderToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    public void openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(menuIcon)).click();
    }

    public void goToDashboardApp() {
        wait.until(ExpectedConditions.elementToBeClickable(dashboardAppLink)).click();
    }

    public int getManagedApplicationsCount() {
        WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(managedApplicationsCard));
        String text = card.getText().trim();
        return extractFirstNumber(text);
    }

    public void clickManagedApplicationsCard() {
        WebElement card = wait.until(ExpectedConditions.elementToBeClickable(managedApplicationsCard));
        card.click();
    }

    private int extractFirstNumber(String text) {
        Matcher m = Pattern.compile("\\d+").matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group());
        }
        return 0;
    }
}
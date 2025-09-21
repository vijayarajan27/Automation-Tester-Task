package com.pagecloudeagle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator for the element showing total applications count, e.g. "30 of 30 Application"
    private By applicationsCountText = By.xpath("//span[contains(normalize-space(),'Application')]");

    // Locator for the list/table rows of applications - adjust if needed
    private By applicationRows = By.xpath("//table[contains(@class,'applications-table')]/tbody/tr");

    public ApplicationsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public int getApplicationsCountFromText() {
        WebElement countElement = wait.until(ExpectedConditions.visibilityOfElementLocated(applicationsCountText));
        String text = countElement.getText().trim();
        return extractFirstNumber(text);
    }

    public int getApplicationsCountFromList() {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(applicationRows));
        return rows.size();
    }

    private int extractFirstNumber(String text) {
        Matcher m = Pattern.compile("\\d+").matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group());
        }
        return 0;
    }
}
package com.testcloudeagle;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pagecloudeagle.ApplicationsPage;
import com.pagecloudeagle.DashboardPage;
import com.pagecloudeagle.LoginPage;

public class DashboardTest extends BaseTest {

    @Test(description = "Validate Managed Applications count matches Applications page count")
    public void testManagedApplicationsCount() {
        driver.get("https://sandbox20.cloudeagle.info/app/application");

        // Login first
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("support+sandbox20@cloudeagle.ai", "[x1nkS6]8~f]A#U;");

        DashboardPage dashboardPage = new DashboardPage(driver);

        // Wait for loader to disappear and navigate to dashboard app
        dashboardPage.waitForLoaderToDisappear();
        dashboardPage.openMenu();
        dashboardPage.goToDashboardApp();

        // Get count from dashboard card
        int countFromDashboard = dashboardPage.getManagedApplicationsCount();

        // Click the card to go to applications page
        dashboardPage.clickManagedApplicationsCard();

        ApplicationsPage applicationsPage = new ApplicationsPage(driver);

        // Get count from applications page text
        int countFromApplicationsPage = applicationsPage.getApplicationsCountFromText();

        System.out.println("Dashboard count: " + countFromDashboard);
        System.out.println("Applications page count: " + countFromApplicationsPage);

        Assert.assertEquals(countFromDashboard, countFromApplicationsPage,
                "Mismatch between dashboard and applications page counts");
    }
}
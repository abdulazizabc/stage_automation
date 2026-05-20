package tests.Login;

import Base.BasePage;
import Base.BaseTest;
import helpers.LoginHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.TrailerPage;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTest extends BaseTest {

    private LoginHelper loginHelper;
    private TrailerPage trailerPage;

    @BeforeEach
    void setUpPages(){

        loginHelper = new LoginHelper(driver);
        trailerPage = new TrailerPage(driver);

        loginHelper.login("account_admin_amazon", "String1!");
    }

    @Test
    void logOutSuccess() throws InterruptedException {
        trailerPage.openProfileMenu();
        trailerPage.clickLogout();
        trailerPage.confirmLogout();
        assertTrue(
                driver.getCurrentUrl().contains("/login")
        );
        assertTrue(
                trailerPage.isTextVisible("Log in")
        );
    }

}

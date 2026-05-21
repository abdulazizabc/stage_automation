package tests.login;

import base.AuthenticatedTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.HeaderPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutTest extends AuthenticatedTest {

    private HeaderPage headerPage;
    private LoginPage loginPage;

    @BeforeEach
    void initPages() {
        headerPage = new HeaderPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test
    void logoutReturnsUserToLoginPage() {
        headerPage.logout();

        assertTrue(driver.getCurrentUrl().contains("/login"), "URL should contain /login after logout");
        loginPage.waitForLoginPage();
        assertTrue(headerPage.isLoginTitleVisible(), "Log in title should be visible after logout");
    }
}

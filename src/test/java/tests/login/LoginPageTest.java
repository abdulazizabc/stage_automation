package tests.login;

import base.BaseTest;
import helpers.ConfigReader;
import helpers.LoginHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    void openLoginPage() {
        loginPage = new LoginPage(driver);
        loginPage.openFreshSession();
    }

    @Test
    void loginPageElementsAreVisible() {
        assertTrue(loginPage.isAgreementVisible(), "Agreement text should be visible");
        assertTrue(loginPage.isLoginButtonVisible(), "Log in button should be visible");
        assertTrue(loginPage.isPasswordFieldVisible(), "Password field should be visible");
        assertTrue(loginPage.isUsernameFieldVisible(), "Username field should be visible");
    }

    @Test
    void emptyLoginShowsValidationErrors() {
        loginPage.clickLogin();

        assertEquals(
                "Password is a required field",
                loginPage.getFieldText(LoginPage.PASSWORD_ERROR),
                "Password validation text should match"
        );
        assertEquals(
                "Username is a required field",
                loginPage.getFieldText(LoginPage.USERNAME_ERROR),
                "Username validation text should match"
        );
    }

    @Test
    void passwordVisibilityIconIsVisible() {
        loginPage.enterPassword("123456");
        assertTrue(
                loginPage.isPasswordVisibilityToggleVisible(),
                "Password visibility toggle should be visible"
        );
    }

    @Test
    void passwordVisibilityCanBeToggled() {
        assertEquals("password", loginPage.getPasswordInputType(), "Password field should be masked initially");

        loginPage.enterPassword("1212121");
        loginPage.togglePasswordVisibility();

        assertEquals("text", loginPage.getPasswordInputType(), "Password field should be visible after toggle");
    }

    @Test
    void termsOfServiceLinkOpensCorrectPage() {
        loginPage.clickLinkWithJavaScript(LoginPage.TERMS_OF_SERVICE_LINK);
        wait.until(ExpectedConditions.urlContains("terms-of-service"));

        assertEquals("https://www.mgkeld.com/terms-of-service", driver.getCurrentUrl());
        driver.navigate().back();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.USERNAME));
    }

    @Test
    void privacyPolicyLinkOpensCorrectPage() {
        loginPage.clickLinkWithJavaScript(LoginPage.PRIVACY_POLICY_LINK);
        wait.until(ExpectedConditions.urlContains("privacy-policy"));

        assertEquals("https://www.mgkeld.com/privacy-policy", driver.getCurrentUrl());
        driver.navigate().back();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.USERNAME));
    }

    @Test
    void successfulLoginRedirectsToDashboard() {
        LoginHelper loginHelper = new LoginHelper(driver);
        loginHelper.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        assertEquals(ConfigReader.getDashboardUrl(), driver.getCurrentUrl());
    }

    @Test
    void invalidCredentialsKeepUserOnLoginPage() {
        loginPage.login("invalid_user_" + System.currentTimeMillis(), "WrongPass1!");
        assertTrue(
                loginPage.isInvalidCredentialsErrorVisible(),
                "Invalid login should show an error or keep user on login page"
        );
        assertTrue(loginPage.isOnLoginPage(), "User should remain on login page after failed login");
    }
}

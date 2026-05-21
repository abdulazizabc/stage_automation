package pages;

import base.BasePage;
import helpers.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    public static final By USERNAME = By.id("username");
    public static final By PASSWORD = By.id("password");
    public static final By LOGIN_BUTTON = By.xpath("//button[contains(.,'Log in')]");
    public static final By AGREEMENT_TEXT = By.xpath("//*[contains(text(),'By logging in I agree')]");
    public static final By PASSWORD_ERROR = By.xpath("//*[contains(text(),'Password is a required field')]");
    public static final By USERNAME_ERROR = By.xpath("//*[contains(text(),'Username is a required field')]");
    public static final By PASSWORD_VISIBILITY_TOGGLE = By.cssSelector("[aria-label='toggle password visibility']");
    public static final By TERMS_OF_SERVICE_LINK = By.partialLinkText("Terms of Service");
    public static final By PRIVACY_POLICY_LINK = By.partialLinkText("Privacy Policy");
    public static final By INVALID_CREDENTIALS_ERROR = By.xpath(
            "//*[contains(text(),'Invalid') or contains(text(),'incorrect') or contains(text(),'wrong')]"
    );

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(ConfigReader.getLoginUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME));
    }

    public void openFreshSession() {
        driver.manage().deleteAllCookies();
        driver.get(ConfigReader.getBaseUrl());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.clear();");
        js.executeScript("sessionStorage.clear();");

        open();
    }

    public void enterUsername(String username) {
        WebElement field = find(USERNAME);
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = find(PASSWORD);
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        click(LOGIN_BUTTON);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public void waitForDashboard() {
        wait.until(ExpectedConditions.urlToBe(ConfigReader.getDashboardUrl()));
    }

    public void waitForLoginPage() {
        wait.until(ExpectedConditions.urlContains("/login"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME));
    }

    public void togglePasswordVisibility() {
        click(PASSWORD_VISIBILITY_TOGGLE);
    }

    public void clickLinkWithJavaScript(By locator) {
        WebElement link = find(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", link);
    }

    public String getPasswordInputType() {
        return find(PASSWORD).getAttribute("type");
    }

    public String getFieldText(By locator) {
        return find(locator).getText();
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("/login")
                && isDisplayed(USERNAME)
                && isDisplayed(PASSWORD);
    }

    public boolean isInvalidCredentialsErrorVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(INVALID_CREDENTIALS_ERROR));
            return true;
        } catch (Exception e) {
            return isOnLoginPage();
        }
    }

    public boolean isAgreementVisible() {
        return isDisplayed(AGREEMENT_TEXT);
    }

    public boolean isLoginButtonVisible() {
        return isDisplayed(LOGIN_BUTTON);
    }

    public boolean isPasswordFieldVisible() {
        return isDisplayed(PASSWORD);
    }

    public boolean isUsernameFieldVisible() {
        return isDisplayed(USERNAME);
    }

    public boolean isPasswordVisibilityToggleVisible() {
        return isDisplayed(PASSWORD_VISIBILITY_TOGGLE);
    }
}

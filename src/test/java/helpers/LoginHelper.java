package helpers;

import org.openqa.selenium.WebDriver;
import pages.LoginPage;

public class LoginHelper {

    private final WebDriver driver;
    private final LoginPage loginPage;

    public LoginHelper(WebDriver driver) {
        this.driver = driver;
        this.loginPage = new LoginPage(driver);
    }

    public void loginWithDefaultCredentials() {
        login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    public void login(String username, String password) {
        loginPage.open();
        loginPage.login(username, password);
        loginPage.waitForDashboard();
    }
}

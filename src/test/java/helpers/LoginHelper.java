package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginHelper {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String username, String password) {
        driver.get("https://stage.app.mgkeld.com/#/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));

        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[contains(.,'Log in')]")).click();

        wait.until(ExpectedConditions.urlToBe("https://stage.app.mgkeld.com/#/dashboard"));
    }
}
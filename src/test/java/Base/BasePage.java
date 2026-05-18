package Base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement find(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator){

        wait.until(ExpectedConditions.elementToBeClickable(locator));

        driver.findElement(locator).click();

    }

    protected void type(By locator, String text){
        find(locator).sendKeys(text);
    }

    protected void clearAndType(By locator, String text){
        WebElement element = find(locator);

        element.sendKeys(Keys.CONTROL + "a");

        element.sendKeys(Keys.DELETE);

        element.sendKeys(text);
    }

}

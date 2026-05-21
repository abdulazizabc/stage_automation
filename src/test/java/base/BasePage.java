package base;

import helpers.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getWaitSeconds()));
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                return element.isDisplayed() && element.isEnabled();
            } catch (Exception e) {
                return false;
            }
        });
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        find(locator).sendKeys(text);
    }

    protected void clearAndType(By locator, String text) {
        WebElement element = find(locator);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void waitUntilInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void selectDropdownByDataValue(By combobox, String dataValue) {
        click(combobox);
        By listbox = By.xpath("//ul[@role='listbox']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(listbox));
        By option = By.xpath("//ul[@role='listbox']//li[@data-value='" + dataValue + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option));
        click(option);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(listbox));
    }

    protected void selectListOptionByText(String optionText) {
        By listbox = By.xpath("//ul[@role='listbox']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(listbox));
        By option = By.xpath("//ul[@role='listbox']//li[normalize-space()='" + optionText + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option));
        click(option);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(listbox));
    }
}

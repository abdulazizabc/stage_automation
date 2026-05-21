package pages.components;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SidebarComponent extends BasePage {

    public static final By DRIVERS = By.id("sidebar-drivers");
    public static final By TRAILERS = By.id("sidebar-trailers");

    public SidebarComponent(WebDriver driver) {
        super(driver);
    }

    public void openDrivers() {
        click(DRIVERS);
    }

    public void openTrailers() {
        wait.until(driver -> driver.findElement(TRAILERS).isEnabled());
        click(TRAILERS);
    }
}

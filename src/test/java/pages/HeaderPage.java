package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderPage extends BasePage {

    public static final By PROFILE_MENU = By.xpath("//header//button[contains(@class,'MuiIconButton-root')]");
    public static final By PROFILE_MENU_LEGACY = By.cssSelector("button.css-5yfvof");
    public static final By LOGOUT_MENU_ITEM = By.xpath("//li[contains(.,'Log out')]");
    public static final By LOGOUT_CONFIRM_BUTTON = By.xpath("//button[contains(.,'Log out')]");
    public static final By LOGIN_TITLE = By.xpath("//*[contains(text(),'Log in')]");

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    public void openProfileMenu() {
        if (isClickable(PROFILE_MENU)) {
            click(PROFILE_MENU);
            return;
        }
        click(PROFILE_MENU_LEGACY);
    }

    private boolean isClickable(By locator) {
        try {
            return driver.findElement(locator).isDisplayed()
                    && driver.findElement(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        click(LOGOUT_MENU_ITEM);
    }

    public void confirmLogout() {
        click(LOGOUT_CONFIRM_BUTTON);
    }

    public void logout() {
        openProfileMenu();
        clickLogout();
        confirmLogout();
    }

    public boolean isLoginTitleVisible() {
        return isDisplayed(LOGIN_TITLE);
    }
}

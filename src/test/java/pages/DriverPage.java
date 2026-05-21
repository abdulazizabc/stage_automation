package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.components.SidebarComponent;

public class DriverPage extends BasePage {

    public static final By ADD_DRIVER_BUTTON = By.xpath("//button[contains(.,'Add Driver')]");
    public static final By SUBMIT_DRIVER_BUTTON = By.xpath(
            "//div[@role='dialog']//button[@type='submit'] | //form//button[@type='submit']"
    );
    public static final By DRIVERS_TITLE = By.xpath("//*[contains(text(),'Drivers')]");
    public static final By SEARCH_INPUT = By.cssSelector("input[placeholder='Search by name']");
    public static final By DRIVERS_TABLE = By.cssSelector(".MuiDataGrid-root");

    public static final By FIRST_NAME = By.id("name");
    public static final By LAST_NAME = By.id("surname");
    public static final By LICENSE_NUMBER = By.id("dl_number");
    public static final By LICENSE_STATE_OPEN = By.xpath(
            "(//input[@id='dl_number']/following::button[@aria-label='Open'])[1]"
    );
    public static final By USERNAME = By.id("username");
    public static final By PASSWORD = By.id("password");

    private final SidebarComponent sidebar;

    public DriverPage(WebDriver driver) {
        super(driver);
        this.sidebar = new SidebarComponent(driver);
    }

    public void openDriversPage() {
        sidebar.openDrivers();
        waitForDriversList();
    }

    public void clickAddDriverButton() {
        click(ADD_DRIVER_BUTTON);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME));
    }

    public void clickCreateDriverButton() {
        click(SUBMIT_DRIVER_BUTTON);
        waitForDriverFormClosed();
    }

    public void waitForDriverFormClosed() {
        waitUntilInvisible(FIRST_NAME);
        waitForDriversList();
    }

    public void waitForDriversList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(DRIVERS_TABLE));
    }

    public void enterFirstName(String name) {
        type(FIRST_NAME, name);
    }

    public void enterLastName(String surname) {
        type(LAST_NAME, surname);
    }

    public void enterDriverLicenseNumber(String number) {
        type(LICENSE_NUMBER, number);
    }

    public void enterDriverLicenseState(String stateLabel) {
        click(LICENSE_STATE_OPEN);
        selectListOptionByText(stateLabel);
    }

    public void enterUsername(String username) {
        type(USERNAME, username);
    }

    public void enterPassword(String password) {
        type(PASSWORD, password);
    }

    public void searchDriver(String query) {
        clearAndType(SEARCH_INPUT, query);
        find(SEARCH_INPUT).sendKeys(Keys.ENTER);
        waitForDriverInGrid(query);
    }

    public void waitForDriverInGrid(String text) {
        By cell = By.xpath(
                "//div[contains(@class,'MuiDataGrid-root')]//*[contains(text(),'" + text + "')]"
        );
        wait.until(ExpectedConditions.visibilityOfElementLocated(cell));
    }

    public boolean isDriverVisibleInGrid(String text) {
        By rowCell = By.xpath(
                "//div[contains(@class,'MuiDataGrid-root')]//*[contains(text(),'" + text + "')]"
        );
        return isDisplayed(rowCell);
    }
}

package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.components.SidebarComponent;

public class DriverPage extends BasePage {

    public static final By ADD_DRIVER_BUTTON = By.xpath("//button[contains(.,'Add Driver')]");
    public static final By SUBMIT_DRIVER_BUTTON = By.xpath(
            "//div[@role='dialog']//button[@type='submit'] | //form//button[@type='submit']");
    public static final By EDIT_DRIVER_BUTTON = By.xpath("//button[contains(.,'Edit')]");
    public static final By SAVE_CHANGES_BUTTON = By.xpath("//button[contains(.,'Save changes')]");

    public static final By SEARCH_INPUT = By.cssSelector("input[placeholder='Search by name']");
    public static final By DRIVERS_TABLE = By.cssSelector(".MuiDataGrid-root");

    public static final By FIRST_NAME = By.id("name");
    public static final By LAST_NAME = By.id("surname");
    public static final By DATE_OF_BIRTH =
            By.xpath("//label[normalize-space()='Date of birth (Optional)']/following::input[1]");
    public static final By LICENSE_NUMBER = By.id("dl_number");
    public static final By DRIVER_ID = By.id("id_number");
    public static final By DRIVER_LICENSE_EXPIRY =
            By.xpath("//label[normalize-space()='Driver license expiry (Optional)']/following::input[1]");
    public static final By LICENSE_STATE_OPEN = By.xpath(
            "(//input[@id='dl_number']/following::button[@aria-label='Open'])[1]"
    );
    public static final By PHONE_NUMBER = By.id("phone");
    public static final By EMAIL = By.id("email");
    public static final By USERNAME = By.id("username");
    public static final By PASSWORD = By.id("password");
    public static final By TG_LINK = By.id("tg_link");
    public static final By COMMENT = By.cssSelector("div[contenteditable='true'][role='textbox']");
    public static final By NOTE_EXPIRY_DATE =
            By.xpath("//label[normalize-space()='Note expiry date (Optional)']/following::input[1]");



    private final SidebarComponent sidebar;

    public DriverPage(WebDriver driver) {
        super(driver);
        this.sidebar = new SidebarComponent(driver);
    }
    //Open
    public void openDriversPage() {
        sidebar.openDrivers();
        waitForDriversList();
    }

    //Click
    public void clickAddDriverButton() {
        click(ADD_DRIVER_BUTTON);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME));
    }

    public void clickCreateDriverButton() {
        click(SUBMIT_DRIVER_BUTTON);
        waitForDriverFormClosed();
    }

    public void clickEditButton() {
        click(EDIT_DRIVER_BUTTON);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME));
    }

    public void clickSaveChangesButton() {
        click(SAVE_CHANGES_BUTTON);
        waitForDriverFormClosed();
    }

    // Wait
    public void waitForDriverFormClosed() {
        waitUntilInvisible(FIRST_NAME);
        waitForDriversList();
    }

    public void waitForDriversList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(DRIVERS_TABLE));
    }

    //Enter
    public void enterFirstName(String name) {
        type(FIRST_NAME, name);
    }

    public void enterLastName(String surname) {
        type(LAST_NAME, surname);
    }

    public void enterDateOfBirth(String dateOfBirth) { type(DATE_OF_BIRTH, dateOfBirth); }

    public void enterDriverID(String driverID) { type(DRIVER_ID, driverID); }

    public void enterDriverLicenseNumber(String number) {
        type(LICENSE_NUMBER, number);
    }

    public void enterDriverLicenseExpiryDate(String driverLicenseExpiry) { type(DRIVER_LICENSE_EXPIRY, driverLicenseExpiry); }

    public void enterDriverLicenseState(String stateLabel) {
        click(LICENSE_STATE_OPEN);
        selectListOptionByText(stateLabel);
    }

    public void enterPhoneNumber(String phoneNumber) { type(PHONE_NUMBER, phoneNumber); }

    public void enterEmail (String email) { type(EMAIL, email); }

    public void enterUsername(String username) {
        type(USERNAME, username);
    }

    public void enterPassword(String password) {
        type(PASSWORD, password);
    }

    public void enterTGLink(String TGLink) { type(TG_LINK, TGLink); }

    public void enterComment(String comment) {
        WebElement editor = find(COMMENT);

        editor.click();
        editor.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        editor.sendKeys(Keys.DELETE);
        editor.sendKeys(comment);
    }

    public void enterNoteExpiryDate(String noteExpiryDate) { type(NOTE_EXPIRY_DATE, noteExpiryDate); }

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

    //Edit
    public void editFirstName(String firstName){clearAndType(FIRST_NAME,firstName);}
    public void editLastName(String lastName) {clearAndType(LAST_NAME, lastName);}
    public void editDateOfBirth(String dateOfBirth) {clearAndType(DATE_OF_BIRTH, dateOfBirth);}
    public void editDriverId(String driverID) {clearAndType(DRIVER_ID, driverID);}
    public void editDriverLicenseNumber(String licenceNumber) {clearAndType(LICENSE_NUMBER, licenceNumber);}
    public void editDriverLicenseExpiry(String licenseExpiryDate){clearAndType(DRIVER_LICENSE_EXPIRY, licenseExpiryDate);}
    public void editLicenseState(String stateLabel){
        click(LICENSE_STATE_OPEN);
        selectListOptionByText(stateLabel);
    }
    public void editPhoneNumber(String phoneNumber) {clearAndType(PHONE_NUMBER, phoneNumber);}
    public void editEmail(String email) { clearAndType(EMAIL, email); }
    public void editPassword(String password) { clearAndType(PASSWORD, password);}
    public void editTGLink(String TGLink) { clearAndType(TG_LINK, TGLink); }
    public void editNoteExpiryDate(String noteExpiryDate) { clearAndType(NOTE_EXPIRY_DATE, noteExpiryDate); }

    //Get
    public String getFirstNameValue() { return find(FIRST_NAME).getAttribute("value"); }
    public String getLastNameValue() { return find(LAST_NAME).getAttribute("value"); }
    public String getDateOfBirthValue() { return find(DATE_OF_BIRTH).getAttribute("value"); }
    public String getDriverIDValue() { return find(DRIVER_ID).getAttribute("value"); }
    public String getDriverLicenseNumberValue() { return find(LICENSE_NUMBER).getAttribute("value"); }
    public String getDriverLicenseExpiryValue() { return find(DRIVER_LICENSE_EXPIRY).getAttribute("value"); }
    public String getPhoneNumberValue() { return find(PHONE_NUMBER).getAttribute("value"); }
    public String getEmailValue() { return find(EMAIL).getAttribute("value"); }
    public String getUsernameValue() { return find(USERNAME).getAttribute("value"); }
    public String getPasswordValue() { return find(PASSWORD).getAttribute("value"); }
    public String getTGLinkValue() { return find(TG_LINK).getAttribute("value"); }
    public String getNoteExpiryDateValue() { return find(NOTE_EXPIRY_DATE).getAttribute("value"); }
}

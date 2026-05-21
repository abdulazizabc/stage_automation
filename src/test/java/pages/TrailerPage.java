package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.components.SidebarComponent;

public class TrailerPage extends BasePage {

    public static final By ADD_TRAILER_BUTTON = By.xpath("//button[contains(.,'Add Trailer')]");
    public static final By EDIT_TRAILER_BUTTON = By.xpath("//button[contains(.,'Edit')]");
    public static final By SUBMIT_BUTTON = By.xpath("//button[@type='submit']");
    public static final By SEARCH_INPUT = By.cssSelector("input[placeholder='Search by Unit#']");

    public static final By UNIT_NUMBER = By.id("unit_number");
    public static final By MAKE = By.id("make");
    public static final By VIN = By.id("vin");
    public static final By PLATE_NUMBER = By.id("plate_number");
    public static final By PRODUCTION_YEAR = By.id("production_year");
    public static final By TRAILER_TYPE = By.id("trailer_type");
    public static final By PLATE_STATE = By.id("plate_state");
    public static final By PLATE_EXPIRATION = By.cssSelector("input[placeholder='MM.DD.YYYY']");
    public static final By PLATE_EXPIRATION_BY_LABEL = By.xpath(
            "//label[contains(.,'Plate expiration date')]/following::input[@placeholder='MM.DD.YYYY'][1]"
    );
    public static final By PLATE_STATE_COMBOBOX = By.xpath("//div[@role='combobox' and @id='plate_state']");
    public static final By TRAILER_TYPE_COMBOBOX = By.xpath("//div[@role='combobox' and @id='trailer_type']");

    private final SidebarComponent sidebar;

    public TrailerPage(WebDriver driver) {
        super(driver);
        this.sidebar = new SidebarComponent(driver);
    }

    public void openTrailersPage() {
        sidebar.openTrailers();
    }

    public void clickAddTrailer() {
        click(ADD_TRAILER_BUTTON);
    }

    public void enterUnit(String unit) {
        type(UNIT_NUMBER, unit);
    }

    public void enterMake(String make) {
        type(MAKE, make);
    }

    public void enterVin(String vin) {
        type(VIN, vin);
    }

    public void enterPlate(String plate) {
        type(PLATE_NUMBER, plate);
    }

    public void enterProductionYear(String year) {
        type(PRODUCTION_YEAR, year);
    }

    public void selectTrailerType(String type) {
        selectDropdownByDataValue(TRAILER_TYPE, type);
    }

    public void selectPlateState(String state) {
        selectDropdownByDataValue(PLATE_STATE, state);
    }

    public void enterPlateExpirationDate(String date) {
        type(PLATE_EXPIRATION, date);
    }

    public void clickCreateTrailerButton() {
        click(SUBMIT_BUTTON);
        waitForTrailerFormClosed();
    }

    public void createDryVanTrailer(
            String unit,
            String vin,
            String make,
            String plate,
            String plateExpiration,
            String plateState,
            String productionYear
    ) {
        openTrailersPage();
        clickAddTrailer();
        enterUnit(unit);
        enterMake(make);
        enterVin(vin);
        enterPlate(plate);
        enterPlateExpirationDate(plateExpiration);
        selectPlateState(plateState);
        enterProductionYear(productionYear);
        selectTrailerType("DRY_VAN");
        clickCreateTrailerButton();
    }

    public boolean isTrailerUnitVisibleInGrid(String unit) {
        return isDisplayed(trailerUnitCell(unit));
    }

    private By trailerUnitCell(String unit) {
        return By.xpath(
                "//div[@data-field='unit_number' and contains(.,'" + unit + "')]"
        );
    }

    public void clickEditTrailer() {
        click(EDIT_TRAILER_BUTTON);
    }

    public void editUnit(String unit) {
        clearAndType(UNIT_NUMBER, unit);
    }

    public void editMake(String make) {
        clearAndType(MAKE, make);
    }

    public void editVin(String vin) {
        clearAndType(VIN, vin);
    }

    public void editProductionYear(String year) {
        clearAndType(PRODUCTION_YEAR, year);
    }

    public void clickSaveTrailerButton() {
        click(SUBMIT_BUTTON);
        waitForTrailerFormClosed();
    }

    public void waitForTrailerFormClosed() {
        waitUntilInvisible(UNIT_NUMBER);
        waitForTrailersList();
    }

    public void waitForTrailersList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiDataGrid-root")));
    }

    public void searchTrailer(String unit) {
        clearAndType(SEARCH_INPUT, unit);
        find(SEARCH_INPUT).sendKeys(Keys.ENTER);
        waitForTrailerUnitInGrid(unit);
    }

    public void waitForTrailerUnitInGrid(String unit) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(trailerUnitCell(unit)));
    }

    public boolean isTextVisibleInGrid(String text) {
        By cell = By.xpath("//div[contains(@class,'MuiDataGrid-root')]//*[contains(text(),'" + text + "')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(cell));
        return find(cell).isDisplayed();
    }

    public void waitForFieldValue(By locator, String value) {
        wait.until(driver -> value.equals(find(locator).getAttribute("value")));
    }

    public String getUnitValue() {
        return find(UNIT_NUMBER).getAttribute("value");
    }

    public String getMakeValue() {
        return find(MAKE).getAttribute("value");
    }

    public String getVinValue() {
        return find(VIN).getAttribute("value");
    }

    public String getPlateValue() {
        return find(PLATE_NUMBER).getAttribute("value");
    }

    public String getPlateExpirationValue() {
        return find(PLATE_EXPIRATION_BY_LABEL).getAttribute("value");
    }

    public String getPlateStateValue() {
        return find(PLATE_STATE_COMBOBOX).getText();
    }

    public String getProductionYearValue() {
        return find(PRODUCTION_YEAR).getAttribute("value");
    }

    public String getTrailerTypeValue() {
        return find(TRAILER_TYPE_COMBOBOX).getText();
    }

    public void waitForEditFormLoaded() {
        wait.until(driver -> {
            String value = driver.findElement(UNIT_NUMBER).getAttribute("value");
            return value != null && !value.isBlank();
        });
    }
}

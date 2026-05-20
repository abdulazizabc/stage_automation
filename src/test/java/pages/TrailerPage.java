package pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TrailerPage extends BasePage {

    public TrailerPage(WebDriver driver){
        super(driver);
    }

    //Create Trailer

    public void openTrailersPage() {

        wait.until(driver ->
                driver.findElement(
                        By.id("sidebar-trailers")
                ).isEnabled()
        );

        click(By.id("sidebar-trailers"));
    }

    public void clickAddTrailer(){

        click( By.xpath("//button[contains(.,'Add Trailer')]"));

    }

    public boolean isUnitNumberInputDisplayed(){

        return find(By.id("unit_number")).isDisplayed();

    }

    public void enterUnit(String unit){

        type(By.id("unit_number"),unit);

    }

    public void enterMake(String make){

        type(By.id("make"),make);

    }

    public void enterVin(String vin){

        type(By.id("vin"),vin);

    }

    public void enterPlate(String plate){

        type(By.id("plate_number"),plate);

    }

    public void enterProductionYear(String year){

        type(By.id("production_year"),year);
    }

    public void selectTrailerType(String type){

        click(By.id("trailer_type"));

        click(By.xpath("//li[@data-value='" + type + "']"));
    }

    public void selectPlateState(String state){

        click(By.id("plate_state"));

        click(By.xpath("//li[@data-value='" + state + "']"));
    }

    public void enterPlateExpirationDate(String date){

        type(By.cssSelector("input[placeholder='MM.DD.YYYY']"),date);

    }

    public void clickCreateTrailerButton(){

        click(By.xpath("//button[@type='submit']"));

    }

    public boolean isTrailerCreated(String unit){

        return find(By.xpath("//div[@data-field='unit_number' and text()='" + unit + "']")).isDisplayed();

    }

    //Edit Trailer
    public void clickEditTrailer() {
        click( By.xpath("//button[contains(.,'Edit')]"));
    }

    public void editUnit(String unit){

        clearAndType(By.id("unit_number"), unit);

    }

    public void editMake(String make){
        clearAndType(By.id("make"),make);
    }

    public void editVin(String vin){
        clearAndType(By.id("vin"),vin);
    }

    public void editProductionYear(String year){
        clearAndType(By.id("production_year"),year);
    }

    public void clickEditTrailerButton(){

        click(By.xpath("//button[@type='submit']"));

    }

    public boolean isTrailerEdited(String unit){

        return find(By.xpath("//div[@data-field='unit_number' and text()='" + unit + "']")).isDisplayed();
//                find(By.xpath("//div[@data-field='make' and text()='" + make + "']")).isDisplayed() &&
//                find(By.xpath("//div[@data-field='vin' and text()='" + vin + "']")).isDisplayed() &&
//                find(By.xpath("//div[@data-field='production_year' and text()='" + year + "']")).isDisplayed();

    }

    public void searchTrailer(String unit){

        type(
                By.cssSelector(
                        "input[placeholder='Search by Unit#']"
                ),
                unit
        );

        find(By.cssSelector("input[placeholder='Search by Unit#']")).sendKeys(Keys.ENTER);

    }

    public boolean isTextVisible(String text){

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'" + text + "')]")));

        return find(
                By.xpath("//*[contains(text(),'" + text + "')]")
        ).isDisplayed();
    }

    //LogOut

    public void openProfileMenu(){

        click(
                By.cssSelector("button.css-5yfvof")
        );
    }

    public void clickLogout(){

        click(
                By.xpath("//li[contains(.,'Log out')]")
        );
    }

    public void confirmLogout(){

        click(
                By.xpath("//button[contains(.,'Log out')]")
        );
    }

}

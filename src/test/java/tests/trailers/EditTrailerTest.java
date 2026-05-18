package tests.trailers;

import Base.BaseTest;
import helpers.LoginHelper;
import helpers.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.TrailerPage;

import static org.junit.jupiter.api.Assertions.*;

public class EditTrailerTest extends BaseTest {

    String unit = TestDataGenerator.generateUnitNumber();
    String vin = TestDataGenerator.generateVin();

    private LoginHelper loginHelper;
    private TrailerPage trailerPage;

    @BeforeEach
    void setUpPages(){

        loginHelper = new LoginHelper(driver);

        trailerPage = new TrailerPage(driver);

        loginHelper.login("account_admin_amazon", "String1!");
    }

    @Test
    void shouldOpenEditTrailerForm() throws InterruptedException {
        trailerPage.openTrailersPage();
        trailerPage.clickEditTrailer();

        assertTrue(trailerPage.isUnitNumberInputDisplayed());
    }

    @Test
    void EditTrailerSuccess() throws InterruptedException {
        trailerPage.openTrailersPage();
        trailerPage.clickEditTrailer();

        wait.until(driver ->
                !driver.findElement(
                        By.id("unit_number")
                ).getAttribute("value").isEmpty()
        );

        trailerPage.editUnit(unit);
        trailerPage.editMake("NewMake");
        trailerPage.editVin(vin);
        trailerPage.editProductionYear("2020");
        trailerPage.clickEditTrailerButton();

        System.out.println(unit);

        trailerPage.searchTrailer(unit);

        assertTrue(trailerPage.isTextVisible(unit));

        assertTrue(trailerPage.isTextVisible("NewMake"));

        assertTrue(trailerPage.isTextVisible(vin));

        assertTrue(trailerPage.isTextVisible("2020"));

    }


}

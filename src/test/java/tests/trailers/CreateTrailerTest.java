package tests.trailers;

import Base.BaseTest;
import helpers.LoginHelper;
import helpers.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.TrailerPage;

import static org.junit.jupiter.api.Assertions.*;

public class CreateTrailerTest extends BaseTest {

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
    void shouldOpenAddTrailerForm(){

        trailerPage.openTrailersPage();
        trailerPage.clickAddTrailer();

        assertTrue(trailerPage.isUnitNumberInputDisplayed());
    }

    @Test
    void AddTrailerSuccess(){

        trailerPage.openTrailersPage();
        trailerPage.clickAddTrailer();

        trailerPage.enterUnit(unit);
        trailerPage.enterMake("Wabash");
        trailerPage.enterVin(vin);
        trailerPage.enterPlate("TEST123");
        trailerPage.enterPlateExpirationDate("05.20.2027");
        trailerPage.selectPlateState("AK");
        trailerPage.enterProductionYear("2025");
        trailerPage.selectTrailerType("DRY_VAN");
        trailerPage.clickCreateTrailerButton();

        assertTrue(
                trailerPage.isTrailerCreated(unit)
        );
    }

}

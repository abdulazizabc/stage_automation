package tests.trailers;

import base.AuthenticatedTest;
import helpers.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.TrailerPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateTrailerTest extends AuthenticatedTest {

    private final String unit = TestDataGenerator.generateUnitNumber();
    private final String vin = TestDataGenerator.generateVin();

    private TrailerPage trailerPage;

    @BeforeEach
    void initPage() {
        trailerPage = new TrailerPage(driver);
    }

    @Test
    void createTrailerSuccess() {
        trailerPage.createDryVanTrailer(
                unit,
                vin,
                "Wabash",
                "TEST123",
                "05.20.2027",
                "AK",
                "2025"
        );

        assertTrue(
                trailerPage.isTrailerUnitVisibleInGrid(unit),
                "Created trailer unit should appear in the grid"
        );

        trailerPage.searchTrailer(unit);
        trailerPage.clickEditTrailer();
        trailerPage.waitForFieldValue(TrailerPage.UNIT_NUMBER, unit);

        assertEquals(unit, trailerPage.getUnitValue());
        assertEquals("Wabash", trailerPage.getMakeValue());
        assertEquals(vin, trailerPage.getVinValue());
        assertEquals("TEST123", trailerPage.getPlateValue());
        assertEquals("May 20, 2027", trailerPage.getPlateExpirationValue());
        assertEquals("AK - Alaska", trailerPage.getPlateStateValue());
        assertEquals("2025", trailerPage.getProductionYearValue());
        assertEquals("Dry van", trailerPage.getTrailerTypeValue());
    }
}

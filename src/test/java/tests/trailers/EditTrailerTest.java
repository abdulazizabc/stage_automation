package tests.trailers;

import base.AuthenticatedTest;
import helpers.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.TrailerPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditTrailerTest extends AuthenticatedTest {

    private TrailerPage trailerPage;

    @BeforeEach
    void initPage() {
        trailerPage = new TrailerPage(driver);
    }

    @Test
    void editTrailerSuccess() {
        String originalUnit = TestDataGenerator.generateUnitNumber();
        String updatedUnit = TestDataGenerator.generateUnitNumber();
        String originalVin = TestDataGenerator.generateVin();
        String updatedVin = TestDataGenerator.generateVin();

        trailerPage.createDryVanTrailer(
                originalUnit,
                originalVin,
                "Wabash",
                "TEST123",
                "05.20.2027",
                "AK",
                "2025"
        );

        trailerPage.waitForTrailerUnitInGrid(originalUnit);
        trailerPage.searchTrailer(originalUnit);
        trailerPage.clickEditTrailer();
        trailerPage.waitForEditFormLoaded();

        trailerPage.editUnit(updatedUnit);
        trailerPage.editMake("NewMake");
        trailerPage.editVin(updatedVin);
        trailerPage.editProductionYear("2020");
        trailerPage.clickSaveTrailerButton();
        trailerPage.searchTrailer(updatedUnit);

        assertTrue(trailerPage.isTrailerUnitVisibleInGrid(updatedUnit), "Updated unit should be visible in grid");
        assertTrue(trailerPage.isTextVisibleInGrid("NewMake"), "Updated make should be visible in grid");
        assertTrue(trailerPage.isTextVisibleInGrid(updatedVin), "Updated VIN should be visible in grid");
        assertTrue(trailerPage.isTextVisibleInGrid("2020"), "Updated production year should be visible in grid");
    }
}

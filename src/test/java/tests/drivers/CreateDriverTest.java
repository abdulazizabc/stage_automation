package tests.drivers;

import base.AuthenticatedTest;
import helpers.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.DriverPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateDriverTest extends AuthenticatedTest {

    private final String firstName = "AutoFirst" + System.currentTimeMillis();
    private final String lastName = "AutoLast" + System.currentTimeMillis();
    private static final String PASSWORD = "AutoPassTest1!";

    private final String username = TestDataGenerator.generateUsername();
    private final String licenseNumber = TestDataGenerator.generateLicenseNumber();

    private DriverPage driverPage;

    @BeforeEach
    void initPage() {
        driverPage = new DriverPage(driver);
    }

    @Test
    void createDriverSuccess() {
        driverPage.openDriversPage();
        driverPage.clickAddDriverButton();

        driverPage.enterFirstName(firstName);
        driverPage.enterLastName(lastName);
        driverPage.enterDriverLicenseNumber(licenseNumber);
        driverPage.enterDriverLicenseState("AK - ALASKA");
        driverPage.enterUsername(username);
        driverPage.enterPassword(PASSWORD);
        driverPage.clickCreateDriverButton();

        driverPage.searchDriver(firstName);

        assertTrue(
                driverPage.isDriverVisibleInGrid(firstName),
                "Created driver first name should appear in the grid"
        );
        assertTrue(
                driverPage.isDriverVisibleInGrid(lastName),
                "Created driver last name should appear in the grid"
        );
        assertTrue(
                driverPage.isDriverVisibleInGrid(username),
                "Created driver username should appear in the grid"
        );
    }
}

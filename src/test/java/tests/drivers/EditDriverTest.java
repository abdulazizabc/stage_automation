package tests.drivers;

import base.AuthenticatedTest;
import helpers.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.DriverPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.DriverPage.FIRST_NAME;

public class EditDriverTest extends AuthenticatedTest {

    private final String firstName = "AutoFirst" + System.currentTimeMillis();
    private final String lastName = "AutoLast" + System.currentTimeMillis();
    private static final String PASSWORD = "AutoPassTest1!";

    private final String email = TestDataGenerator.generateEmail();
    private final String licenseNumber = TestDataGenerator.generateLicenseNumber();

    private DriverPage driverPage;

    @BeforeEach
    void initPage() {
        driverPage = new DriverPage(driver);
    }

    @Test
    void EditDriverSuccess() throws InterruptedException {

        driverPage.openDriversPage();
        driverPage.clickEditButton();

        wait.until(driver ->
                !driver.findElement(
                        By.id("name")
                ).getAttribute("value").isEmpty()
        );

        driverPage.editFirstName(firstName);
        driverPage.editLastName(lastName);
        driverPage.editDateOfBirth("02022004");
        driverPage.editDriverId("21211");
        driverPage.editDriverLicenseNumber(licenseNumber);
        driverPage.editDriverLicenseExpiry("02022029");
        driverPage.editLicenseState("FL - FLORIDA");
        driverPage.editPhoneNumber("121212");
        driverPage.editEmail(email);
        driverPage.editPassword(PASSWORD);
        driverPage.editTGLink("https://t.me/NEWTestGroup");
        driverPage.editNoteExpiryDate("02022029");

        driverPage.clickSaveChangesButton();

        //validation

        driverPage.searchDriver(firstName);
        Thread.sleep(3000);

        assertTrue(
                driverPage.isDriverVisibleInGrid(firstName),
                "Created driver first name should appear in the grid"
        );
        assertTrue(
                driverPage.isDriverVisibleInGrid(lastName),
                "Created driver last name should appear in the grid"
        );

        driverPage.clickEditButton();
        wait.until(driver ->
                driver.findElement(FIRST_NAME)
                        .getAttribute("value")
                        .equals(firstName)
        );

        assertEquals(firstName, driverPage.getFirstNameValue());
        assertEquals(lastName, driverPage.getLastNameValue());
        assertEquals("Feb 2, 2004", driverPage.getDateOfBirthValue());
        assertEquals("21211", driverPage.getDriverIDValue());
        assertEquals(licenseNumber, driverPage.getDriverLicenseNumberValue());
        assertEquals("Feb 2, 2029", driverPage.getDriverLicenseExpiryValue());
        assertEquals("121212", driverPage.getPhoneNumberValue());
        assertEquals(email, driverPage.getEmailValue());
        assertEquals(PASSWORD, driverPage.getPasswordValue());
        assertEquals("https://t.me/NEWTestGroup", driverPage.getTGLinkValue());
        assertEquals("Feb 2, 2029", driverPage.getNoteExpiryDateValue());
    }
}

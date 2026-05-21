package base;

import helpers.LoginHelper;
import org.junit.jupiter.api.BeforeEach;

public abstract class AuthenticatedTest extends BaseTest {

    protected LoginHelper loginHelper;

    @BeforeEach
    void logIn() {
        loginHelper = new LoginHelper(driver);
        loginHelper.loginWithDefaultCredentials();
    }
}

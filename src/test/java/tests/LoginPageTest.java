package tests;

import helpers.LoginHelper;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Опционально
public class LoginPageTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    static void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window();
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void openLoginPage() {
        // Полная очистка сессии для независимости тестов
        driver.manage().deleteAllCookies();

        driver.get("https://stage.app.mgkeld.com");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.clear();");
        js.executeScript("sessionStorage.clear();");

        driver.get("https://stage.app.mgkeld.com/#/login");

        // Ждём реальной загрузки страницы логина
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
    }

    // ---------- Вспомогательные методы ----------
    private WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement usernameField() {
        return findElement(By.id("username"));
    }

    private WebElement passwordField() {
        return findElement(By.id("password"));
    }

    private WebElement loginButton() {
        return findElement(By.xpath("//button[contains(.,'Log in')]"));
    }

    // ---------- Тесты ----------
    @Test
    void loginPageElementsTest() {
        WebElement agreementText = findElement(By.xpath("//*[contains(text(),'By logging in I agree')]"));
        assertTrue(agreementText.isDisplayed(), "Текст соглашения должен быть виден");

        assertTrue(loginButton().isDisplayed(), "Кнопка Log In должна отображаться");
        assertTrue(passwordField().isDisplayed(), "Поле пароля должно отображаться");
        assertTrue(usernameField().isDisplayed(), "Поле логина должно отображаться");
    }

    @Test
    void emptyLoginValidationTest() {
        loginButton().click();

        WebElement passwordError = findElement(By.xpath("//*[contains(text(),'Password is a required field')]"));
        WebElement usernameError = findElement(By.xpath("//*[contains(text(),'Username is a required field')]"));

        assertEquals("Password is a required field", passwordError.getText(),
                "Текст ошибки пароля должен совпадать");
        assertEquals("Username is a required field", usernameError.getText(),
                "Текст ошибки логина должен совпадать");
    }

    @Test
    void passwordEyeIconTest() {
        passwordField().sendKeys("123456");
        WebElement eyeIcon = findElement(By.cssSelector("[aria-label='toggle password visibility']"));
        assertTrue(eyeIcon.isDisplayed(), "Иконка глаза должна отображаться");
    }

    @Test
    void passwordVisibilityChangeTest() {
        assertEquals("password", passwordField().getAttribute("type"),
                "Изначально поле должно быть типа password");

        passwordField().sendKeys("1212121");

        WebElement eyeIcon = findElement(By.cssSelector("[aria-label='toggle password visibility']"));
        eyeIcon.click();

        assertEquals("text", passwordField().getAttribute("type"),
                "После клика по глазу тип поля должен стать text");
    }

    @Test
    void termsOfServiceLinkTest() {
        // Находим ссылку "Terms of Service"
        WebElement termsLink = findElement(By.partialLinkText("Terms of Service"));

        // Сохраняем исходный URL, чтобы можно было вернуться
        String originalUrl = driver.getCurrentUrl();

        // Кликаем через JavaScript (надёжнее, если элемент перекрыт или скрыт)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", termsLink);

        // Ждём, пока URL будет содержать "terms-of-service" (это надёжнее, чем точное совпадение)
        wait.until(ExpectedConditions.urlContains("terms-of-service"));

        // Проверяем, что попали на правильную страницу
        assertEquals("https://www.mgkeld.com/terms-of-service", driver.getCurrentUrl(),
                "URL должен вести на страницу Terms of Service");

        // Возвращаемся обратно на страницу логина
        driver.navigate().back();

        // Ждём возврата (наличие поля username подтверждает, что мы на логине)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
    }

    @Test
    void privacyPolicyLinkTest() {
        // Находим ссылку "Privacy Policy"
        WebElement privacyLink = findElement(By.partialLinkText("Privacy Policy"));

        // Сохраняем исходный URL
        String originalUrl = driver.getCurrentUrl();

        // Кликаем через JavaScript (надёжнее)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", privacyLink);

        // Ждём, пока URL не будет содержать "privacy-policy"
        wait.until(ExpectedConditions.urlContains("privacy-policy"));

        // Проверяем точный URL
        assertEquals("https://www.mgkeld.com/privacy-policy", driver.getCurrentUrl(),
                "URL должен вести на страницу Privacy Policy");

        // Возвращаемся обратно на страницу логина
        driver.navigate().back();

        // Ждём, пока вернёмся на логин (поле username видно)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
    }

    @Test
    void successfulLogIn() {
        LoginHelper loginHelper = new LoginHelper(driver);
        loginHelper.login("account_admin_amazon", "String1!");
    }
}
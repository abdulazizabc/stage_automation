package Base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void setUp(){

        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window();

    }

    @AfterEach
    void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

}

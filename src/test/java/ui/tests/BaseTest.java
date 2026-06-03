package ui.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/// Базовый класс для тестов
public class BaseTest {

    protected WebDriver driver;

    /// Создаем браузер перед каждым тестом
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    /// Закрываем браузер после каждого тестом
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
package ui.pages;

import org.openqa.selenium.WebDriver;
import ui.utils.ConfigReader;

/// Базовый класс для страниц
public abstract class BasePage {

    protected WebDriver driver;

    private final String baseUrl =
            ConfigReader.getProperty("base.url");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    ///  Путь страницы
    protected abstract String getPagePath();

    /// Загрузка страницы
    public void open() {
        driver.get(baseUrl + getPagePath());
    }
}
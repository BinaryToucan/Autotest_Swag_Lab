package pages;

import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public abstract class BasePage {

    protected WebDriver driver;

    private final String baseUrl =
            ConfigReader.getProperty("base.url");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected abstract String getPagePath();

    public void open() {
        driver.get(baseUrl + getPagePath());
    }
}
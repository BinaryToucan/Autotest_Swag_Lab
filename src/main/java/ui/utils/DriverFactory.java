package ui.utils;

import utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    public static WebDriver createDriver() throws MalformedURLException {

        String runMode = ConfigReader.getEnvProperty("RUN_MODE");

        if (runMode == null) {
            runMode = "local";
        }

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        System.out.println("RUN_MODE = " + runMode);

        if (runMode.equalsIgnoreCase("remote")) {

            String seleniumUrl = System.getenv("SELENIUM_URL");

            if (seleniumUrl == null) {
                throw new RuntimeException("SELENIUM_URL is not set for remote run");
            }

            return new RemoteWebDriver(
                    new URL(seleniumUrl),
                    options
            );
        }

        // LOCAL RUN
        return new ChromeDriver(options);
    }
}

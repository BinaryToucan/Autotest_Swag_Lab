package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.utils.Endpoints;
import ui.enums.TestUser;

/// Страница логина
public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.className("error-message-container");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPagePath() {
        return Endpoints.LOGIN_PAGE;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    ///  Нажатие на кнопку ввода
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    /// Ввод данных для входа
    public void login(TestUser user) {
        enterUsername(user.getUsername());
        enterPassword(user.getPassword());
        clickLogin();
    }

    /// Получение ошибки в ходе авторизации
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    /// Добавился ли класс ошибки
    public boolean isFieldInvalid(By element) {
        return driver.findElement(element)
                .getAttribute("class")
                .contains("error");
    }

    public boolean isUsernameFieldInvalid() {
        return isFieldInvalid(usernameInput);
    }

    public boolean isPasswordFieldInvalid() {
        return isFieldInvalid(passwordInput);
    }
}
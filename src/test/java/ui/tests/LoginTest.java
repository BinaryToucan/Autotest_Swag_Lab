package ui.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ui.enums.ErrorMessage;
import ui.pages.LoginPage;
import ui.utils.Endpoints;
import ui.enums.TestUser;

public class LoginTest extends BaseTest {

    /// Успешная авторизация существующего юзера
    @Test
    public void successfulStandardLoginTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.login(TestUser.STANDARD_USER);

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(
                currentUrl.contains(Endpoints.INVENTORY_PAGE)
        );
    }

    ///  Попытка входа с неверным паролем
    @Test
    public void invalidPasswordTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.login(TestUser.WRONG_PASS_USER);

        String errorText = loginPage.getErrorMessage();
        Assertions.assertTrue(
                errorText.contains(ErrorMessage.INVALID_PASSWORD.getText())
        );
        Assertions.assertTrue(loginPage.isUsernameFieldInvalid());
        Assertions.assertTrue(loginPage.isPasswordFieldInvalid());
    }

    /// Попытка входа заблокированного юзера
    @Test
    public void lockedUserTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.login(TestUser.LOCKED_USER);

        String errorText = loginPage.getErrorMessage();
        Assertions.assertTrue(
                errorText.contains(ErrorMessage.LOCKED_USER.getText())
        );
        Assertions.assertTrue(loginPage.isUsernameFieldInvalid());
        Assertions.assertTrue(loginPage.isPasswordFieldInvalid());
    }

    /// Попытка входа без ввода данных
    @Test
    public void emptyUserTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.clickLogin();

        String errorText = loginPage.getErrorMessage();
        Assertions.assertTrue(
                errorText.contains(ErrorMessage.REQUIRED_USER.getText())
        );
        Assertions.assertTrue(loginPage.isUsernameFieldInvalid());
        Assertions.assertTrue(loginPage.isPasswordFieldInvalid());
    }

    /// Попытка входа с логином, но без пароля
    @Test
    public void emptyPassTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.enterUsername(TestUser.STANDARD_USER.getUsername());

        loginPage.clickLogin();

        String errorText = loginPage.getErrorMessage();
        Assertions.assertTrue(
                errorText.contains(ErrorMessage.REQUIRED_PASSWORD.getText())
        );
        Assertions.assertTrue(loginPage.isUsernameFieldInvalid());
        Assertions.assertTrue(loginPage.isPasswordFieldInvalid());
    }

    /// Попытка входа с паролем, но без логина
    @Test
    public void emptyUsernameTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.enterPassword(TestUser.STANDARD_USER.getPassword());

        loginPage.clickLogin();

        String errorText = loginPage.getErrorMessage();
        Assertions.assertTrue(
                errorText.contains(ErrorMessage.REQUIRED_USER.getText())
        );
        Assertions.assertTrue(loginPage.isUsernameFieldInvalid());
        Assertions.assertTrue(loginPage.isPasswordFieldInvalid());
    }
}
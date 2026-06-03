package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import utils.Endpoints;
import utils.TestUser;

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
                errorText.contains(
                        "Epic sadface: Username and password do not match any user in this service"
                )
        );
    }

    /// Попытка входа заблокированного юзера
    @Test
    public void lockedUserTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.login(TestUser.LOCKED_USER);

        String errorText = loginPage.getErrorMessage();
        Assertions.assertTrue(
                errorText.contains(
                        "Epic sadface: Sorry, this user has been locked out."
                )
        );
    }

}
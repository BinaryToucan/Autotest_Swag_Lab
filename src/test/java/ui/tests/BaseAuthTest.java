package ui.tests;

import org.junit.jupiter.api.BeforeEach;
import ui.enums.TestUser;
import ui.pages.LoginPage;

public class BaseAuthTest extends BaseTest {
    @BeforeEach
    public void login() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();
        loginPage.login(TestUser.STANDARD_USER);
    }
}

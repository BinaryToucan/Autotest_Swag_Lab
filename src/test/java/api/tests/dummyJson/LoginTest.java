package api.tests.dummyJson;

import annotations.ApiTest;
import annotations.SmokeTest;
import api.clients.dummyJson.LoginClient;
import api.models.dummyJson.LoginRequest;
import api.models.dummyJson.LoginResponse;
import api.tests.BaseTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Epic("Petstore Login API")
@Feature("Logins")
@ApiTest
public class LoginTest extends BaseTest {

    private final LoginClient loginClient = new LoginClient();

    private final LoginRequest successLoginRequest = new LoginRequest(
                "emilys",
            "emilyspass"
    );

    @SmokeTest
    @Test
    @Story("Авторизация существующего юзера")
    @Severity(SeverityLevel.CRITICAL)
    void loginShouldReturnAccessToken() {

        LoginResponse response =
                loginClient.login(successLoginRequest)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(LoginResponse.class);

        assertNotNull(response.getAccessToken());
        assertFalse(response.getAccessToken().isEmpty());
    }
}

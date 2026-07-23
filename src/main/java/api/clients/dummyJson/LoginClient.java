package api.clients.dummyJson;

import api.clients.BaseApiClient;
import api.config.SpecificationConfig;
import api.models.dummyJson.LoginRequest;
import api.utils.DummyEndpoints;
import io.restassured.response.Response;

public class LoginClient extends BaseApiClient {

    public LoginClient() {
        super(SpecificationConfig.dummySpec());
    }

    /// Авторизация юзера
    public Response login(LoginRequest request) {
        return request()
                .body(request)
                .when()
                .post(DummyEndpoints.LOGIN);
    }
}

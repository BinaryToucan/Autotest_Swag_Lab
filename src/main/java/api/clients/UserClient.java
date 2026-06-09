package api.clients;

import api.utils.Endpoints;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserClient extends BaseApiClient{

    public Response getUsers() {
        return request()
                .when()
                .log().all()
                .get(Endpoints.USERS);
    }

    public Response getUser(int id) {
        return request()
                .when()
                .log().all()
                .get(Endpoints.USERS + id);
    }
}

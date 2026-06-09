package api.clients;

import api.utils.Endpoints;
import com.github.dockerjava.api.model.Endpoint;
import io.restassured.response.Response;

import static api.config.SpecificationConfig.requestSpec;
import static io.restassured.RestAssured.given;

public class TodoClient extends BaseApiClient{

    public Response getTodo(int id) {

        return request()
                .when()
                .get(Endpoints.TODOS + id);
    }

    public Response getAllTodos() {

        return given()
                .spec(requestSpec())
                .when()
                .get(Endpoints.TODOS);
    }
}

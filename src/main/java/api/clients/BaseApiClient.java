package api.clients;

import api.config.SpecificationConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseApiClient {
    protected RequestSpecification spec;

    public BaseApiClient() {
        this.spec = SpecificationConfig.requestSpec();
    }

    protected RequestSpecification request() {
        return given().spec(spec);
    }
}

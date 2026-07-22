package api.config;

import io.restassured.http.ContentType;
import utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class SpecificationConfig {
    public static RequestSpecification requestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getConfigProperty("base.api.url"))
                .setContentType(ContentType.JSON)
                .build();
    }

    ///Этот метод нужен для тестирования другого api (petstore)
    public static RequestSpecification petStoreSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getConfigProperty("base.api.petstore.url"))
                .setContentType(ContentType.JSON)
                .build();
    }
}

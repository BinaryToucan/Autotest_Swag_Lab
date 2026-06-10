package api.config;

import utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class SpecificationConfig {
    public static RequestSpecification requestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getConfigProperty("base.api.url"))
                .setContentType("application/json")
                .build();
    }
}

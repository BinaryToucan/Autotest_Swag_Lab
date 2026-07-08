package api.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    void setUp() {
        RestAssured.filters(new AllureRestAssured());
    }
}

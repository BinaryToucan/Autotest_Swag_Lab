package api.clients;

import api.config.SpecificationConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

///  Базовый API-клиент.
///  Содержит общую конфигурацию и методы для выполнения HTTP-запросов.
public class BaseApiClient {

    /// Базовая спецификация запроса
    protected RequestSpecification spec;

    /// Инициализирует базовую спецификацию запросов
    public BaseApiClient() {
        this.spec = SpecificationConfig.requestSpec();
    }

    /// Создаёт request specification для выполнения HTTP-запроса.
    /// @return настроенный RequestSpecification
    protected RequestSpecification request() {
        return given().spec(spec);
    }
}

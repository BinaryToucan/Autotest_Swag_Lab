package api.clients;

import api.config.SpecificationConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

///  Базовый API-клиент.
///  Содержит общую конфигурацию и методы для выполнения HTTP-запросов.
public class BaseApiClient {

    /// Базовая спецификация запроса
    protected RequestSpecification spec;

    /// Инициализация
    public BaseApiClient() {
        this.spec = SpecificationConfig.requestSpec();
    }

    /// Инициализирует с заданной спецификацией запросов
    public BaseApiClient(RequestSpecification spec) {
        this.spec = spec;
    }

    /// Создаёт request specification для выполнения HTTP-запроса.
    /// @return настроенный RequestSpecification
    protected RequestSpecification request() {
        return given().spec(spec);
    }
}

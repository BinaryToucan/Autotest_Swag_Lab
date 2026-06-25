package api.clients;

import api.utils.Endpoints;
import io.restassured.response.Response;

import static api.config.SpecificationConfig.requestSpec;
import static io.restassured.RestAssured.given;

/// Клиент для работы с Todo API
public class TodoClient extends BaseApiClient{

    /// Получает одну задачу по её идентификатору.
    /// @param id идентификатор задачи
    /// @return HTTP response с данными задачи
    public Response getTodo(int id) {
        return request()
                .when()
                .get(Endpoints.TODOS + "/" + id);
    }

    /// Получает список всех задач.
    /// @return HTTP response со списком задач
    public Response getAllTodos() {
        return request()
                .get(Endpoints.TODOS);
    }
}

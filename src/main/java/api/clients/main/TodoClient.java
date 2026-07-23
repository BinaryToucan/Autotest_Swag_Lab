package api.clients.main;

import api.clients.BaseApiClient;
import api.models.Todo;
import api.utils.MainEndpoints;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

/// Клиент для работы с Todo API
public class TodoClient extends BaseApiClient {

    /// Получает одну задачу по её идентификатору.
    /// @param id идентификатор задачи
    /// @return HTTP response с данными задачи
    public Response getTodo(int id) {
        return request()
                .when()
                .get(MainEndpoints.TODOS + "/" + id);
    }

    /// Получает список всех задач.
    /// @return HTTP response со списком задач
    public Response getAllTodos() {
        return request()
                .get(MainEndpoints.TODOS);
    }

    /// Получает список всех задач как список.
    /// @return список задач
    public List<Todo> getAllTodosAsList() {
        return request()
                .get(MainEndpoints.TODOS)
                .then()
                .extract()
                .jsonPath()
                .getList("", Todo.class);
    }
}

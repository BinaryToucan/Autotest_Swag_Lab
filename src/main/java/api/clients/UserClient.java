package api.clients;

import api.utils.Endpoints;
import io.restassured.response.Response;

/// Клиент для работы с User API
public class UserClient extends BaseApiClient{

    /// Получает список всех пользователей.
    /// @return HTTP response со списком пользователей
    public Response getUsers() {
        return request()
                .when()
                .log().all()
                .get(Endpoints.USERS);
    }

    /// Получает пользователя по его идентификатору.
    /// @param id идентификатор пользователя
    /// @return HTTP response с данными пользователя
    public Response getUser(int id) {
        return request()
                .when()
                .log().all()
                .get(Endpoints.USERS + id);
    }
}

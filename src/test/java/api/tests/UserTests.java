package api.tests;

import api.clients.UserClient;
import api.models.user.User;
import org.junit.jupiter.api.Test;
import annotations.ApiTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTest
public class UserTests {

    UserClient userClient = new UserClient();

    @Test
    void shouldGetToDoList() {
        userClient
                .getUsers()
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void shouldGetUserById() {

        var user = userClient
                .getUser(1)
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);

        assertEquals(1, user.getId());
        assertEquals("Leanne Graham", user.getName());
        assertNotNull(user.getEmail());

    }
}

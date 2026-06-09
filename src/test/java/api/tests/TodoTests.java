package api.tests;

import api.clients.TodoClient;
import api.models.Todo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TodoTests {
    TodoClient todoClient = new TodoClient();

    @Test
    void checkSingleTodo() {

        Todo todo =
                todoClient
                        .getTodo(1)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Todo.class);

        assertEquals(1, todo.getId());
        assertFalse(todo.isCompleted());
    }

    @Test
    void checkTodosCount() {

        List<Todo> todos =
                todoClient
                        .getAllTodos()
                        .then()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getList("", Todo.class);

        assertEquals(200, todos.size());
    }

    @Test
    void checkTitleContains() {

        todoClient
                .getTodo(1)
                .then()
                .statusCode(200)
                .body("title", containsString("delectus"));
    }

    @Test
    void checkCompletedFieldExists() {

        todoClient
                .getTodo(1)
                .then()
                .statusCode(200)
                .body("$", hasKey("completed"));
    }
}

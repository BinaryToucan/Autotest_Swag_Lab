package api.tests;

import annotations.SmokeTest;
import api.clients.TodoClient;
import api.models.Todo;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import annotations.ApiTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ApiTest
public class TodoTests {
    private static final int EXPECTED_TODOS_COUNT = 200;
    private static final int EXISTING_TODO_ID = 1;

    private final TodoClient todoClient = new TodoClient();

    @SmokeTest
    @Test
    @DisplayName("Should return 200 for existing todo")
    void checkValidSingleTodo() {
        var todo =
                todoClient
                        .getTodo(EXISTING_TODO_ID)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Todo.class);

        assertAll(
                () -> assertEquals(EXISTING_TODO_ID, todo.getId()),
                () -> assertFalse(todo.isCompleted())
        );
    }

    @SmokeTest
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 201})
    @DisplayName("Should return 404 for invalid todo")
    void checkTodoWithInvalidId(int id) {
        todoClient
                .getTodo(id)
                .then()
                .statusCode(404);
    }

    @SmokeTest
    @Test
    @DisplayName("Should return all todos")
    void checkTodosCount() {

        List<Todo> todos =
                todoClient
                        .getAllTodos()
                        .then()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getList("", Todo.class);

        assertEquals(EXPECTED_TODOS_COUNT, todos.size());
    }

    @SmokeTest
    @Test
    @DisplayName("Should map todo correctly")
    void shouldMapTodoCorrectly() {
        Todo todo = todoClient
                .getTodo(1)
                .then()
                .statusCode(200)
                .extract()
                .as(Todo.class);

        assertAll(
                () -> assertEquals(1, todo.getUserId()),
                () -> assertEquals(1, todo.getId()),
                () -> assertEquals("delectus aut autem", todo.getTitle()),
                () -> assertFalse(todo.isCompleted())
        );
    }

    @SmokeTest
    @Test
    @DisplayName("Should return identical todo for repeated requests")
    void shouldBeEqual() {
        Todo first = todoClient.getTodo(EXISTING_TODO_ID)
                .as(Todo.class);

        Todo second = todoClient.getTodo(EXISTING_TODO_ID)
                .as(Todo.class);

        assertEquals(first, second);
    }

    @Test
    @DisplayName("Todo should contain all required fields")
    void shouldContainAllRequiredFields() {

        List<Todo> todos = todoClient.getAllTodosAsList();

        assertThat(todos)
            .allSatisfy(todo -> {
                assertThat(todo.getUserId()).isPositive();
                assertThat(todo.getId()).isPositive();
                assertThat(todo.getTitle()).isNotBlank();
        });
    }

    @SmokeTest
    @Test
    @DisplayName("Todo should contain all required fields")
    void firstTodoShouldContainAllRequiredFields() {

        val todo = todoClient.getAllTodosAsList().getFirst();

        assertAll(
                () -> assertTrue(todo.getUserId() > 0),
                () -> assertTrue(todo.getId() > 0),
                () -> assertNotNull(todo.getTitle()),
                () -> assertFalse(todo.getTitle().isBlank())
        );
    }
}

package api.tests;

import api.clients.TodoClient;
import api.models.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import annotations.ApiTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@ApiTest
public class TodoTests {
    private static final int EXPECTED_TODOS_COUNT = 200;
    private static final int EXISTING_TODO_ID = 1;

    private final TodoClient todoClient = new TodoClient();

    @DisplayName("Should return 200 for existing todo")
    @Test
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

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 201})
    @DisplayName("Should return 404 for invalid todo")
    void checkTodoWithInvalidId(int id) {
        todoClient
                .getTodo(id)
                .then()
                .statusCode(404);
    }

    @DisplayName("Should return all todos")
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

        assertEquals(EXPECTED_TODOS_COUNT, todos.size());
    }

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

    @Test
    @DisplayName("Should return identical todo for repeated requests")
    void shouldBeEqual() {
        Todo first = todoClient.getTodo(EXISTING_TODO_ID)
                .as(Todo.class);

        Todo second = todoClient.getTodo(EXISTING_TODO_ID)
                .as(Todo.class);

        assertEquals(first, second);
    }

    @ParameterizedTest
    @MethodSource("todos")
    @DisplayName("Todo should contain all required fields")
    void shouldContainAllRequiredFields(Todo todo) {
        assertAll(
                () -> assertTrue(todo.getUserId() > 0),
                () -> assertTrue(todo.getId() > 0),
                () -> assertNotNull(todo.getTitle()),
                () -> assertFalse(todo.getTitle().isBlank())
        );
    }

    static Stream<Todo> todos() {
        return new TodoClient()
                .getAllTodos()
                .then()
                .extract()
                .jsonPath()
                .getList("", Todo.class)
                .stream();
    }
}

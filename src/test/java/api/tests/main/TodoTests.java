package api.tests.main;

import annotations.SmokeTest;
import api.clients.main.TodoClient;
import api.models.Todo;
import api.tests.BaseTest;
import io.qameta.allure.*;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import annotations.ApiTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Epic("ToDo API")
@Feature("ToDos")
@ApiTest
public class TodoTests extends BaseTest {
    private static final int EXPECTED_TODOS_COUNT = 200;
    private static final int EXISTING_TODO_ID = 1;

    private final TodoClient todoClient = new TodoClient();

    @SmokeTest
    @Story("Получение задачи по ID")
    @Severity(SeverityLevel.BLOCKER)
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
    @Story("Получение задачи по ID")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Should return 404 for invalid todo")
    void checkTodoWithInvalidId(int id) {
        todoClient
                .getTodo(id)
                .then()
                .statusCode(404);
    }

    @SmokeTest
    @Test
    @Story("Получение всех задач")
    @Severity(SeverityLevel.CRITICAL)
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
    @Story("Получение задач по ID")
    @Severity(SeverityLevel.NORMAL)
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
    @Story("Получение задач по ID")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Should return identical todo for repeated requests")
    void shouldBeEqual() {
        Todo first = todoClient.getTodo(EXISTING_TODO_ID)
                .as(Todo.class);

        Todo second = todoClient.getTodo(EXISTING_TODO_ID)
                .as(Todo.class);

        assertEquals(first, second);
    }

    @Test
    @Story("Получение всех задач")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Todo should contain all required fields in all todos")
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
    @Story("Получение задачи по ID")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Todo should contain all required fields in first todo")
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

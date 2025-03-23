package fr.kevin.llps.todo.controller.error;

import fr.kevin.llps.todo.exception.TodoNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomExceptionHandlerTest {

    private final CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    @Test
    void handleTodoNotFoundException() {
        TodoNotFoundException exception = new TodoNotFoundException("Todo was not found");

        ErrorDto errorDto = customExceptionHandler.handleTodoNotFoundException(exception);

        assertThat(errorDto).isNotNull();
        assertThat(errorDto.message()).isEqualTo("Todo was not found");
    }
}

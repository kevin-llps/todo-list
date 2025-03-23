package fr.kevin.llps.todo.controller.error;

import fr.kevin.llps.todo.exception.TodoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomExceptionHandlerTest {

    private final CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    @Test
    void handleMethodArgumentNotValidException() {
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getFieldError()).thenReturn(fieldError);

        ErrorDto errorDto = customExceptionHandler.handleMethodArgumentNotValidException(exception);

        assertThat(errorDto).isNotNull();
        assertThat(errorDto.message()).isEqualTo("defaultMessage");
    }

    @Test
    void handleMethodArgumentNotValidExceptionWithoutFieldError() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getFieldError()).thenReturn(null);

        ErrorDto errorDto = customExceptionHandler.handleMethodArgumentNotValidException(exception);

        assertThat(errorDto).isNotNull();
        assertThat(errorDto.message()).isEqualTo("At least one of the request field is not valid");
    }

    @Test
    void handleTodoNotFoundException() {
        TodoNotFoundException exception = new TodoNotFoundException("Todo was not found");

        ErrorDto errorDto = customExceptionHandler.handleTodoNotFoundException(exception);

        assertThat(errorDto).isNotNull();
        assertThat(errorDto.message()).isEqualTo("Todo was not found");
    }
}

package fr.kevin.llps.todo.controller.error;

import fr.kevin.llps.todo.exception.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TodoNotFoundException.class)
    public ErrorDto handleTodoNotFoundException(TodoNotFoundException todoNotFoundException) {
        return new ErrorDto(todoNotFoundException.getMessage());
    }
}

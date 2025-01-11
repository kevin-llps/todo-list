package fr.kevin.llps.todo.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fr.kevin.llps.todo.exception.TodoNotFoundException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        FieldError fieldError = methodArgumentNotValidException.getFieldError();

        if (fieldError != null) {
            return new ErrorDto(fieldError.getDefaultMessage());
        }
        return new ErrorDto("At least one of the request field is not valid");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TodoNotFoundException.class)
    public ErrorDto handleTodoNotFoundException(TodoNotFoundException todoNotFoundException) {
        return new ErrorDto(todoNotFoundException.getMessage());
    }
}

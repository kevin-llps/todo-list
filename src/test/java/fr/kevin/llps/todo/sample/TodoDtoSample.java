package fr.kevin.llps.todo.sample;

import fr.kevin.llps.todo.dto.TodoDto;
import fr.kevin.llps.todo.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoDtoSample {

    public static TodoDto oneTodoDto() {
        return createTodoDto();
    }

    public static TodoDto oneTodoDto(Integer id) {
        TodoDto todoDto = createTodoDto();
        todoDto.setId(id);

        return todoDto;
    }

    public static TodoDto oneTodoDto(Integer id, String title, Boolean completed, Integer numOrder, LocalDateTime expiryDate) {
        TodoDto todoDto = createTodoDto();
        todoDto.setId(id);
        todoDto.setTitle(title);
        todoDto.setCompleted(completed);
        todoDto.setNumOrder(numOrder);
        todoDto.setExpiryDate(expiryDate);

        return todoDto;
    }

    public static TodoDto oneTodoDto(String title, Boolean completed, Integer numOrder, LocalDateTime expiryDate) {
        TodoDto todoDto = createTodoDto();
        todoDto.setTitle(title);
        todoDto.setCompleted(completed);
        todoDto.setNumOrder(numOrder);
        todoDto.setExpiryDate(expiryDate);

        return todoDto;
    }

    public static List<TodoDto> todoDtoList() {
        return List.of(
                new TodoDto(1, "Test Todo 1", false, 1, LocalDateTime.parse("2025-03-22T21:49:05.815")),
                new TodoDto(2, "Test Todo 2", true, 1, LocalDateTime.parse("2025-03-23T10:30:05.123")));
    }

    public static TodoDto createTodoDto() {
        TodoDto todoDto = new TodoDto();
        todoDto.setTitle("Test Todo");
        todoDto.setCompleted(false);
        todoDto.setNumOrder(1);
        todoDto.setExpiryDate(LocalDateTime.parse("2025-03-22T21:49:05.815"));

        return todoDto;
    }

}

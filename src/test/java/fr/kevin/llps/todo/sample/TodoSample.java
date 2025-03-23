package fr.kevin.llps.todo.sample;

import fr.kevin.llps.todo.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoSample {

    public static Todo oneTodo() {
        return createTodo();
    }

    public static Todo oneTodo(Integer id) {
        Todo todo = createTodo();
        todo.setTodoId(id);

        return todo;
    }

    public static List<Todo> todoList() {
        return List.of(
                new Todo(1, "Test Todo 1", false, 1, LocalDateTime.parse("2025-03-22T21:49:05.815")),
                new Todo(2, "Test Todo 2", true, 1, LocalDateTime.parse("2025-03-23T10:30:05.123")));
    }

    private static Todo createTodo() {
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setCompleted(false);
        todo.setNumOrder(1);
        todo.setExpiryDate(LocalDateTime.parse("2025-03-22T21:49:05.815"));

        return todo;
    }

}

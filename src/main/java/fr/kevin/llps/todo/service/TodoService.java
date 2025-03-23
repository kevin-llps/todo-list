package fr.kevin.llps.todo.service;

import fr.kevin.llps.todo.dto.TodoDto;
import fr.kevin.llps.todo.entity.Todo;
import fr.kevin.llps.todo.exception.TodoNotFoundException;
import fr.kevin.llps.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAllOrdered();

        return todos.stream()
                .map(TodoDto::new)
                .toList();
    }

    public TodoDto getTodoById(int id) {
        return todoRepository.findById(id)
                .map(TodoDto::new)
                .orElseThrow(() -> new TodoNotFoundException("Todo was not found"));
    }

}

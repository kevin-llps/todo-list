package fr.kevin.llps.todo.service;

import org.springframework.stereotype.Service;

import fr.kevin.llps.todo.dto.TodoDto;
import fr.kevin.llps.todo.entity.Todo;
import fr.kevin.llps.todo.exception.TodoNotFoundException;
import fr.kevin.llps.todo.repository.TodoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoDto createTodo(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setCompleted(todoDto.getCompleted());
        todo.setNumOrder(todoDto.getNumOrder());
        todo.setExpiryDate(todoDto.getExpiryDate());

        Todo savedTodo = todoRepository.save(todo);

        return new TodoDto(savedTodo);
    }

    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAllOrdered();
        return todos.stream().map(TodoDto::new).collect(Collectors.toList());
    }

    public TodoDto getTodoById(int id) {
        return todoRepository.findById(id).map(TodoDto::new).orElseThrow(() -> new TodoNotFoundException("Todo was not found"));
    }

    public void deleteTodoById(int id) {
        todoRepository.deleteById(id);
    }
}

package fr.kevin.llps.todo.service;

import fr.kevin.llps.todo.dto.TodoDto;
import fr.kevin.llps.todo.entity.Todo;
import fr.kevin.llps.todo.exception.TodoNotFoundException;
import fr.kevin.llps.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void testCreateTodo() {
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setCompleted(false);
        todo.setNumOrder(1);
        todo.setExpiryDate(LocalDateTime.now());

        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        TodoDto todoDto = new TodoDto();
        todoDto.setTitle("Test Todo");
        todoDto.setCompleted(false);
        todoDto.setNumOrder(1);
        todoDto.setExpiryDate(LocalDateTime.now());

        TodoDto createdTodo = todoService.createTodo(todoDto);

        assertThat(createdTodo).isNotNull();
        assertThat(createdTodo.getTitle()).isEqualTo("Test Todo");
        assertThat(createdTodo.getCompleted()).isFalse();
        assertThat(createdTodo.getNumOrder()).isEqualTo(1);
        assertThat(createdTodo.getExpiryDate()).isEqualTo(todo.getExpiryDate());
    }

    @Test
    void testGetAllTodos() {
        Todo todo1 = new Todo();
        todo1.setTitle("Test Todo 1");
        todo1.setCompleted(false);
        todo1.setNumOrder(1);
        todo1.setExpiryDate(LocalDateTime.now());

        Todo todo2 = new Todo();
        todo2.setTitle("Test Todo 2");
        todo2.setCompleted(true);
        todo2.setNumOrder(2);
        todo2.setExpiryDate(LocalDateTime.now().plusDays(1));

        when(todoRepository.findAllOrdered()).thenReturn(Arrays.asList(todo1, todo2));

        List<TodoDto> todos = todoService.getAllTodos();

        assertThat(todos).hasSize(2);

        TodoDto todoDto1 = todos.get(0);
        assertThat(todoDto1.getTitle()).isEqualTo("Test Todo 1");
        assertThat(todoDto1.getCompleted()).isFalse();
        assertThat(todoDto1.getNumOrder()).isEqualTo(1);
        assertThat(todoDto1.getExpiryDate()).isEqualTo(todo1.getExpiryDate());

        TodoDto todoDto2 = todos.get(1);
        assertThat(todoDto2.getTitle()).isEqualTo("Test Todo 2");
        assertThat(todoDto2.getCompleted()).isTrue();
        assertThat(todoDto2.getNumOrder()).isEqualTo(2);
        assertThat(todoDto2.getExpiryDate()).isEqualTo(todo2.getExpiryDate());
    }

    @Test
    void testGetTodoById() {
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setCompleted(false);
        todo.setNumOrder(1);
        todo.setExpiryDate(LocalDateTime.now());

        when(todoRepository.findById(anyInt())).thenReturn(Optional.of(todo));

        TodoDto todoDto = todoService.getTodoById(1);

        assertThat(todoDto).isNotNull();
        assertThat(todoDto.getTitle()).isEqualTo("Test Todo");
        assertThat(todoDto.getCompleted()).isFalse();
        assertThat(todoDto.getNumOrder()).isEqualTo(1);
        assertThat(todoDto.getExpiryDate()).isEqualTo(todo.getExpiryDate());
    }

    @Test
    void testGetTodoByIdNotFound() {
        when(todoRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> todoService.getTodoById(1))
                .isInstanceOf(TodoNotFoundException.class)
                .hasMessage("Todo was not found");
    }

    @Test
    void testDeleteTodoById() {
        todoService.deleteTodoById(1);
        verify(todoRepository, times(1)).deleteById(1);
    }
}

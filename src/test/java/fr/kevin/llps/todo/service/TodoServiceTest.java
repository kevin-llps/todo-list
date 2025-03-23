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

import java.util.List;
import java.util.Optional;

import static fr.kevin.llps.todo.sample.TodoDtoSample.oneTodoDto;
import static fr.kevin.llps.todo.sample.TodoDtoSample.todoDtoList;
import static fr.kevin.llps.todo.sample.TodoSample.oneTodo;
import static fr.kevin.llps.todo.sample.TodoSample.todoList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private IdGeneratorService idGeneratorService;

    @InjectMocks
    private TodoService todoService;

    @Test
    void shouldCreateTodo() {
        Todo todo = oneTodo(1);
        TodoDto todoDto = oneTodoDto();
        TodoDto expected = oneTodoDto(1);

        when(todoRepository.save(todo)).thenReturn(todo);
        when(idGeneratorService.generateId()).thenReturn(1);

        TodoDto actual = todoService.createTodo(todoDto);

        assertThat(actual).isEqualTo(expected);

        verify(todoRepository).save(todo);
        verify(idGeneratorService).generateId();
        verifyNoMoreInteractions(todoRepository, idGeneratorService);
    }

    @Test
    void shouldGetAllTodos() {
        List<Todo> todoList = todoList();
        List<TodoDto> expected = todoDtoList();

        when(todoRepository.findAllOrdered()).thenReturn(todoList);

        List<TodoDto> actual = todoService.getAllTodos();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);

        verify(todoRepository).findAllOrdered();
        verifyNoMoreInteractions(todoRepository);
    }

    @Test
    void shouldGetTodoById() {
        int id = 1;
        Todo todo = oneTodo(id);
        TodoDto expected = oneTodoDto(id);

        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        TodoDto todoDto = todoService.getTodoById(id);

        assertThat(todoDto).isEqualTo(expected);

        verify(todoRepository).findById(id);
        verifyNoMoreInteractions(todoRepository);
    }

    @Test
    void givenNoExistentId_whenGetTodoById_shouldThrowTodoNotFoundException() {
        int id = 1;

        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> todoService.getTodoById(id))
                .isInstanceOf(TodoNotFoundException.class)
                .hasMessage("Todo was not found");

        verify(todoRepository).findById(id);
        verifyNoMoreInteractions(todoRepository);
    }

    @Test
    void shouldDeleteTodoById() {
        int id = 1;

        todoService.deleteTodoById(id);

        verify(todoRepository).deleteById(id);
    }
}

package fr.kevin.llps.todo.controller;

import fr.kevin.llps.todo.dto.TodoDto;
import fr.kevin.llps.todo.exception.TodoNotFoundException;
import fr.kevin.llps.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Test
    void shouldCreateTodo() throws Exception {
        TodoDto todoDto = new TodoDto();
        todoDto.setTitle("Test Todo");
        todoDto.setCompleted(false);
        todoDto.setNumOrder(1);
        todoDto.setExpiryDate(LocalDateTime.now());

        when(todoService.createTodo(any(TodoDto.class))).thenReturn(todoDto);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Todo\", \"completed\": false, \"numOrder\": 1, \"expiryDate\": \"" + LocalDateTime.now().toString() + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Todo"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.numOrder").value(1))
                .andExpect(jsonPath("$.expiryDate").value(todoDto.getExpiryDate().toString()));
    }

    @Test
    void shouldReturnBadRequestWhenTitleIsMissing() throws Exception {
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"completed\": false, \"numOrder\": 1, \"expiryDate\": \"" + LocalDateTime.now().toString() + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Title is mandatory"));
    }

    @Test
    void shouldReturnBadRequestWhenCompletedIsMissing() throws Exception {
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Todo\", \"numOrder\": 1, \"expiryDate\": \"" + LocalDateTime.now().toString() + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Completed status is mandatory"));
    }

    @Test
    void shouldGetAllTodos() throws Exception {
        TodoDto todo1 = new TodoDto();
        todo1.setTitle("Test Todo 1");
        todo1.setCompleted(false);
        todo1.setNumOrder(1);
        todo1.setExpiryDate(LocalDateTime.now());

        TodoDto todo2 = new TodoDto();
        todo2.setTitle("Test Todo 2");
        todo2.setCompleted(true);
        todo2.setNumOrder(2);
        todo2.setExpiryDate(LocalDateTime.now().plusDays(1));

        List<TodoDto> todos = Arrays.asList(todo1, todo2);

        when(todoService.getAllTodos()).thenReturn(todos);

        mockMvc.perform(get("/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test Todo 1"))
                .andExpect(jsonPath("$[1].title").value("Test Todo 2"));
    }

    @Test
    void shouldGetTodoById() throws Exception {
        TodoDto todoDto = new TodoDto();
        todoDto.setTitle("Test Todo");
        todoDto.setCompleted(false);
        todoDto.setNumOrder(1);
        todoDto.setExpiryDate(LocalDateTime.now());

        when(todoService.getTodoById(1)).thenReturn(todoDto);

        mockMvc.perform(get("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Todo"));
    }

    @Test
    void shouldReturnNotFoundWhenTodoByIdDoesNotExist() throws Exception {
        when(todoService.getTodoById(1)).thenThrow(new TodoNotFoundException("Todo was not found"));

        mockMvc.perform(get("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Todo was not found"));
    }

    @Test
    void shouldDeleteTodoById() throws Exception {
        mockMvc.perform(delete("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

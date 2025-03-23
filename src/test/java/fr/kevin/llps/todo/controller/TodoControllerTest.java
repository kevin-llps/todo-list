package fr.kevin.llps.todo.controller;

import fr.kevin.llps.todo.dto.TodoDto;
import fr.kevin.llps.todo.exception.TodoNotFoundException;
import fr.kevin.llps.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.List;

import static fr.kevin.llps.todo.sample.TodoDtoSample.oneTodoDto;
import static fr.kevin.llps.todo.sample.TodoDtoSample.todoDtoList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Value("classpath:/json/get-todo-by-id-response.json")
    private Resource getTodoByIdResponse;

    @Value("classpath:/json/get-all-todo-response.json")
    private Resource getAllTodoResponse;

    @Test
    void shouldGetAllTodos() throws Exception {
        List<TodoDto> todoList = todoDtoList();

        when(todoService.getAllTodos()).thenReturn(todoList);

        mockMvc.perform(get("/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(getAllTodoResponse.getContentAsString(Charset.defaultCharset())));

        verify(todoService).getAllTodos();
        verifyNoMoreInteractions(todoService);
    }

    @Test
    void shouldGetTodoById() throws Exception {
        int id = 1;

        TodoDto todoDto = oneTodoDto(id);

        when(todoService.getTodoById(id)).thenReturn(todoDto);

        mockMvc.perform(get("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(getTodoByIdResponse.getContentAsString(Charset.defaultCharset()), true));

        verify(todoService).getTodoById(id);
        verifyNoMoreInteractions(todoService);
    }

    @Test
    void shouldReturnNotFound_whenTodoIdDoesNotExist() throws Exception {
        when(todoService.getTodoById(1)).thenThrow(new TodoNotFoundException("Todo was not found"));

        mockMvc.perform(get("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Todo was not found"));

        verify(todoService).getTodoById(1);
        verifyNoMoreInteractions(todoService);
    }

}

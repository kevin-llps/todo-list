package fr.kevin.llps.todo.integration;

import fr.kevin.llps.todo.entity.Todo;
import fr.kevin.llps.todo.repository.TodoRepository;
import fr.kevin.llps.todo.service.IdGeneratorService;
import fr.kevin.llps.todo.utils.MySQLContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.todo.sample.TodoSample.oneTodo;
import static fr.kevin.llps.todo.sample.TodoSample.todoList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
class TodoIntegTest extends MySQLContainerTest {

    @Value("classpath:/json/create-todo-request.json")
    private Resource createTodoRequest;

    @Value("classpath:/json/create-todo-response.json")
    private Resource createTodoResponse;

    @Value("classpath:/json/get-todo-by-id-response.json")
    private Resource getTodoByIdResponse;

    @Value("classpath:/json/get-all-todo-response.json")
    private Resource getAllTodoResponse;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @MockitoBean
    private IdGeneratorService idGeneratorService;

    @AfterEach
    void tearDown() {
        todoRepository.deleteAll();
    }

    @Test
    void shouldCreateTodo() throws Exception {
        when(idGeneratorService.generateId()).thenReturn(1);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createTodoRequest.getContentAsString(Charset.defaultCharset())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createTodoResponse.getContentAsString(Charset.defaultCharset()), true));

        List<Todo> todoList = todoRepository.findAll();

        assertThat(todoList).isNotNull()
                .hasSize(1)
                .extracting("title", "completed", "numOrder", "expiryDate")
                .containsExactlyInAnyOrder(
                        tuple("Test Todo", false, 1, LocalDateTime.parse("2025-03-22T21:49:05.815")));
    }

    @Test
    void shouldGetAllTodos() throws Exception {
        todoRepository.saveAll(todoList());

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(getAllTodoResponse.getContentAsString(Charset.defaultCharset()), true));
    }

    @Test
    void shouldGetByTodoId() throws Exception {
        todoRepository.save(oneTodo(1));

        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(getTodoByIdResponse.getContentAsString(Charset.defaultCharset()), true));
    }

    @Test
    void shouldDeleteTodoById() throws Exception {
        todoRepository.save(oneTodo(1));

        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isNoContent());

        assertThat(todoRepository.findAll()).isEmpty();
    }

}

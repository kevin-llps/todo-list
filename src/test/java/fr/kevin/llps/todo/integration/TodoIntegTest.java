package fr.kevin.llps.todo.integration;

import fr.kevin.llps.todo.repository.TodoRepository;
import fr.kevin.llps.todo.utils.MySQLContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static fr.kevin.llps.todo.sample.TodoSample.oneTodo;
import static fr.kevin.llps.todo.sample.TodoSample.todoList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
class TodoIntegTest extends MySQLContainerTest {

    @Value("classpath:/json/get-todo-by-id-response.json")
    private Resource getTodoByIdResponse;

    @Value("classpath:/json/get-all-todo-response.json")
    private Resource getAllTodoResponse;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @AfterEach
    void tearDown() {
        todoRepository.deleteAll();
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

}

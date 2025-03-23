package fr.kevin.llps.todo.repository;

import fr.kevin.llps.todo.entity.Todo;
import fr.kevin.llps.todo.utils.MySQLContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static fr.kevin.llps.todo.sample.TodoSample.todoList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class TodoRepositoryTest extends MySQLContainerTest {

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository.saveAll(todoList());
    }

    @AfterEach
    void tearDown() {
        todoRepository.deleteAll();
    }

    @Transactional
    @Test
    void shouldFindAllOrdered() {
        List<Todo> todoList = todoRepository.findAllOrdered();

        assertThat(todoList).isNotNull()
                .hasSize(2)
                .extracting("title", "completed", "numOrder", "expiryDate")
                .containsExactlyInAnyOrder(
                        tuple("Test Todo 1", false, 1, LocalDateTime.parse("2025-03-22T21:49:05.815")),
                        tuple("Test Todo 2", true, 1, LocalDateTime.parse("2025-03-23T10:30:05.123")));
    }

}

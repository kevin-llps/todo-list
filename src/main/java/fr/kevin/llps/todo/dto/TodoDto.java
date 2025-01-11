package fr.kevin.llps.todo.dto;

import fr.kevin.llps.todo.entity.Todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TodoDto {

    private Integer todoId;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotNull(message = "Completed status is mandatory")
    private Boolean completed;

    private Integer numOrder;
    private LocalDateTime expiryDate;

    public TodoDto() {}

    public TodoDto(Todo todo) {
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.completed = todo.getCompleted();
        this.numOrder = todo.getNumOrder();
        this.expiryDate = todo.getExpiryDate();
    }

    public Integer getTodoId() {
        return todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getNumOrder() {
        return numOrder;
    }

    public void setNumOrder(Integer numOrder) {
        this.numOrder = numOrder;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}

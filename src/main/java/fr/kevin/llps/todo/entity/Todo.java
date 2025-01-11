package fr.kevin.llps.todo.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id", nullable = false, columnDefinition = "INT")
    private Integer todoId;

    @Column(name = "title", nullable = false, length = 200, columnDefinition = "VARCHAR(200)")
    private String title;

    @Column(name = "completed", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean completed;

    @Column(name = "num_order", nullable = true, columnDefinition = "INT")
    private Integer numOrder;

    @Column(name = "expiry_date", nullable = true, columnDefinition = "DATETIME")
    private LocalDateTime expiryDate;

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

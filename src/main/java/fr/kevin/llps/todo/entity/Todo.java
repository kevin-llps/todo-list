package fr.kevin.llps.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}

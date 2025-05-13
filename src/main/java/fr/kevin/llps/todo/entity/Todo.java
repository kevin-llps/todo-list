package fr.kevin.llps.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}

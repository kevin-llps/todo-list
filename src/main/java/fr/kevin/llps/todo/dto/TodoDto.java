package fr.kevin.llps.todo.dto;

import fr.kevin.llps.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

    private Integer id;

    private String title;

    private Boolean completed;

    private Integer numOrder;
    private LocalDateTime expiryDate;

    public TodoDto(Todo todo) {
        this.id = todo.getTodoId();
        this.title = todo.getTitle();
        this.completed = todo.getCompleted();
        this.numOrder = todo.getNumOrder();
        this.expiryDate = todo.getExpiryDate();
    }

}

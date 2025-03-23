package fr.kevin.llps.todo.dto;

import fr.kevin.llps.todo.entity.Todo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

    private Integer id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotNull(message = "Completed status is mandatory")
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

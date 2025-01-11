package fr.kevin.llps.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.kevin.llps.todo.entity.Todo;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Query("SELECT t FROM Todo t ORDER BY t.expiryDate, t.completed, t.title")
    List<Todo> findAllOrdered();
}

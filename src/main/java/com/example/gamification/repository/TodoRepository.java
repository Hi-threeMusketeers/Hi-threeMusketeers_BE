package com.example.gamification.repository;

import com.example.gamification.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByMember_LoginIdAndTodoDate(String loginId, LocalDate todoDate);

    List<Todo> findByMember_LoginIdAndTodoDateBetween(String loginId, LocalDate startDate, LocalDate endDate);

    Optional<Todo> findByTodoIdAndMember_LoginId(Long todoId, String loginId);
}
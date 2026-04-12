package com.example.gamification.repository;

import com.example.gamification.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByMember_MemberIdAndTodoDate(Long memberId, LocalDate todoDate);

    List<Todo> findByMember_MemberIdAndTodoDateBetween(Long memberId, LocalDate startDate, LocalDate endDate);
}
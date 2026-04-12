package com.example.gamification.domain.todo;

import com.example.gamification.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "todo")
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long todoId;

    @Column(name = "todo_date", nullable = false)
    private LocalDate todoDate;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Todo(Member member, LocalDate todoDate, String title, String content) {
        this.member = member;
        this.todoDate = todoDate;
        this.title = title;
        this.content = content;
        this.isCompleted = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void complete() {
        this.isCompleted = true;
        this.updatedAt = LocalDateTime.now();
    }
}
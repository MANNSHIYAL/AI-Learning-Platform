package com.multisystem.task.taskservice.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.multisystem.taskcontract.staticvalues.TaskStatus;
import com.multisystem.taskcontract.staticvalues.TaskType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private String taskId;

    @Column(nullable=false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition="TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TaskStatus taskStatus = TaskStatus.PENDING;

        @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TaskType taskType = TaskType.EXPLAIN;

    @Column(columnDefinition="TEXT")
    private String aiResponse;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

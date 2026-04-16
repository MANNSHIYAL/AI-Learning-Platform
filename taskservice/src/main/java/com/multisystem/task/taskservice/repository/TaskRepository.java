package com.multisystem.task.taskservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multisystem.task.taskservice.entity.Task;
import com.multisystem.taskcontract.staticvalues.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByUserIdOrderByCreatedAtDesc(String userId);
    List<Task> findByUserIdAndTaskStatus(String userId,TaskStatus status);
}

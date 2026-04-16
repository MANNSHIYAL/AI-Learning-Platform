package com.multisystem.task.taskservice.dto;

import java.time.LocalDateTime;

import com.multisystem.task.taskservice.entity.Task;
import com.multisystem.taskcontract.staticvalues.TaskStatus;
import com.multisystem.taskcontract.staticvalues.TaskType;

import lombok.Data;

@Data
public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private TaskType taskType;
    private TaskStatus taskStatus;
    private String aiResponse;
    private LocalDateTime createdAt;

    public static TaskResponse from(Task task){
        TaskResponse response = new TaskResponse();
        response.id = task.getTaskId();
        response.title = task.getTitle();
        response.description = task.getDescription();
        response.taskType = task.getTaskType();
        response.taskStatus = task.getTaskStatus();
        response.aiResponse = task.getAiResponse();
        response.createdAt = task.getCreatedAt();
        return response;   
    }

}

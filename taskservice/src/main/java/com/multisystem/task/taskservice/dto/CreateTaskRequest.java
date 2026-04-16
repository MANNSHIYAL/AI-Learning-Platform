package com.multisystem.task.taskservice.dto;

import com.multisystem.taskcontract.staticvalues.TaskType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateTaskRequest {

    @NotBlank(message="Title is required.")
    @Size(min=3,max=200,message="Title must ne 3-200 characters.")
    private String title;
    
    @Size(max=2000,message="Description max 2000 characters.")
    private String description;

    @NotBlank(message="Task type is required.")
    private TaskType taskType;
}

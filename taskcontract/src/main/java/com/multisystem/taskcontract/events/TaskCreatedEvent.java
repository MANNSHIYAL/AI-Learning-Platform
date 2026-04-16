package com.multisystem.taskcontract.events;

import java.time.LocalDateTime;

import com.multisystem.taskcontract.staticvalues.TaskType;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TaskCreatedEvent {
    private String taskId;
    private Long userId;
    private String Title;
    private String description;
    private TaskType taskType; 
    private LocalDateTime createedDate;
}

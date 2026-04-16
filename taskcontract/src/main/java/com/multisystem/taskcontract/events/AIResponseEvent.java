package com.multisystem.taskcontract.events;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AIResponseEvent {
    private String taskId;
    private Long userId;
    private String response;
    private boolean success;
    private String errorMessage;
}

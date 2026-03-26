package com.multisystem.contracts.events;

import java.util.Map;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TaskEvent {
    private String type;
    private Map<String,Object> payload;
}

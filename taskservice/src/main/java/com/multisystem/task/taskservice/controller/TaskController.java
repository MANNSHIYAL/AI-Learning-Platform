package com.multisystem.task.taskservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multisystem.contracts.events.TaskEvent;
import com.multisystem.task.taskservice.producer.TaskProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private TaskProducer taskProducer;

    @PostMapping
    public String create(@RequestBody TaskEvent event){
        taskProducer.send(event);
        return "Task Sent";
    }
}

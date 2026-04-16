package com.multisystem.task.taskservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multisystem.task.taskservice.producer.TaskKafkaProducer;
import com.multisystem.taskcontract.events.TaskCreatedEvent;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private TaskKafkaProducer taskProducer;

    @PostMapping
    public String create(@RequestBody TaskCreatedEvent event){
        taskProducer.publishTaskCreated(event);
        return "Task Sent";
    }
}

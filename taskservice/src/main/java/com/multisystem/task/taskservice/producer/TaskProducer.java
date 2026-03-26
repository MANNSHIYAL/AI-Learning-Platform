package com.multisystem.task.taskservice.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.multisystem.contracts.events.TaskEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskProducer {

    private KafkaTemplate<String, TaskEvent> kafka;

    public void send(TaskEvent event){
        kafka.send("task-created",event);
    }
}

package com.multisystem.task.taskservice.producer;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.multisystem.taskcontract.events.TaskCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(TaskKafkaProducer.class);
    @Value("${task.kafka.create}")
    private final String TOPIC;

    private final KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate;

    public void publishTaskCreated(TaskCreatedEvent event){

        CompletableFuture<SendResult<String,TaskCreatedEvent>> future = kafkaTemplate.send(TOPIC,event.getTaskId(),event);
        
        future.whenComplete((result,ex) -> {
            if(ex != null){
                log.error("[KAFKA] Failed to publish TaskCreatedEvent for taskId={}: {}",event.getTaskId(), ex.getMessage());
            }else{
                log.info("[KAFKA] Published TaskCreatedEvent taskId={} to partition={}",event.getTaskId(),result.getRecordMetadata().partition());
            }
        });
    }
}

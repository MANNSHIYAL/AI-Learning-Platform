package com.multisystem.ai.aiservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.multisystem.ai.aiservice.service.RagService;
import com.multisystem.contracts.events.TaskEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskConsumer {
    private RagService ragService;

    @KafkaListener(topics="task-ai-reuqest")
    public void consumer(TaskEvent event){
        String query = (String) event.getPayload().get("query");

        String response = ragService.answer(query);

        System.out.println("AI Response: " + response);
    }
}

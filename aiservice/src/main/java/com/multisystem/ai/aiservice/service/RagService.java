package com.multisystem.ai.aiservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RagService {
    private final EmbeddingService embeddingService;
    private final RetrievalService retrievalService;
    private final LLMService llmService;

    public String answer(String query) {

        // 1. Convert query → embedding
        float[] vector = embeddingService.embed(query);

        // 2. Retrieve relevant docs
        List<String> docs = retrievalService.search(vector);

        // 3. Build prompt
        String prompt = buildPrompt(query, docs);

        // 4. Call LLM
        return llmService.generate(prompt);
    }

    private String buildPrompt(String query, List<String> docs) {
        return "Answer based ONLY on context:\n" +
                String.join("\n", docs) +
                "\n\nQuestion: " + query;
    }
}

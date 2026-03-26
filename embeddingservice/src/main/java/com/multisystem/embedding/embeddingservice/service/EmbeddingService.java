package com.multisystem.embedding.embeddingservice.service;

import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    public float[] generateEmbedding(String text) {
        // Call external model (OpenAI / local model)
        return new float[]{0.1f, 0.2f}; // mock
    }
}

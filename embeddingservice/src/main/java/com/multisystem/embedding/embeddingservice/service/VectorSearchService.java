package com.multisystem.embedding.embeddingservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class VectorSearchService {
    public List<String> search(float[] queryVector){
        // Implement similarity search (cosine similarity)
        return List.of("doc1", "doc2");
    }
}

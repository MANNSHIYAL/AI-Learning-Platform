package com.multisystem.ai.aiservice.service;

import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {
    public float[] embed(String query){
        float[] data = {(float) 1.0,(float) 2.0}; 
        return data;
    }
}

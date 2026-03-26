package com.multisystem.ai.aiservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RetrievalService {
    public List<String> search(float[] vector){
        // TODO: integrate pgvector / elasticsearch
        return List.of("Sample context 1","Sample context 2"); 
    }
}

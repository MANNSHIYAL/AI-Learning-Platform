package com.multisystem.embedding.embeddingservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multisystem.embedding.embeddingservice.service.EmbeddingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/embeddings")
@RequiredArgsConstructor
public class EmbeddingController {
    private final EmbeddingService embeddingService;

    @PostMapping
    public float[] embed(@RequestBody String text){
        return embeddingService.generateEmbedding(text);
    }
}

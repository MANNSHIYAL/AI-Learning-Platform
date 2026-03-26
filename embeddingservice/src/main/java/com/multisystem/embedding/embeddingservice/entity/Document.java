package com.multisystem.embedding.embeddingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Document {
    @Id
    @GeneratedValue
    private Long id;
    private String content;

    @Column(columnDefinition="vector")
    private float[] embedding;
}

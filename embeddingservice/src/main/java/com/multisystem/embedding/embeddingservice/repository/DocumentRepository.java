package com.multisystem.embedding.embeddingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multisystem.embedding.embeddingservice.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{

}

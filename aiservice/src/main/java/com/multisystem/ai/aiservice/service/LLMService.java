package com.multisystem.ai.aiservice.service;

import org.springframework.stereotype.Service;

@Service
public class LLMService {
    public String generate(String prompt){
        return "Generate AI response for: " + prompt;
    }
}

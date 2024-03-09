package com.example.playersapp.utils.health;

import org.springframework.stereotype.Service;

@Service
public class ApplicationReadinessService {

    private volatile boolean isReady = false;

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
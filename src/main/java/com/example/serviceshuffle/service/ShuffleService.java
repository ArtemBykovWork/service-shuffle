package com.example.serviceshuffle.service;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ShuffleService {
    List<Integer> shuffleNumber(int number);

    @Async
    CompletableFuture<Void> logRequestAsync(List<Integer> numbers);
}

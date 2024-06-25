package com.example.serviceshuffle.service.impl;

import com.example.serviceshuffle.service.ShuffleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Service
public class ShuffleServiceImpl implements ShuffleService {
    private final RestTemplate restTemplate;
    private final String logServiceUrl;
    private final Random random;

    public ShuffleServiceImpl(RestTemplate restTemplate, @Value("${log.service.url}") String logServiceUrl) {
        this.restTemplate = restTemplate;
        this.logServiceUrl = logServiceUrl;
        this.random = new Random();
    }

    @Override
    public List<Integer> shuffleNumber(int number) {
        List<Integer> numbers = IntStream.rangeClosed(1, number).boxed().collect(Collectors.toList());
        shuffle(numbers);

        logRequestAsync(numbers);

        return numbers;
    }

    //Fisher-Yates shuffle algorithm
    private void shuffle(List<Integer> list) {
        for (int i = list.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }

    @Override
    @Async
    public CompletableFuture<Void> logRequestAsync(List<Integer> numbers) {
        restTemplate.postForObject(logServiceUrl, numbers, Void.class);
        return CompletableFuture.completedFuture(null);
    }
}
package com.example.serviceshuffle.service.impl;

import com.example.serviceshuffle.service.ShuffleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShuffleServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Captor
    private ArgumentCaptor<String> urlCaptor;

    private ShuffleService shuffleService;

    private final String logServiceUrl = "http://localhost:8081/log";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        shuffleService = new ShuffleServiceImpl(restTemplate, logServiceUrl);
    }

    @Test
    public void testShuffleNumber() {
        int number = 5;

        when(restTemplate.postForObject(anyString(), any(), eq(Void.class))).thenReturn(null);

        List<Integer> shuffledNumbers = shuffleService.shuffleNumber(number);

        assertEquals(number, shuffledNumbers.size(), "Result size should match input number");

        verify(restTemplate, times(1)).postForObject(urlCaptor.capture(), any(), eq(Void.class));
        assertEquals(logServiceUrl, urlCaptor.getValue(), "URL should match logServiceUrl");

        verify(restTemplate, times(1)).postForObject(anyString(), eq(shuffledNumbers), eq(Void.class));
    }

    @Test
    public void testLogRequestAsync() throws ExecutionException, InterruptedException {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        when(restTemplate.postForObject(anyString(), any(), eq(Void.class))).thenReturn(null);

        CompletableFuture<Void> future = shuffleService.logRequestAsync(numbers);

        future.get();

        verify(restTemplate, times(1)).postForObject(urlCaptor.capture(), any(), eq(Void.class));
        assertEquals(logServiceUrl, urlCaptor.getValue(), "URL should match logServiceUrl");

        verify(restTemplate, times(1)).postForObject(anyString(), eq(numbers), eq(Void.class));
    }
}
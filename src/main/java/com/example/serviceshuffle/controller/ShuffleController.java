package com.example.serviceshuffle.controller;

import com.example.serviceshuffle.service.ShuffleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shuffle")
public class ShuffleController {

    @Autowired
    private ShuffleService shuffleService;

    @PostMapping
    public List<Integer> shuffle(@RequestParam int number) {
        return shuffleService.shuffleNumber(number);
    }
}
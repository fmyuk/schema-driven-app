package com.example.todo_api.controller.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SampleDTO {

    private String content;
    private LocalDateTime timestamp;
}

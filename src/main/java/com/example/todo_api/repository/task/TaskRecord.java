package com.example.todo_api.repository.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskRecord {

    private long id;
    private String title;
}

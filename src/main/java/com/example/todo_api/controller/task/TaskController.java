package com.example.todo_api.controller.task;

import com.example.todo_api.service.task.TaskEntity;
import com.example.todo_api.service.task.TaskService;
import com.example.todoapi.controller.TasksApi;
import com.example.todoapi.model.TaskDTO;
import com.example.todoapi.model.TaskForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {

    private final TaskService service;

    @Override
    public ResponseEntity<TaskDTO> showTask(Long taskId) {
        TaskEntity entity = service.find(taskId);
        TaskDTO body = new TaskDTO();
        body.setId(entity.getId());
        body.setTitle(entity.getTitle());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<TaskDTO> createTask(TaskForm form) {
        TaskEntity entity = service.create(form.getTitle());
        TaskDTO dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        return ResponseEntity.created(URI.create("/tasks/" + dto.getId())).body(dto);
    }
}

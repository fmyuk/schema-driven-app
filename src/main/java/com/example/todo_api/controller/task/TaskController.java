package com.example.todo_api.controller.task;

import com.example.todo_api.service.task.TaskEntity;
import com.example.todo_api.service.task.TaskService;
import com.example.todoapi.controller.TasksApi;
import com.example.todoapi.model.PageDTO;
import com.example.todoapi.model.TaskDTO;
import com.example.todoapi.model.TaskForm;
import com.example.todoapi.model.TaskListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {

    private final TaskService service;

    @Override
    public ResponseEntity<TaskDTO> showTask(Long taskId) {
        TaskEntity entity = service.find(taskId);
        TaskDTO dto = toTaskDTO(entity);

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> createTask(TaskForm form) {
        TaskEntity entity = service.create(form.getTitle());
        TaskDTO dto = toTaskDTO(entity);

        return ResponseEntity.created(URI.create("/tasks/" + dto.getId())).body(dto);
    }

    @Override
    public ResponseEntity<TaskListDTO> listTasks(Integer limit, Long offset) {
        List<TaskEntity> entityList = service.find(limit, offset);
        List<TaskDTO> dtoList = entityList.stream()
                .map(TaskController::toTaskDTO)
                .toList();

        PageDTO pageDTO = new PageDTO();
        pageDTO.setLimit(limit);
        pageDTO.setOffset(offset);
        pageDTO.setSize(dtoList.size());

        TaskListDTO dto = new TaskListDTO();
        dto.setPage(pageDTO);
        dto.setResults(dtoList);

        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> editTask(Long taskId, TaskForm taskForm) {
        TaskEntity entity = service.update(taskId, taskForm.getTitle());
        TaskDTO dto = toTaskDTO(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long taskId) {
        service.delete(taskId);
        return ResponseEntity.noContent().build();
    }

    private static TaskDTO toTaskDTO(TaskEntity taskEntity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskEntity.getId());
        taskDTO.setTitle(taskEntity.getTitle());
        return taskDTO;
    }

}

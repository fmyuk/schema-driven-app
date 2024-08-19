package com.example.todo_api.service.task;

import com.example.todo_api.repository.task.TaskRecord;
import com.example.todo_api.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public TaskEntity find(long taskId) {
        return repository.select(taskId)
                .map(record -> new TaskEntity(record.getId(), record.getTitle()))
                .orElseThrow(() -> new TaskEntityNotFoundException(taskId));
    }

    public TaskEntity create(String title) {
        TaskRecord record = new TaskRecord(null, title);
        repository.insert(record);

        return new TaskEntity(record.getId(), record.getTitle());
    }

    public List<TaskEntity> find(int limit, long offset) {
        return repository.selectList(limit, offset)
                .stream()
                .map(record -> new TaskEntity(record.getId(), record.getTitle()))
                .toList();
    }

    public TaskEntity update(Long taskId, String title) {
        repository.select(taskId)
                        .orElseThrow(() -> new TaskEntityNotFoundException(taskId));
        repository.update(new TaskRecord(taskId, title));
        return find(taskId);
    }

    public void delete(Long taskId) {
        repository.select(taskId)
                .orElseThrow(() -> new TaskEntityNotFoundException(taskId));
        repository.delete(taskId);
    }
}

package com.example.todo_api.repository.task;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskRepository {

    @Select("SELECT id, title FROM tasks WHERE id = #{taskId}")
    Optional<TaskRecord> select(long taskId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO tasks (title) VALUES (#{title})")
    void insert(TaskRecord record);

    @Select("SELECT id, title FROM tasks LIMIT #{limit} OFFSET #{offset}")
    List<TaskRecord> selectList(int limit, long offset);

    @Update("UPDATE tasks SET title = #{title} WHERE id = #{id}")
    void update(TaskRecord taskRecord);

    @Delete("DELETE FROM tasks WHERE id = #{id}")
    void delete(Long taskId);
}

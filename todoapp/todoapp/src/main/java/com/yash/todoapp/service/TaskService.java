package com.yash.todoapp.service;

import com.yash.todoapp.dto.TaskDto;
import com.yash.todoapp.entity.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

    List<TaskDto> getAllTasks();

    Page<Task> getAllTasksPageable(Integer pageNumber, Integer pageSize);

    void createTask(TaskDto taskDto);

    void deleteTask(Long id);

    TaskDto getTaskById(Long id);

    void updateTask(TaskDto taskDto);
}

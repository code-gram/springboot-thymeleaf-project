package com.yash.todoapp.service.impl;

import com.yash.todoapp.dto.TaskDto;
import com.yash.todoapp.entity.Task;
import com.yash.todoapp.repository.TaskRepository;
import com.yash.todoapp.service.TaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    @Override
    public Page<Task> getAllTasksPageable(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Task> pageTasks =  taskRepository.findAll(pageable);
        return pageTasks;
    }

    @Override
    public void createTask(TaskDto taskDto) {
        Task task = modelMapper.map(taskDto,Task.class);
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).get();
        TaskDto taskDto= modelMapper.map(task,TaskDto.class);
        return taskDto;
    }

    @Override
    public void updateTask(TaskDto taskDto) {
        taskRepository.save(modelMapper.map(taskDto,Task.class));
    }

    @Override
    public List<TaskDto> getAllTasks() {

         List<Task> tasks =  taskRepository.findAll();
        return tasks.stream().map((task)->modelMapper.map(task,TaskDto.class)).collect(Collectors.toList());
    }
}

package com.yash.todoapp.controller;

import com.yash.todoapp.DateComparatorUtil;
import com.yash.todoapp.dto.TaskDto;
import com.yash.todoapp.entity.Task;
import com.yash.todoapp.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;
    private ModelMapper modelMapper;

    @GetMapping("/tasks/{pageNumber}")
    public String getAllTasks(@PathVariable(name="pageNumber",required = false) Integer pageNumber,
            Model model){
        Integer pageSize = 5;
        Page<Task> taskPageable =   taskService.getAllTasksPageable(pageNumber,pageSize);
        List<Task> tasks =  taskPageable.getContent();
        model.addAttribute("tasks",tasks);
        model.addAttribute("currentPageNo",pageNumber);
        model.addAttribute("totalPages",taskPageable.getTotalPages());
        model.addAttribute("totalItems",taskPageable.getTotalElements());

        return "task/tasks";
    }

    @GetMapping("/createTask")
    public String createTaskForm(Model model){
        TaskDto taskDto =  new TaskDto();
        model.addAttribute("task", taskDto);
        return "task/createTask";
    }

    @PostMapping("/tasks")
    public String saveTask(@ModelAttribute("task") TaskDto task,Model model){
        taskService.createTask(task);
        model.addAttribute("msg","Task Added Successfully");
        return "redirect:/tasks";

    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id){
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model){
        TaskDto taskDto =  taskService.getTaskById(id);
        model.addAttribute("task", taskDto);
        return "task/edit_task";
    }

    @PostMapping("/tasks/{id}")
    public String updateTask(@PathVariable("id") Long id,@Valid @ModelAttribute("task") TaskDto taskDto,
                             BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("task",taskDto);
            return "task/edit_task";
        }
        taskDto.setId(id);
        taskService.updateTask(taskDto);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/view/{id}")
    public String viewTask(@PathVariable("id") Long id, Model model){
        TaskDto taskDto =  taskService.getTaskById(id);
        model.addAttribute("task",taskDto);
        return "task/view_task";
    }

    @GetMapping("/showLatest/{pageNumber}")
    public String showLatestAddedRecords(@PathVariable(name="pageNumber",required = false) Integer pageNumber,
                                         Model model){

        Integer pageSize = 5;
        Page<Task> taskPageable =   taskService.getAllTasksPageable(pageNumber,pageSize);
        List<Task> tasks =  taskPageable.getContent();
        List<TaskDto> taskDtos =  tasks.stream().map((task)->modelMapper.map(task,TaskDto.class)).collect(Collectors.toList());
        Collections.sort(taskDtos, new DateComparatorUtil());
        model.addAttribute("tasks",taskDtos);
        model.addAttribute("currentPageNo",pageNumber);
        model.addAttribute("totalPages",taskPageable.getTotalPages());
        model.addAttribute("totalItems",taskPageable.getTotalElements());
        return "task/tasks";
    }
}

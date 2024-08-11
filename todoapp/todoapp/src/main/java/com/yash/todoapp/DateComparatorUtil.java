package com.yash.todoapp;

import com.yash.todoapp.dto.TaskDto;

import java.util.Comparator;

public class DateComparatorUtil implements Comparator<TaskDto> {

    @Override
    public int compare(TaskDto taskDto1, TaskDto taskDto2) {
        return taskDto2.getStartDate().compareTo(taskDto1.getStartDate());
    }
}

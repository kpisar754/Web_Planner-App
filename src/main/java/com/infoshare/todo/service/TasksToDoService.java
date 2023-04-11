package com.infoshare.todo.service;

import com.infoshare.todo.domain.TaskToDo;
import com.infoshare.todo.repository.TasksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksToDoService {

    private final TasksRepository tasksRepository;

    public TasksToDoService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<TaskToDo> addTask(TaskToDo task) {
        return tasksRepository.addTaskToList(task);
    }

    public List<TaskToDo> getAllTasks() {
        return tasksRepository.getAllTasks();
    }
}

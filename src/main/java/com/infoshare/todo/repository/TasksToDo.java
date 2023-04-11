package com.infoshare.todo.repository;

import com.infoshare.todo.domain.TaskToDo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TasksToDo implements TasksRepository{

    private final List<TaskToDo> tasksList = new ArrayList<>();
    private final TaskSorter taskSorter;

    public TasksToDo(TaskSorter taskSorter) {
        this.taskSorter = taskSorter;
    }

    @Override
    public List<TaskToDo> getAllTasks() {
        return taskSorter.sortTasksByDescendingPriority(tasksList);
    }

    @Override
    public List<TaskToDo> addTaskToList(TaskToDo task) {
        tasksList.add(task);
        return tasksList;
    }
}

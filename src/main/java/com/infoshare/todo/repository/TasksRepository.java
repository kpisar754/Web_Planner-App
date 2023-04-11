package com.infoshare.todo.repository;

import com.infoshare.todo.domain.TaskToDo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository {

    List<TaskToDo> getAllTasks();

    List<TaskToDo> addTaskToList(TaskToDo task);
}

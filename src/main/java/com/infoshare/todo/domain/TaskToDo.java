package com.infoshare.todo.domain;

import com.infoshare.todo.repository.TaskToDoRepository;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public class TaskToDo implements TaskToDoRepository {

    private final String taskDescription;
    private final Category category;
    private final int priority;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate date;


    public TaskToDo(String taskDescription, Category category, int priority, LocalDate date) {
        this.taskDescription = taskDescription;
        this.category = category;
        this.priority = priority;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskToDo taskToDo = (TaskToDo) o;
        return priority == taskToDo.priority && Objects.equals(taskDescription, taskToDo.taskDescription) && category == taskToDo.category && Objects.equals(date, taskToDo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskDescription, category, priority, date);
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getPriority() {
        return priority;
    }

    public LocalDate getDate() {
        return date;
    }
}

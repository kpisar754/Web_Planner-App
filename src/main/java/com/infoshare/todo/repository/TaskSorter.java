package com.infoshare.todo.repository;

import com.infoshare.todo.domain.Category;
import com.infoshare.todo.domain.TaskToDo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskSorter {

    @SuppressWarnings("unused")
    private TaskToDoRepository taskToDo;
    private static final String TASKS_LIST_CANNOT_BE_NULL_MESSAGE = "tasks list cannot be null";

    public List<TaskToDo> searchTasksWithHighestPriorityEqualToOne(List<TaskToDo> tasks) {
        return Objects.requireNonNull(tasks, TASKS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .filter(taskToDo -> taskToDo.getPriority() == 1)
                .toList();
    }

    public List<TaskToDo> searchTasksForNextDay(List<TaskToDo> tasks) {
        LocalDate tomorrow = LocalDate.now().plusDays(1L);

        return Objects.requireNonNull(tasks, TASKS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .filter(taskToDo -> taskToDo.getDate().equals(tomorrow))
                .toList();
    }

    public List<TaskToDo> sortTasksByDescendingPriority(List<TaskToDo> tasks) {
        return Objects.requireNonNull(tasks, TASKS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .sorted(Comparator.comparingInt(TaskToDo::getPriority))
                .toList();
    }

    public List<TaskToDo> sortTaskByDateFromNewest(List<TaskToDo> tasks) {
        return Objects.requireNonNull(tasks, TASKS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .sorted(Comparator.comparing(TaskToDo::getDate))
                .toList();
    }

    public List<TaskToDo> searchTasksBySelectedCategory(List<TaskToDo> tasks, Category category) {
        return Objects.requireNonNull(tasks, TASKS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .filter(taskToDo -> category != null && category.equals(taskToDo.getCategory()))
                .toList();
    }

    public List<TaskToDo> searchTasksByPhraseInDescription(List<TaskToDo> tasks, String phrase) {
        return Objects.requireNonNull(tasks, TASKS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .filter(taskToDo -> phrase != null && taskToDo.getTaskDescription().toLowerCase().contains(phrase.trim().toLowerCase()))
                .toList();
    }

    public TaskToDo searchMostImportantTaskByDateAndPriority(List<TaskToDo> tasks) {
        return Objects.requireNonNull(tasks, TASKS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .min(Comparator.comparing(TaskToDo::getDate).thenComparing(TaskToDo::getPriority))
                .orElseThrow(NoSuchElementException::new);
    }

    public Map<Category, List<TaskToDo>> divideTasksByCategory(List<TaskToDo> tasks) {
        return Objects.requireNonNull(tasks, TASKS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .filter(taskToDo -> taskToDo.getCategory() != null)
                .collect(Collectors.groupingBy(TaskToDo::getCategory));
    }

    public Map<Integer, List<TaskToDo>> divideTasksByPriority(List<TaskToDo> tasks) {
        return Objects.requireNonNull(tasks, TASKS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .filter(taskToDo -> taskToDo.getPriority() > 0 && taskToDo.getPriority() <= 5)
                .collect(Collectors.groupingBy(TaskToDo::getPriority));
    }

    public Map<Category, Optional<TaskToDo>> searchTasksWithHighestPriorityForEachCategory(List<TaskToDo> tasks) {
        return Objects.requireNonNull(tasks, TASKS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .filter(taskToDo -> taskToDo.getCategory() != null)
                .collect(Collectors.groupingBy(TaskToDo::getCategory,
                        Collectors.minBy(Comparator.comparingInt(TaskToDo::getPriority))));
    }
}
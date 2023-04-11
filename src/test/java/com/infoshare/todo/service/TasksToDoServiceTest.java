package com.infoshare.todo.service;

import com.infoshare.todo.domain.Category;
import com.infoshare.todo.domain.TaskToDo;
import com.infoshare.todo.repository.TasksRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TasksToDoServiceTest {

    private TasksRepository tasksRepositoryMock;
    private TasksToDoService service;
    private TaskToDo task;
    private TaskToDo task2;
    private TaskToDo nullTask;
    private static final LocalDate NOW = LocalDate.now();

    @BeforeEach
    void setUpTest() {

        // given
        tasksRepositoryMock = mock(TasksRepository.class);
        service = new TasksToDoService(tasksRepositoryMock);
        task = new TaskToDo("have to buy second monitor", Category.MY_JOB, 3, NOW.plusWeeks(2L));
        task2 = new TaskToDo("take child to aquapark", Category.CHILDREN, 4, NOW.minusMonths(1L));
        nullTask = null;
    }

    @AfterEach
    void closeTest() {

        tasksRepositoryMock = null;
        service = null;
        task = null;
        task2 = null;
    }

    @Test
    void testIfTaskIsProperlyAddedToList() {
        // given
        when(tasksRepositoryMock.addTaskToList(any(TaskToDo.class)))
                .thenReturn(Collections.singletonList(task));

        // when
        List<TaskToDo> tasksListAfterAddingOneTask = service.addTask(task);

        // then
        assertThat(tasksListAfterAddingOneTask)
                .hasSize(1)
                .containsOnly(task);
    }

    @Test
    void testIfListIsEmptyWhenTaskIsNull() {
        // given
        when(tasksRepositoryMock.addTaskToList(any(TaskToDo.class)))
                .thenReturn(Collections.singletonList(task));

        // when
        List<TaskToDo> tasksListWhenTaskIsNull = service.addTask(nullTask);

        // then
        assertThat(tasksListWhenTaskIsNull).isEmpty();
    }

    @Test
    void testIfAllTasksAreReturnedByMethod() {
        // given
        when(tasksRepositoryMock.getAllTasks())
                .thenReturn(List.of(task, task2));

        // when
        List<TaskToDo> allTasksList = service.getAllTasks();

        // then
        assertThat(allTasksList).hasSize(2)
                .containsExactlyInAnyOrder(task, task2);
    }

    @Test
    void testIfEmptyListIsReturnedWhenNoTasks() {
        // given
        when(tasksRepositoryMock.getAllTasks())
                .thenReturn(Collections.emptyList());

        // when and then
        List<TaskToDo> allTasksListWhenEmpty = service.getAllTasks();

        // then
        assertThat(allTasksListWhenEmpty).isEmpty();
    }
}
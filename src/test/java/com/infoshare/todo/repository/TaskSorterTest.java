package com.infoshare.todo.repository;

import com.infoshare.todo.domain.Category;
import com.infoshare.todo.domain.TaskToDo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskSorterTest {

    private static final LocalDate NOW = LocalDate.now();
    private TaskSorter sorter;
    private List<TaskToDo> testTasksList;
    private List<TaskToDo> testTasksListForMapTests;
    private List<TaskToDo> testEmptyTasksList;
    private List<TaskToDo> testListWithNullArgumentForCategory;
    private List<TaskToDo> testListWithZeroArgumentForPriority;
    private TaskToDo task1;
    private TaskToDo task2;
    private TaskToDo task3;
    private TaskToDo task4;
    private TaskToDo task5;
    private TaskToDo task6;
    private TaskToDo task7;

    @BeforeEach
    void setUpTest() {
        sorter = new TaskSorter();

        // given
        task1 = new TaskToDo("do homework", Category.MY_JOB, 1, NOW.plusDays(1L));
        task2 = new TaskToDo("take child to dentist", Category.CHILDREN, 3, NOW.plusDays(5L));
        task3 = new TaskToDo("start to prepare my bike for season", Category.MY_FREE_TIME, 5, NOW.plusWeeks(2L));
        task4 = new TaskToDo("replace the bulb", Category.HOME, 1, NOW);
        task5 = new TaskToDo("take the garbage out", Category.MY_HEALTH, 2, NOW);
        task6 = new TaskToDo("improve the project", Category.MY_JOB, 3, NOW.plusDays(8L));
        task7 = new TaskToDo("start to prepare my cv", Category.MY_JOB, 4, NOW.plusWeeks(3L));

        // given
        testTasksList = List.of(task1, task2, task3, task4, task5, task6, task7);
        testTasksListForMapTests = List.of(task1, task2, task3, task6);
        testEmptyTasksList = List.of();
        testListWithNullArgumentForCategory = List.of(new TaskToDo("take child to lunapark", null,5, NOW.plusMonths(1L)));
        testListWithZeroArgumentForPriority = List.of(new TaskToDo("take child to lunapark", Category.CHILDREN,0, NOW.plusMonths(1L)));
    }

    @AfterEach
    void closeTest() {
        sorter = null;
        testTasksList = null;
        testTasksListForMapTests = null;
        testEmptyTasksList = null;
        testListWithNullArgumentForCategory = null;
    }

    @Test
    void testIfTasksWithHighestPriorityAreCorrectlySearched() {
        // when
        List<TaskToDo> tasksWithHighestPriority = sorter.searchTasksWithHighestPriorityEqualToOne(testTasksList);

        // then
        assertThat(tasksWithHighestPriority)
                .hasSize(2)
                .containsExactlyInAnyOrder(task1, task4);
    }

    @Test
    void testIfEmptyListIsReturnedWhenListToBeSearchedByHighestPriorityIsEmpty() {
        // when
        List<TaskToDo> tasksWithHighestPriorityWhenEmptyList = sorter.searchTasksWithHighestPriorityEqualToOne(testEmptyTasksList);

        // then
        assertThat(tasksWithHighestPriorityWhenEmptyList).isEmpty();
    }

    @Test
    void testIfTasksForNextDayAreCorrectlySearched() {
        // when
        List<TaskToDo> tasksForNextDay = sorter.searchTasksForNextDay(testTasksList);

        // then
        assertThat(tasksForNextDay)
                .hasSize(1)
                .containsOnly(task1);
    }

    @Test
    void testIfEmptyListIsReturnedWhenListToBeSearchedByTasksForNextDayIsEmpty() {
        // when
        List<TaskToDo> tasksForNextDayWhenEmptyList = sorter.searchTasksForNextDay(testEmptyTasksList);

        // then
        assertThat(tasksForNextDayWhenEmptyList).isEmpty();
    }

    @Test
    void testIfTasksAreCorrectlySortedByDescendingPriority() {
        // when
        List<TaskToDo> tasksByDescPriority = sorter.sortTasksByDescendingPriority(testTasksList);

        // then
        assertThat(tasksByDescPriority).isSortedAccordingTo(Comparator.comparingInt(TaskToDo::getPriority));
    }

    @Test
    void testIfEmptyListIsReturnedWhenListToBeSortedByDescPriorityIsEmpty() {
        // when
        List<TaskToDo> tasksByDescPriorityWhenEmptyList = sorter.sortTasksByDescendingPriority(testEmptyTasksList);

        // then
        assertThat(tasksByDescPriorityWhenEmptyList).isEmpty();
    }

    @Test
    void testIfTasksAreCorrectlySortedByDateFromNewest() {
        // when
        List<TaskToDo> tasksByDateFromNewest = sorter.sortTaskByDateFromNewest(testTasksList);

        // then
        assertThat(tasksByDateFromNewest).isSortedAccordingTo(Comparator.comparing(TaskToDo::getDate));
    }

    @Test
    void testIfEmptyListIsReturnedWhenListToBeSortedByDateFromNewestIsEmpty() {
        // when
        List<TaskToDo> tasksByDateFromNewestWhenEmptyList = sorter.sortTaskByDateFromNewest(testEmptyTasksList);

        // then
        assertThat(tasksByDateFromNewestWhenEmptyList).isEmpty();
    }

    @Test
    void testIfTasksFromSelectedCategoryAreCorrectlySearched() {
        // when
        List<TaskToDo> tasksFromSelectedCategory = sorter.searchTasksBySelectedCategory(testTasksList, Category.MY_JOB);

        // then
        assertThat(tasksFromSelectedCategory)
                .hasSize(3)
                .extracting(TaskToDo::getCategory)
                .containsOnly(Category.MY_JOB);
    }

    @Test
    void testIfEmptyListIsReturnedWhenListIsSortedWhenSelectedCategoryIsNull() {
        // when
        List<TaskToDo> tasksBySelectedCategoryWhenCategoryIsNull = sorter.searchTasksBySelectedCategory(testTasksList, null);

        // then
        assertThat(tasksBySelectedCategoryWhenCategoryIsNull).isEmpty();
    }

    @Test
    void testIfTasksWithGivenPhraseInDescriptionAreCorrectlySearched() {
        // when
        List<TaskToDo> tasksWithGivenPhraseInDescr = sorter.searchTasksByPhraseInDescription(testTasksList, "start to prepare");

        // then
        assertThat(tasksWithGivenPhraseInDescr)
                .hasSize(2)
                .allMatch(task -> task.getTaskDescription().startsWith("start to prepare"));
    }

    @Test
    void testIfEmptyListIsReturnedWhenListIsSortedWhenGivenPhraseIsNull() {
        // when
        List<TaskToDo> tasksWithGivenPhraseInDescrWhenPhraseIsNull = sorter.searchTasksByPhraseInDescription(testTasksList, null);

        // then
        assertThat(tasksWithGivenPhraseInDescrWhenPhraseIsNull).isEmpty();
    }

    @Test
    void testIfOneTaskWithShortestDeadlineAndHighestPriorityIsCorrectlySearched() {
        // when
        TaskToDo taskWithShortestDeadlineAndHighestPriority = sorter.searchMostImportantTaskByDateAndPriority(testTasksList);
        TaskToDo expectedTask = task4;
        TaskToDo notExpectedTask = task7;

        // then
        assertEquals(expectedTask, taskWithShortestDeadlineAndHighestPriority);
        assertNotEquals(notExpectedTask, taskWithShortestDeadlineAndHighestPriority);
    }

    @Test
    void testIfExceptionIsThrownWhenMostImportantTaskIsNotFoundBecauseOfEmptyList() {
        // when and then
        assertThrows(NoSuchElementException.class, () -> sorter.searchMostImportantTaskByDateAndPriority(testEmptyTasksList),
                "NoSuchElementException should be thrown");
    }

    @Test
    void testIfMapWithProperValuesIsReturnedByMethodThatDividesTasksByCategory() {
        // when
        Map<Category, List<TaskToDo>> tasksMapByCategory = sorter.divideTasksByCategory(testTasksListForMapTests);

        // then
        assertThat(tasksMapByCategory)
                .containsKeys(Category.MY_JOB, Category.CHILDREN, Category.MY_FREE_TIME)
                .doesNotContainKeys(Category.HOME, Category.MY_HEALTH);
        assertThat(tasksMapByCategory.get(Category.MY_JOB))
                .hasSize(2)
                .contains(task1, task6);
    }

    @Test
    void testIfEmptyMapIsReturnedByMethodThatDividesTasksByCategoryWhenCategoryIsNull() {
        // when
        Map<Category, List<TaskToDo>> resultMapWhenCategoryIsNull = sorter.divideTasksByCategory(testListWithNullArgumentForCategory);

        // then
        assertThat(resultMapWhenCategoryIsNull).isEmpty();
    }

    @Test
    void testIfMapWithProperValuesIsReturnedByMethodThatDividesTasksByPriority() {
        // when
        Map<Integer, List<TaskToDo>> tasksMapByPriority = sorter.divideTasksByPriority(testTasksListForMapTests);

        // then
        assertThat(tasksMapByPriority)
                .containsKeys(1, 3, 5)
                .doesNotContainKeys(2, 4);
        assertThat(tasksMapByPriority.get(5))
                .hasSize(1)
                .contains(task3);
    }

    @Test
    void testIfExceptionIsThrownByMethodThatDividesTasksByPriorityWhenPriorityIsOutOfRange() {
        // when
        Map<Integer, List<TaskToDo>> resultMapWhenPriorityIsOutOfRange = sorter.divideTasksByPriority(testListWithZeroArgumentForPriority);

        // then
        assertThat(resultMapWhenPriorityIsOutOfRange).isEmpty();
    }

    @Test
    void testIfMapWithProperValuesIsReturnedByMethodThatSearchesTaskWithHighestPriorityForEachCategory() {
        // when
        Map<Category, Optional<TaskToDo>> tasksWithHighestPriorityForEachCategory = sorter.searchTasksWithHighestPriorityForEachCategory(testTasksList);

        // then
        assertThat(tasksWithHighestPriorityForEachCategory.get(Category.MY_JOB)).isEqualTo(Optional.of(task1));
        assertThat(tasksWithHighestPriorityForEachCategory.get(Category.MY_HEALTH)).isEqualTo(Optional.of(task5));
        assertThat(tasksWithHighestPriorityForEachCategory.get(Category.HOME)).isNotEqualTo(Optional.of(task2));
    }

    @Test
    void testIfEmptyMapIsReturnedByMethodThatSearchesTasksWithHighestPriorityForEachCategoryWhenListHasNullArgumentForCategory() {
        // when
        Map<Category, Optional<TaskToDo>> taskWithHighestPriorityForEachCategoryWhenCategoryIsNull = sorter.searchTasksWithHighestPriorityForEachCategory(testListWithNullArgumentForCategory);

        // then
        assertThat(taskWithHighestPriorityForEachCategoryWhenCategoryIsNull).isEmpty();
    }
}

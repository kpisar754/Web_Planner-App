package com.infoshare.todo.repository;

import com.infoshare.todo.domain.Category;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TaskToDoRepository {

    String getTaskDescription();
    Category getCategory();
    Integer getPriority();
    LocalDate getDate();
}

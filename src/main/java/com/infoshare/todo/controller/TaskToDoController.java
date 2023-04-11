package com.infoshare.todo.controller;

import com.infoshare.todo.domain.TaskToDo;
import com.infoshare.todo.service.TasksToDoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Controller
public class TaskToDoController {

    private final TasksToDoService tasksToDoService;
    private final LocaleResolver localeResolver;

    public TaskToDoController(TasksToDoService tasksToDoService, LocaleResolver localeResolver) {
        this.tasksToDoService = tasksToDoService;
        this.localeResolver = localeResolver;
    }

    @GetMapping
    public ModelAndView showAllTasks(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("main");
        List<TaskToDo> tasks = tasksToDoService.getAllTasks();
        model.addObject("tasks", tasks);
        Locale locale = localeResolver.resolveLocale(request);
        model.addObject("lang", locale.getLanguage());
        return model;
    }

    @GetMapping("/create")
    public ModelAndView showTaskCreateForm() {
        ModelAndView model = new ModelAndView("add-task");
        TaskToDo task = new TaskToDo("",null, 1, LocalDate.now());
        model.addObject("task", task);
        return model;
    }

    @PostMapping("task/create")
    public ModelAndView createTask(TaskToDo task) {
        ModelAndView model = new ModelAndView("redirect:/");
        List<TaskToDo> tasksToDo = tasksToDoService.addTask(task);
        model.addObject("tasks", tasksToDo);
        return model;
    }
}

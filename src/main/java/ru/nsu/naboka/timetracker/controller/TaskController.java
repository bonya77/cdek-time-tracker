package ru.nsu.naboka.timetracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nsu.naboka.timetracker.dto.TaskDto;
import ru.nsu.naboka.timetracker.model.Task;
import ru.nsu.naboka.timetracker.model.TaskStatus;
import ru.nsu.naboka.timetracker.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public Task createTask(@Valid @RequestBody TaskDto dto){
        return taskService.createTask(dto);
    }

    @GetMapping("/{id}")
    public Task getTaskId(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id, @RequestParam TaskStatus status){
        taskService.updateTaskStatus(id, status);
    }
}

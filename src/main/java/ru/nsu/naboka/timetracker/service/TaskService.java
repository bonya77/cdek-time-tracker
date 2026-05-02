package ru.nsu.naboka.timetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.naboka.timetracker.dto.TaskDto;
import ru.nsu.naboka.timetracker.exception.ResourceNotFoundException;
import ru.nsu.naboka.timetracker.mapper.TaskMapper;
import ru.nsu.naboka.timetracker.model.Task;
import ru.nsu.naboka.timetracker.model.TaskStatus;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskMapper taskMapper;

    public Task createTask(TaskDto dto){
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setTaskStatus(TaskStatus.NEW);

        taskMapper.insert(task);
        return task;
    }

    public Task getTaskById(Long id){
        Task task = taskMapper.findById(id);
        if(task == null){
            throw new ResourceNotFoundException("Задача с id " + id + " не найдена");
        }
        return task;
    }
    public void updateTaskStatus(Long id, TaskStatus status){
        getTaskById(id);
        taskMapper.updateStatus(id, status);
    }
}

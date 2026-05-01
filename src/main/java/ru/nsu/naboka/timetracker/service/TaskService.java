package ru.nsu.naboka.timetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.naboka.timetracker.dto.TaskDto;
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
            throw new RuntimeException("Task not found");
        }
        return task;
    }
    public void updateTaskStatus(Long id, TaskStatus status){
        if(getTaskById(id) != null){
            taskMapper.updateStatus(id, status);
        }
        else
            throw new RuntimeException("Task not found");
    }
}

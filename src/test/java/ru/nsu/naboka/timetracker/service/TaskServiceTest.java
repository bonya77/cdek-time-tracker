package ru.nsu.naboka.timetracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nsu.naboka.timetracker.dto.TaskDto;
import ru.nsu.naboka.timetracker.exception.ResourceNotFoundException;
import ru.nsu.naboka.timetracker.mapper.TaskMapper;
import ru.nsu.naboka.timetracker.model.Task;
import ru.nsu.naboka.timetracker.model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_ShouldReturnNewTask() {
        TaskDto dto = new TaskDto();
        dto.setTitle("Test Task");
        dto.setDescription("Test Description");

        Task result = taskService.createTask(dto);

        assertEquals("Test Task", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals(TaskStatus.NEW, result.getTaskStatus());

        // Проверяю что маппер был вызван для сохранения
        verify(taskMapper, times(1)).insert(any(Task.class));
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenTaskExists() {
        Task mockTask = new Task(1L, "Title", "Desc", TaskStatus.NEW);
        when(taskMapper.findById(1L)).thenReturn(mockTask);
        Task result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getTaskById_ShouldThrowException_WhenTaskDoesNotExist() {
        when(taskMapper.findById(1L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void updateTaskStatus_ShouldUpdateStatus() {
        Task mockTask = new Task(1L, "Title", "Desc", TaskStatus.NEW);
        when(taskMapper.findById(1L)).thenReturn(mockTask);

        taskService.updateTaskStatus(1L, TaskStatus.IN_PROGRESS);

        verify(taskMapper, times(1)).updateStatus(1L, TaskStatus.IN_PROGRESS);
    }
}
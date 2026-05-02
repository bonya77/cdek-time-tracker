package ru.nsu.naboka.timetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.nsu.naboka.timetracker.dto.TaskDto;
import ru.nsu.naboka.timetracker.exception.ResourceNotFoundException;
import ru.nsu.naboka.timetracker.model.Task;
import ru.nsu.naboka.timetracker.model.TaskStatus;
import ru.nsu.naboka.timetracker.service.TaskService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc; // Инструмент для имитации HTTP-запросов

    @Autowired
    private ObjectMapper objectMapper; // Инструмент для конвертации объектов в JSON

    @MockitoBean
    private TaskService taskService; // Мокируем сервис, чтобы не лезть в БД

    @Test
    void createTask_ShouldReturnCreatedTask() throws Exception {
        // Arrange
        TaskDto dto = new TaskDto();
        dto.setTitle("Controller Test");
        dto.setDescription("Desc");

        Task task = new Task(1L, "Controller Test", "Desc", TaskStatus.NEW);

        when(taskService.createTask(any(TaskDto.class))).thenReturn(task);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Controller Test"));
    }

    @Test
    void createTask_ShouldReturn400_WhenTitleIsBlank() throws Exception {
        // Arrange
        TaskDto dto = new TaskDto();
        dto.setTitle(""); // Невалидное поле (нарушает @NotBlank)

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()) // Ожидаем 400 Bad Request
                .andExpect(jsonPath("$.message").exists()); // GlobalExceptionHandler должен вернуть сообщение
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenExists() throws Exception {
        // Arrange
        Task task = new Task(1L, "Title", "Desc", TaskStatus.NEW);
        when(taskService.getTaskById(1L)).thenReturn(task);

        // Act & Assert
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getTaskById_ShouldReturn404_WhenTaskNotFound() throws Exception {
        // Arrange
        when(taskService.getTaskById(99L)).thenThrow(new ResourceNotFoundException("Task not found"));

        // Act & Assert
        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound()) // Ожидаем 404 Not Found
                .andExpect(jsonPath("$.message").value("Task not found"));
    }

    @Test
    void updateStatus_ShouldReturn200() throws Exception {
        // Act & Assert
        mockMvc.perform(patch("/api/tasks/1/status")
                        .param("status", "DONE"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).updateTaskStatus(1L, TaskStatus.DONE);
    }
}
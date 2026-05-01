package ru.nsu.naboka.timetracker.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TimeRecordDto {
    @NotNull(message = "employee ID cannot be empty")
    private Long employeeId;

    @NotNull(message = "task ID cannot be empty")
    private Long taskId;

    @NotNull(message = "start time cannot be empty")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @NotNull(message = "task description cannot be empty")
    private String workDescription;
}

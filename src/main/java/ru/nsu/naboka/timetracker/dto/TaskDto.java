package ru.nsu.naboka.timetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDto {
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 255)
    private String title;

    private String description;
}

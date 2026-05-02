package ru.nsu.naboka.timetracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nsu.naboka.timetracker.dto.TimeRecordDto;
import ru.nsu.naboka.timetracker.mapper.TimeRecordMapper;
import ru.nsu.naboka.timetracker.model.Task;
import ru.nsu.naboka.timetracker.model.TaskStatus;
import ru.nsu.naboka.timetracker.model.TaskTimeRecord;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeRecordServiceTest {

    @Mock
    private TimeRecordMapper timeRecordMapper;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TimeRecordService timeRecordService;

    @Test
    void createRecord_ShouldReturnRecord_WhenValidDto() {
        TimeRecordDto dto = new TimeRecordDto();
        dto.setTaskId(1L);
        dto.setEmployeeId(100L);
        dto.setStartTime(LocalDateTime.now().minusHours(2));
        dto.setEndTime(LocalDateTime.now());
        dto.setWorkDescription("Doing some work");

        Task mockTask = new Task(1L, "Title", "Desc", TaskStatus.IN_PROGRESS);
        when(taskService.getTaskById(1L)).thenReturn(mockTask);

        TaskTimeRecord result = timeRecordService.createRecord(dto);

        assertNotNull(result);
        assertEquals(100L, result.getEmployeeId());
        assertEquals("Doing some work", result.getWorkDescription());
        verify(timeRecordMapper, times(1)).insert(any(TaskTimeRecord.class));
    }

    @Test
    void createRecord_ShouldThrowException_WhenEndTimeBeforeStartTime() {
        TimeRecordDto dto = new TimeRecordDto();
        dto.setTaskId(1L);
        dto.setStartTime(LocalDateTime.now());
        dto.setEndTime(LocalDateTime.now().minusHours(1));

        when(taskService.getTaskById(1L)).thenReturn(new Task());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> timeRecordService.createRecord(dto)
        );
        assertEquals("End time cannot be befor start time", exception.getMessage());
    }

    @Test
    void getRecordByPeriod_ShouldThrowException_WhenPeriodInvalid() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusDays(1);

        assertThrows(IllegalArgumentException.class, () ->
                timeRecordService.getRecordByPeriod(1L, start, end)
        );
    }
}
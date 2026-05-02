package ru.nsu.naboka.timetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.nsu.naboka.timetracker.dto.TimeRecordDto;
import ru.nsu.naboka.timetracker.model.TaskTimeRecord;
import ru.nsu.naboka.timetracker.service.TimeRecordService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimeRecordController.class)
class TimeRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TimeRecordService timeRecordService;

    @Test
    void createRecord_ShouldReturnRecord() throws Exception {
        // Arrange
        TimeRecordDto dto = new TimeRecordDto();
        dto.setEmployeeId(1L);
        dto.setTaskId(2L);
        dto.setStartTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)); // убираем микросекунды для JSON
        dto.setWorkDescription("Test Work");

        TaskTimeRecord record = new TaskTimeRecord(1L, 1L, 2L, dto.getStartTime(), null, "Test Work");

        when(timeRecordService.createRecord(any(TimeRecordDto.class))).thenReturn(record);

        // Act & Assert
        mockMvc.perform(post("/api/time-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.workDescription").value("Test Work"));
    }

    @Test
    void getRecords_ShouldReturnList() throws Exception {
        // Arrange
        LocalDateTime start = LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime end = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        TaskTimeRecord record = new TaskTimeRecord(1L, 1L, 2L, start, end, "Work");
        when(timeRecordService.getRecordByPeriod(eq(1L), any(), any())).thenReturn(List.of(record));

        // Act & Assert
        mockMvc.perform(get("/api/time-records")
                        .param("employeeId", "1")
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].employeeId").value(1));
    }
}
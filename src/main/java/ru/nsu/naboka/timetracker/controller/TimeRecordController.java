package ru.nsu.naboka.timetracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.nsu.naboka.timetracker.dto.TimeRecordDto;
import ru.nsu.naboka.timetracker.model.TaskTimeRecord;
import ru.nsu.naboka.timetracker.service.TimeRecordService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/time-records")
@RequiredArgsConstructor
public class TimeRecordController {
    private final TimeRecordService timeRecordService;

    @PostMapping
    public TaskTimeRecord createRecord(@Valid @RequestBody TimeRecordDto dto){
        return timeRecordService.CreateRecord(dto);
    }

    @GetMapping
    public List<TaskTimeRecord> getRecords(@RequestParam Long employeeId,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                           LocalDateTime start,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                               LocalDateTime end){
        return timeRecordService.getRecordByPeriod(employeeId, start, end);
    }
}

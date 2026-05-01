package ru.nsu.naboka.timetracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.naboka.timetracker.dto.TimeRecordDto;
import ru.nsu.naboka.timetracker.mapper.TimeRecordMapper;
import ru.nsu.naboka.timetracker.model.TaskTimeRecord;

@RestController
@RequestMapping("/api/time-records")
@RequiredArgsConstructor

public class TimeRecordController {
    private final TimeRecordMapper timeRecordMapper;

    @PostMapping
    public String createRecord(@Valid @RequestBody TimeRecordDto dto){

    }

}

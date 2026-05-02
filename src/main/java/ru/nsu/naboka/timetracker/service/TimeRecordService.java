package ru.nsu.naboka.timetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.naboka.timetracker.dto.TimeRecordDto;
import ru.nsu.naboka.timetracker.mapper.TimeRecordMapper;
import ru.nsu.naboka.timetracker.model.TaskTimeRecord;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TimeRecordService {
    private final TimeRecordMapper timeRecordMapper;
    private final TaskService taskService;
    public TaskTimeRecord createRecord(TimeRecordDto dto) {

        taskService.getTaskById(dto.getTaskId());

        if (dto.getEndTime() != null && dto.getEndTime().isBefore(dto.getStartTime())) {
            throw new IllegalArgumentException("End time cannot be befor start time");
        }

        TaskTimeRecord record = new TaskTimeRecord();
        record.setEmployeeId(dto.getEmployeeId());
        record.setStartTime(dto.getStartTime());
        record.setWorkDescription(dto.getWorkDescription());
        record.setEndTime(dto.getEndTime());
        record.setTaskId(dto.getTaskId());

        timeRecordMapper.insert(record);
        return record;
    }

    public List<TaskTimeRecord> getRecordByPeriod(Long employeeId, LocalDateTime start,
                                                  LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("incorrect period: start data after end data");
        }
        return timeRecordMapper.selectByEmployeeIdAndPeriod(employeeId, start, end);
    }
}
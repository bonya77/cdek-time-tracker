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

    public TaskTimeRecord CreateRecord(TimeRecordDto dto){
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
                                                  LocalDateTime end){
        return timeRecordMapper.selectByEmployeeIdAndPeriod(employeeId, start, end);
    }
}

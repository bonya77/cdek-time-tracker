package ru.nsu.naboka.timetracker.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.naboka.timetracker.model.Task;
import ru.nsu.naboka.timetracker.model.TaskStatus;
import ru.nsu.naboka.timetracker.model.TaskTimeRecord;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@org.springframework.test.context.jdbc.Sql(scripts = "/init.sql")
class TimeRecordMapperTest {

    @Autowired
    private TimeRecordMapper timeRecordMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void selectByEmployeeIdAndPeriod_ShouldReturnRecords() {
        Task task = new Task(null, "Task 1", "Desc", TaskStatus.NEW);
        taskMapper.insert(task);

        LocalDateTime now = LocalDateTime.now();
        TaskTimeRecord record = new TaskTimeRecord(null, 1L, task.getId(), now.minusHours(1), now, "Work");
        timeRecordMapper.insert(record);

        List<TaskTimeRecord> records = timeRecordMapper.selectByEmployeeIdAndPeriod(
                1L, now.minusHours(2), now.plusHours(1)
        );

        assertFalse(records.isEmpty());
        assertEquals(1, records.size());
        assertEquals("Work", records.get(0).getWorkDescription());
    }
}
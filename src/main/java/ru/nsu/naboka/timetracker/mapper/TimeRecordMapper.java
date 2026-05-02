package ru.nsu.naboka.timetracker.mapper;

import org.apache.ibatis.annotations.*;
import ru.nsu.naboka.timetracker.model.TaskTimeRecord;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TimeRecordMapper {

    @Insert("INSERT INTO time_records (employee_id, task_id, start_time, end_time, work_description) " +
            "VALUES (#{employeeId}, #{taskId}, #{startTime}, #{endTime}, #{workDescription})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TaskTimeRecord record);

    @Select("SELECT id, employee_id AS employeeId, task_id AS taskId, start_time AS startTime, " +
            "end_time AS endTime, work_description AS workDescription " +
            "FROM time_records WHERE employee_id = #{employeeId} AND start_time <= #{endTime} " +
            "AND end_time >= #{startTime}")
    List<TaskTimeRecord> selectByEmployeeIdAndPeriod(@Param("employeeId") Long employeeId,
                                                     @Param("startTime") LocalDateTime startTime,
                                                     @Param("endTime") LocalDateTime endTime);
}

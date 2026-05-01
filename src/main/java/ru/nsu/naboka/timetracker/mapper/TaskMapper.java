package ru.nsu.naboka.timetracker.mapper;

import org.apache.ibatis.annotations.*;
import ru.nsu.naboka.timetracker.model.Task;
import ru.nsu.naboka.timetracker.model.TaskStatus;

@Mapper
public interface TaskMapper {

    @Insert("INSERT INTO tasks ( title, description, taskStatus) VALUES (#{title}, #{description}, #{taskStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Task task);

    @Select("SELECT id, title, description, taskStatus FROM tasks WHERE id = #{id}")
    Task findById(Long id);

    @Update("UPDATE tasks SET taskStatus = #{taskStatus} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("taskStatus") TaskStatus taskStatus);

}

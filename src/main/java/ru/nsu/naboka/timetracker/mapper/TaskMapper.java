package ru.nsu.naboka.timetracker.mapper;

import org.apache.ibatis.annotations.*;
import ru.nsu.naboka.timetracker.model.Task;
import ru.nsu.naboka.timetracker.model.TaskStatus;

@Mapper
public interface TaskMapper {
    @Insert("INSERT INTO tasks (title, description, status) VALUES (#{title}, #{description}, #{taskStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Task task);

    @Select("SELECT id, title, description, status AS taskStatus FROM tasks WHERE id = #{id}")
    Task findById(Long id);

    @Update("UPDATE tasks SET status = #{taskStatus} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("taskStatus") TaskStatus taskStatus);
}
package ru.nsu.naboka.timetracker.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.naboka.timetracker.model.Task;
import ru.nsu.naboka.timetracker.model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@org.springframework.test.context.jdbc.Sql(scripts = "/init.sql")
class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void insertAndFindById_ShouldWork() {
        Task task = new Task(null, "Test Task H2", "Description", TaskStatus.NEW);

        taskMapper.insert(task);

        assertNotNull(task.getId(), "ID должен быть сгенерирован");
        Task found = taskMapper.findById(task.getId());
        assertNotNull(found);
        assertEquals("Test Task H2", found.getTitle());
        assertEquals(TaskStatus.NEW, found.getTaskStatus());
    }

    @Test
    void updateStatus_ShouldChangeTaskStatus() {
        Task task = new Task(null, "Task to update", "Desc", TaskStatus.NEW);
        taskMapper.insert(task);
        taskMapper.updateStatus(task.getId(), TaskStatus.DONE);

        Task updatedTask = taskMapper.findById(task.getId());
        assertEquals(TaskStatus.DONE, updatedTask.getTaskStatus());
    }
}
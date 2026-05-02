package ru.nsu.naboka.timetracker.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.nsu.naboka.timetracker.model.Task;
import ru.nsu.naboka.timetracker.model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskMapperTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        // Говорим Spring инициализировать базу скриптом schema.sql из test/resources
        registry.add("spring.sql.init.mode", () -> "always");
    }

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void insertAndFindById_ShouldWork() {
        Task task = new Task(null, "Test Task", "Description", TaskStatus.NEW);
        taskMapper.insert(task);

        assertNotNull(task.getId());
        Task found = taskMapper.findById(task.getId());
        assertEquals("Test Task", found.getTitle());
    }
}
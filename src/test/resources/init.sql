CREATE TABLE tasks (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       status VARCHAR(20) NOT NULL CHECK (status IN ('NEW', 'IN_PROGRESS', 'DONE'))
);

CREATE TABLE time_records (
                              id SERIAL PRIMARY KEY,
                              employee_id BIGINT NOT NULL,
                              task_id INTEGER REFERENCES tasks(id) ON DELETE CASCADE,
                              start_time TIMESTAMP NOT NULL,
                              end_time TIMESTAMP,
                              work_description TEXT
);
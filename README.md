```

---

## Особенности реализации (Дополнительные критерии)

*   **Валидация**: Входные DTO проверяются с помощью Bean Validation (`@NotBlank`, `@NotNull`, `@Size`)[cite: 10].
*   **Обработка исключений**: Реализован `@RestControllerAdvice` для обработки `ResourceNotFoundException`, ошибок валидации и бизнес-логики[cite: 10].
*   **Логика**: При создании записи времени проверяется существование задачи и корректность временного интервала (время окончания не может быть раньше начала).

---

## Модели данных

### Task (Задача)

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| id | Long | Уникальный идентификатор[cite: 10] |
| title | String | Название задачи[cite: 10] |
| description | String | Описание задачи[cite: 10] |
| taskStatus | Enum | Статус: NEW, IN_PROGRESS, DONE[cite: 10] |

### TimeRecord (Запись времени)

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| id | Long | Уникальный идентификатор[cite: 10] |
| employeeId | Long | ID сотрудника[cite: 10] |
| taskId | Long | ID задачи[cite: 10] |
| startTime | LocalDateTime | Время начала работ[cite: 10] |
| endTime | LocalDateTime | Время окончания работ[cite: 10] |
| workDescription | String | Описание проделанных работПонял тебя, в прошлом ответе произошел какой-то сбой разметки Markdown, и часть текста слилась. Давай я исправлю это и выведу **весь README одним чистым блоком кода**, чтобы тебе было удобно скопировать его целиком в один файл без битых таблиц.
```markdown
# Task time tracker API

Backend REST-сервис для учета времени сотрудников СДЭК, затраченного на выполнение задач[cite: 10]. Система позволяет создавать задачи, изменять их статусы и фиксировать временные интервалы работы[cite: 10].

## Технологии

*   **Java 21** (или 17+)[cite: 10]
*   **Spring Boot 3.x**[cite: 10]
*   **MyBatis**[cite: 10]
*   **PostgreSQL Database** (Docker)[cite: 10]
*   **Maven**[cite: 10]
*   **Lombok**
*   **Bean Validation (Hibernate Validator)**[cite: 10]
*   **JUnit 5 & Mockito**[cite: 10]

---

## Сборка и запуск

### Сборка проекта

С прогоном юнит-тестов[cite: 10]:
```bash
./mvnw clean package
```

Без запуска тестов:
```bash
./mvnw clean package -DskipTests
```

### Запуск приложения

1.  **Запуск базы данных (PostgreSQL в Docker)**:
    Убедитесь, что файл `init.sql` находится по пути `src/main/resources/init.sql`.
    ```bash
    sudo docker compose up -d
    ```

2.  **Запуск приложения**:
    ```bash
    ./mvnw spring-boot:run
    ```

Приложение запустится на [http://localhost:8080](http://localhost:8080).
Конфигурация подключения к БД находится в `src/main/resources/application.properties`.

---

## API

### Задачи (Tasks)

| Метод | URL | Описание |
| :--- | :--- | :--- |
| POST | `/api/tasks` | Создание новой задачи[cite: 10] |
| GET | `/api/tasks/{id}` | Получение информации о задаче по ID[cite: 10] |
| PATCH | `/api/tasks/{id}/status` | Изменение статуса задачи (NEW/IN_PROGRESS/DONE)[cite: 10] |

### Записи времени (Time Records)

| Метод | URL | Описание |
| :--- | :--- | :--- |
| POST | `/api/time-records` | Создать запись о затраченном времени сотрудника на задачу[cite: 10] |
| GET | `/api/time-records` | Получить время сотрудника за период (параметры `employeeId`, `start`, `end`)[cite: 10] |

---

## Примеры использования

### Создать задачу

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Разработка API", "description": "Реализовать эндпоинты для СДЭК"}'
```

**Ответ:**
```json
{
  "id": 1,
  "title": "Разработка API",
  "description": "Реализовать эндпоинты для СДЭК",
  "taskStatus": "NEW"
}
```

### Создать запись времени

```bash
curl -X POST http://localhost:8080/api/time-records \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": 123,
    "taskId": 1,
    "startTime": "2026-05-02T10:00:00",
    "workDescription": "Написание мапперов MyBatis"
  }'
```

### Получить информацию о времени за период

```bash
curl "http://localhost:8080/api/time-records?employeeId=123&start=2026-05-01T00:00:00&end=2026-05-31T23:59:59"
```

---

## Особенности реализации (Дополнительные критерии)

*   **Валидация**: Входные DTO проверяются с помощью Bean Validation (`@NotBlank`, `@NotNull`, `@Size`)[cite: 10].
*   **Обработка исключений**: Реализован `@RestControllerAdvice` для обработки `ResourceNotFoundException`, ошибок валидации и бизнес-логики[cite: 10].
*   **Логика**: При создании записи времени проверяется существование задачи и корректность временного интервала (время окончания не может быть раньше начала).

---

## Модели данных

### Task (Задача)

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| id | Long | Уникальный идентификатор[cite: 10] |
| title | String | Название задачи[cite: 10] |
| description | String | Описание задачи[cite: 10] |
| taskStatus | Enum | Статус: NEW, IN_PROGRESS, DONE[cite: 10] |

### TimeRecord (Запись времени)

| Поле | Тип | Описание |
| :--- | :--- | :--- |
| id | Long | Уникальный идентификатор[cite: 10] |
| employeeId | Long | ID сотрудника[cite: 10] |
| taskId | Long | ID задачи[cite: 10] |
| startTime | LocalDateTime | Время начала работ[cite: 10] |
| endTime | LocalDateTime | Время окончания работ[cite: 10] |
| workDescription | String | Описание проделанных работ[cite: 10] |
```
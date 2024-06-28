package synergyhubback.calendar.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.calendar.domain.entity.Task;
import synergyhubback.calendar.dto.request.TaskCreateRequest;
import synergyhubback.calendar.dto.request.TaskUpdateRequest;
import synergyhubback.calendar.dto.response.TaskResponse;
import synergyhubback.calendar.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/task/regist")
    public TaskResponse createTask(@RequestBody synergyhubback.calendar.dto.request.TaskCreateRequest taskRequest) {
        Task task = taskService.createTask(taskRequest);
        return TaskResponse.from(task);
    }

    @PutMapping("/task/{id}")
    public TaskResponse updateTask(@PathVariable String id, @RequestBody TaskUpdateRequest taskRequest) {
        Task task = taskService.updateTask(id, taskRequest);
        return TaskResponse.from(task);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks")
    public List<TaskResponse> getAllTasks() {
        return taskService.findAllTasks().stream().map(TaskResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/task/{id}")
    public TaskResponse getTaskById(@PathVariable String id) {
        Task task = taskService.findTaskById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        return TaskResponse.from(task);
    }
}

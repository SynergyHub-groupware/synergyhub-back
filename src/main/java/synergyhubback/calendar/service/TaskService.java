package synergyhubback.calendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.calendar.domain.entity.Task;
import synergyhubback.calendar.domain.repository.TaskRepository;
import synergyhubback.calendar.dto.request.TaskCreateRequest;
import synergyhubback.calendar.dto.request.TaskUpdateRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(TaskCreateRequest taskRequest) {
        Task task = new Task();
        task.setId(generateTaskCode());
        task.setTitle(taskRequest.getTitle());
        task.setModifiedDate(taskRequest.getModifiedDate());
        task.setStart(taskRequest.getStart());
        task.setEnd(taskRequest.getEnd());
        task.setStatus(taskRequest.getStatus());
        task.setPriority(taskRequest.getPriority());
        task.setDescription(taskRequest.getDescription());
        task.setOwner(taskRequest.getOwner());
        task.setDisplay(taskRequest.isDisplay());
        // Set employee if needed
        return taskRepository.save(task);
    }

    public Task updateTask(String id, TaskUpdateRequest taskRequest) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(taskRequest.getTitle());
        task.setModifiedDate(taskRequest.getModifiedDate());
        task.setStart(taskRequest.getStart());
        task.setEnd(taskRequest.getEnd());
        task.setStatus(taskRequest.getStatus());
        task.setPriority(taskRequest.getPriority());
        task.setDescription(taskRequest.getDescription());
        task.setOwner(taskRequest.getOwner());
        task.setDisplay(taskRequest.isDisplay());
        // Set employee if needed
        return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> findTaskById(String id) {
        return taskRepository.findById(id);
    }

    private String generateTaskCode() {
        long count = taskRepository.count();
        return "TA" + (count + 1);
    }
}

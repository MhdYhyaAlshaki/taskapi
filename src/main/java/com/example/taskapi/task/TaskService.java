package com.example.taskapi.task;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.taskapi.user.User;
import com.example.taskapi.user.UserNotFoundException;
import com.example.taskapi.user.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponse> getAll() {
        return taskRepository.findAll().stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    public TaskResponse getById(Long id) {
        return new TaskResponse(findTask(id));
    }

    public TaskResponse create(CreateTaskRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        Task task = new Task(request.getTitle(), request.isCompleted(), user);
        return new TaskResponse(taskRepository.save(task));
    }

    public TaskResponse update(Long id, UpdateTaskRequest request) {
        Task task = findTask(id);
        task.setTitle(request.getTitle());
        task.setCompleted(request.isCompleted());
        return new TaskResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    private Task findTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}

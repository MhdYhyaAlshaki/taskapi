package com.example.taskapi.task;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.taskapi.common.PageResponse;
import com.example.taskapi.common.error.InvalidSortFieldException;
import com.example.taskapi.user.User;
import com.example.taskapi.user.UserNotFoundException;
import com.example.taskapi.user.UserRepository;

@Service
public class TaskService {

    private static final List<String> SORTABLE_FIELDS = Arrays.asList("id", "title", "completed", "user.id", "user.name");

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public PageResponse<TaskResponse> search(Long userId, Boolean completed, String title, Pageable pageable) {
        for (Sort.Order order : pageable.getSort()) {
            if (!SORTABLE_FIELDS.contains(order.getProperty())) {
                throw new InvalidSortFieldException("Invalid sort field. Allowed fields: " + String.join(", ", SORTABLE_FIELDS));
            }
        }

        String escapedTitle = title == null ? null : title.replace("!", "!!").replace("%", "!%").replace("_", "!_");
        Page<TaskResponse> page = taskRepository.search(userId, completed, escapedTitle, pageable)
                .map(TaskResponse::new);
        return new PageResponse<>(page);
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

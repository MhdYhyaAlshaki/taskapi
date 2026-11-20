package com.example.taskapi.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll(Sort.by("id")).stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponse(user);
    }

    public UserResponse create(CreateUserRequest request) {
        User user = new User(request.getName(), request.getEmail());
        try {
            return new UserResponse(userRepository.save(user));
        } catch (DataIntegrityViolationException ex) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new DuplicateUserEmailException(ex);
            }
            throw ex;
        }
    }
}

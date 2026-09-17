package com.sreymouy.incidenttrackerapi.service;

import com.sreymouy.incidenttrackerapi.model.User;
import com.sreymouy.incidenttrackerapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
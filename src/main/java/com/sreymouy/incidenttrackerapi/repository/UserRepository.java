package com.sreymouy.incidenttrackerapi.repository;

import com.sreymouy.incidenttrackerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
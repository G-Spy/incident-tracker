package com.sreymouy.incidenttrackerapi.repository;

import com.sreymouy.incidenttrackerapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
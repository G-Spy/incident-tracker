package com.sreymouy.incidenttrackerapi.service;

import com.sreymouy.incidenttrackerapi.model.Comment;
import com.sreymouy.incidenttrackerapi.model.Incident;
import com.sreymouy.incidenttrackerapi.model.User;
import com.sreymouy.incidenttrackerapi.repository.CommentRepository;
import com.sreymouy.incidenttrackerapi.repository.IncidentRepository;
import com.sreymouy.incidenttrackerapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public IncidentService(
            IncidentRepository incidentRepository,
            UserRepository userRepository,
            CommentRepository commentRepository) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public Incident createIncident(Incident incident) {
        return incidentRepository.save(incident);
    }

    public List<Incident> getAllIncidents(String status) {
        if (status == null || status.isBlank()) {
            return incidentRepository.findAll();
        }

        return incidentRepository.findByStatus(status);
    }

    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ticket not found"
                ));
    }

    public Incident updateIncidentStatus(Long id, String newStatus) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ticket not found"
                ));

        String currentStatus = incident.getStatus();

        boolean validTransition =
                (currentStatus.equals("OPEN") && newStatus.equals("IN_PROGRESS")) ||
                        (currentStatus.equals("IN_PROGRESS") && newStatus.equals("RESOLVED")) ||
                        (currentStatus.equals("RESOLVED") && newStatus.equals("CLOSED"));

        if (!validTransition) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid status transition from "
                            + currentStatus + " to " + newStatus
            );
        }

        incident.setStatus(newStatus);
        incident.setStatusChangedAt(LocalDateTime.now());

        return incidentRepository.save(incident);
    }

    public Incident assignIncident(Long incidentId, Long userId) {

        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ticket not found"
                ));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        incident.setAssignee(user);

        return incidentRepository.save(incident);
    }

    public Comment addComment(Long incidentId, Long userId, String content) {

        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ticket not found"
                ));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        Comment comment = new Comment();

        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setIncident(incident);

        return commentRepository.save(comment);
    }
}
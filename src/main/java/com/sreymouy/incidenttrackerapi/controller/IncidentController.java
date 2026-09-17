package com.sreymouy.incidenttrackerapi.controller;

import com.sreymouy.incidenttrackerapi.model.Comment;
import com.sreymouy.incidenttrackerapi.model.Incident;
import com.sreymouy.incidenttrackerapi.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Incident createIncident(@Valid @RequestBody Incident incident) {
        return incidentService.createIncident(incident);
    }

    @GetMapping
    public List<Incident> getAllIncidents(
            @RequestParam(required = false) String status) {
        return incidentService.getAllIncidents(status);
    }

    @GetMapping("/{id}")
    public Incident getIncidentById(@PathVariable Long id) {
        return incidentService.getIncidentById(id);
    }

    @PatchMapping("/{id}/status")
    public Incident updateIncidentStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        String newStatus = request.get("status");

        return incidentService.updateIncidentStatus(id, newStatus);
    }

    @PatchMapping("/{id}/assignee")
    public Incident assignIncident(
            @PathVariable Long id,
            @RequestBody Map<String, Long> request) {

        Long userId = request.get("userId");

        return incidentService.assignIncident(id, userId);
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addComment(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {

        String content = (String) request.get("content");
        Long userId = ((Number) request.get("userId")).longValue();

        return incidentService.addComment(id, userId, content);
    }
}
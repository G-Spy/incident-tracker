package com.sreymouy.incidenttrackerapi.repository;

import com.sreymouy.incidenttrackerapi.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findByStatus(String status);
}
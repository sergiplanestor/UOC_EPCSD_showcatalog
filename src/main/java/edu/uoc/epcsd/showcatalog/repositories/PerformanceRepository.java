package edu.uoc.epcsd.showcatalog.repositories;

import edu.uoc.epcsd.showcatalog.models.entities.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}

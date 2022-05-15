package edu.uoc.epcsd.showcatalog.repositories;

import edu.uoc.epcsd.showcatalog.models.db.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}

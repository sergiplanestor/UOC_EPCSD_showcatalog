package edu.uoc.epcsd.showcatalog.repositories;

import edu.uoc.epcsd.showcatalog.models.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ShowRepository extends JpaRepository<Show, Long> {
}

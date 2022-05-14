package edu.uoc.epcsd.showcatalog.repositories;

import edu.uoc.epcsd.showcatalog.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

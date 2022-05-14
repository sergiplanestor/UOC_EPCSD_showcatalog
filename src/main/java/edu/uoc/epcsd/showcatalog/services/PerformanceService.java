package edu.uoc.epcsd.showcatalog.services;

import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.errors.exceptions.NotUniqueValueError;
import edu.uoc.epcsd.showcatalog.models.entities.Performance;
import edu.uoc.epcsd.showcatalog.models.entities.Show;
import edu.uoc.epcsd.showcatalog.repositories.PerformanceRepository;
import edu.uoc.epcsd.showcatalog.utils.ListUtils;
import edu.uoc.epcsd.showcatalog.utils.ValueValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2(topic = "PerformanceService")
@Component
public class PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    public List<Performance> getAll() {
        return performanceRepository.findAll();
    }

    public Performance save(String showName, Performance performance) throws InvalidParamError {
        if (!ValueValidator.nonNullOrBlank(showName)) {
            throw new InvalidParamError(true, "Show(name)", "Not null or blank");
        } else {
            Show show = ListUtils.find(getAll(), p -> p.getShow().getName().equals(showName)).getShow();
            if (show == null) {
                throw new InvalidParamError(true, "Show(name)", "Not exists");
            } else {

            }
        }



            if (!ListUtils.any(getAll(), p -> p.getShow().getName().equals(showName))) {
            throw new NotUniqueValueError();
        } else {
            return categoryRepository.saveAndFlush(category);
        }
    }
}

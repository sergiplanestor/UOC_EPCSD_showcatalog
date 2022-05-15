package edu.uoc.epcsd.showcatalog.services;

import edu.uoc.epcsd.showcatalog.errors.exceptions.IdentifierNotFoundError;
import edu.uoc.epcsd.showcatalog.errors.exceptions.InvalidParamError;
import edu.uoc.epcsd.showcatalog.models.db.entities.Show;
import edu.uoc.epcsd.showcatalog.models.db.valueobj.Performance;

import java.util.List;

public interface ShowService {
    List<Show> getAll();
    Show getDetails(Long id) throws IdentifierNotFoundError;
    List<Performance> getShowPerformances(Long id) throws IdentifierNotFoundError;
    List<Show> findByName(boolean isCaseSensitive, boolean allowContains, String name);
    List<Show> findByCategory(String name);
    Show saveShow(Show show) throws InvalidParamError;
    Performance savePerformance(Long showId, Performance performance) throws IdentifierNotFoundError, InvalidParamError;
    void deleteShow(Long id) throws InvalidParamError;
    void deletePerformance(Long showId, String performanceId) throws InvalidParamError, IdentifierNotFoundError;
}

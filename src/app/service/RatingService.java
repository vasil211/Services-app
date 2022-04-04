package app.service;

import app.exeption.NonexistingEntityException;
import app.model.Rating;

import java.util.Collection;

public interface RatingService {
    Collection<Rating> getAll();
    Collection<Rating> getAllDeleted();
    Collection<Rating> getAllForUser(Long id) throws NonexistingEntityException;
    Rating getRatingById(Long id) throws NonexistingEntityException;
    Rating createRating(Rating rating);
    Rating updateRating(Rating rating);
    boolean deleteRatingById(Long id, String explanation) throws NonexistingEntityException;
    float calculateRatingForUser(Long id) throws NonexistingEntityException;
    float calculateRatingForPost(Long id) throws NonexistingEntityException;
    Long countForUser(Long id);

    Collection<Rating> getAllRatingsForPost(long id) throws NonexistingEntityException;

    Collection<Rating> getAllRatingsForUser(long id) throws NonexistingEntityException;

    Collection<Rating> getAllRatingsFromUser(long id) throws NonexistingEntityException;


    boolean markAsModerated(Long id);

    Collection<Rating> getAllUnmoderated();

    Collection<Rating> getAllModerated();


}

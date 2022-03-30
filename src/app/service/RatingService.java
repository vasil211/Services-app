package app.service;

import app.exeption.InvalidEntityDataException;
import app.exeption.InvalidUserLoginExeption;
import app.exeption.NonexistingEntityException;
import app.model.Post;
import app.model.Rating;
import app.model.User;

import java.util.Collection;

public interface RatingService {
    Collection<Rating> getAll();
    Collection<Rating> getAllForUser(Long id) throws NonexistingEntityException;
    Rating getRatingById(Long id) throws NonexistingEntityException;
    Rating createRating(Rating rating);
    Rating updateRating(Rating rating);
    boolean deleteRatingById(Long id) throws NonexistingEntityException;
    float calculateRatingForUser(Long id) throws NonexistingEntityException;
    Long countForUser(Long id);
}

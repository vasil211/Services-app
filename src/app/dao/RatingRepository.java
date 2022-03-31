package app.dao;

import app.model.Category;
import app.model.Rating;

import java.util.Collection;

public interface RatingRepository extends Repository<Long, Rating>{
    float calculateRating(Long id);
    Collection<Rating> findAllForUser(Long id);
    Long countForUser(Long id);

    Collection<Rating> findAllRatingsForPost(long id);

    Collection<Rating> findAllRatingsForUser(long id);

    Collection<Rating> findAllRatingsFromUser(long id);
}

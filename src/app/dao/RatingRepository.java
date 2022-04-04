package app.dao;

import app.model.Rating;

import java.util.Collection;

public interface RatingRepository extends Repository<Long, Rating>{
    float calculateRatingForUser(Long id);
    float calculateRatingForPost(Long id);
    Collection<Rating> findAllForUser(Long id);
    Long countForUser(Long id);

    Collection<Rating> findAllRatingsForPost(long id);

    Collection<Rating> findAllRatingsForUser(long id);

    Collection<Rating> findAllRatingsFromUser(long id);

    Collection<Rating> findAllDeleted();
    boolean deleteByIdEpx(Long id, String explanation);
    boolean markAsModerated(Long id);

    Collection<Rating> getAllUnmoderated();

    Collection<Rating> getAllModerated();


}

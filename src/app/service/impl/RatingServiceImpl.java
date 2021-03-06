package app.service.impl;

import app.dao.RatingRepository;
import app.exeption.ConstraintViolationException;
import app.exeption.NonexistingEntityException;
import app.model.Rating;
import app.service.RatingService;
import app.service.validators.RatingValidation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepo;
    private final RatingValidation ratingValidation;

    public RatingServiceImpl(RatingRepository ratingRepo, RatingValidation ratingValidation) {
        this.ratingRepo = ratingRepo;
        this.ratingValidation = ratingValidation;
    }

    @Override
    public Collection<Rating> getAll() {
        return ratingRepo.findAll();
    }

    @Override
    public Collection<Rating> getAllDeleted() {
        return ratingRepo.findAllDeleted();
    }

    @Override
    public Collection<Rating> getAllForUser(Long id) throws NonexistingEntityException {
        var ratings = ratingRepo.findAllForUser(id);
        if (ratings.isEmpty()) throw new NonexistingEntityException("No ratings for this user");
        return ratings;
    }

    @Override
    public Rating getRatingById(Long id) throws NonexistingEntityException {
        Rating rating = ratingRepo.findById(id);
        if (rating == null) throw new NonexistingEntityException();
        return rating;
    }

    @Override
    public Rating createRating(Rating rating) {
        try {
            rating = ratingValidation.validateRating(rating);
            rating.setCreated(LocalDateTime.now());
            rating.setModified(LocalDateTime.now());
        } catch (ConstraintViolationException ex) {
            var sb = new StringBuilder(ex.getMessage());
            sb.append(", invalid fields:\n");
            var violations = (ex.getFieldViolations());
            sb.append(violations.stream().map(v -> String.format(" - %s.%s [%s] - %s",
                            v.getType().substring(v.getType().lastIndexOf(".") + 1),
                            v.getField(),
                            v.getInvalidValue(),
                            v.getErrorMessage())
                    ).collect(Collectors.joining("\n"))
            );
            System.out.println("\n" + sb.toString());
            return null;
        }
        return ratingRepo.create(rating);
    }

    @Override
    public Rating updateRating(Rating rating) {
        try {
            rating = ratingValidation.validateRating(rating);
            rating.setModified(LocalDateTime.now());
        } catch (ConstraintViolationException ex) {
            var sb = new StringBuilder(ex.getMessage());

                sb.append(", invalid fields:\n");
                var violations = (ex.getFieldViolations());
                sb.append(violations.stream().map(v -> String.format(" - %s.%s [%s] - %s",
                                v.getType().substring(v.getType().lastIndexOf(".") + 1),
                                v.getField(),
                                v.getInvalidValue(),
                                v.getErrorMessage())
                        ).collect(Collectors.joining("\n"))
                );

            System.out.println(sb);
            return null;
        }
        return ratingRepo.update(rating);
    }

    @Override
    public boolean deleteRatingById(Long id, String explanation) throws NonexistingEntityException {
        if (!ratingRepo.deleteByIdEpx(id, explanation)) {
            throw new NonexistingEntityException("This rating does not exists!");
        }
        return true;
    }

    @Override
    public float calculateRatingForUser(Long id) throws NonexistingEntityException {
        float rating = ratingRepo.calculateRatingForUser(id);
        if (rating == 0.0) throw new NonexistingEntityException("Did not find rating for this user");
        return rating;
    }

    @Override
    public float calculateRatingForPost(Long id) throws NonexistingEntityException {
        float rating = ratingRepo.calculateRatingForPost(id);
        if (rating == 0.0) throw new NonexistingEntityException("This post does not have ratings");
        return rating;
    }

    @Override
    public Long countForUser(Long id) {
        return ratingRepo.countForUser(id);
    }

    @Override
    public Collection<Rating> getAllRatingsForPost(long id) throws NonexistingEntityException {
        var ratings = ratingRepo.findAllRatingsForPost(id);
        if (ratings.isEmpty()) throw new NonexistingEntityException("No ratings for this post");
        return ratings;
    }

    @Override
    public Collection<Rating> getAllRatingsForUser(long id) throws NonexistingEntityException{
        var ratings = ratingRepo.findAllRatingsForUser(id);
        if (ratings.isEmpty()) throw new NonexistingEntityException("No ratings for this user");
        return ratings;
    }

    @Override
    public Collection<Rating> getAllRatingsFromUser(long id) throws NonexistingEntityException {
        var ratings = ratingRepo.findAllRatingsFromUser(id);
        if (ratings.isEmpty()) throw new NonexistingEntityException("No ratings for this user");
        return ratings;
    }

    @Override
    public boolean markAsModerated(Long id) {
        return ratingRepo.markAsModerated(id);
    }

    @Override
    public Collection<Rating> getAllUnmoderated() {
        return ratingRepo.getAllUnmoderated();
    }

    @Override
    public Collection<Rating> getAllModerated() {
        return ratingRepo.getAllModerated();
    }

    @Override
    public Rating getRatingByPostIdFromUser(Long postId, Long userId) {
        return ratingRepo.getRatingByPostIdFromUser(postId, userId);
    }
}

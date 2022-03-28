package app.service.validators;

import app.dao.RatingRepository;
import app.dao.UserRepository;
import app.exeption.ConstraintViolation;
import app.exeption.ConstraintViolationException;
import app.model.Post;
import app.model.Rating;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RatingValidation {
    private final RatingRepository ratingRepo;

    public RatingValidation(RatingRepository ratingRepo) {
        this.ratingRepo = ratingRepo;
    }
    public Rating validateRating(Rating rating) throws ConstraintViolationException {
        List<ConstraintViolation> violations = new ArrayList<>();
        if (rating.getRating() < 1 || rating.getRating() > 5) {
            violations.add(
                    new ConstraintViolation(rating.getClass().getName(), " Rating ", rating.getRating(),
                            " Rating should be between 1 and 5."));
        }
        if(rating.getComment().length() > 250){
            violations.add(
                    new ConstraintViolation(rating.getClass().getName()," Comment\n ", rating.getComment(),
                            "\n - Max character length for comments is 250."));
        }
        if(violations.size() > 0) {
            throw new ConstraintViolationException("Error: invalid rating field!", violations);
        }
        return rating;
    }
}

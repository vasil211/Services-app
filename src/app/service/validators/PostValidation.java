package app.service.validators;

import app.exeption.ConstraintViolation;
import app.exeption.ConstraintViolationException;
import app.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostValidation {
    public Post validatePost(Post post) throws ConstraintViolationException{
        List<ConstraintViolation> violations = new ArrayList<>();
        if(post.getName().length() >50 || post.getName().length() <2){
            violations.add(
                    new ConstraintViolation(post.getClass().getName(), "title", post.getName(),
                            "Name of post should be between 2 and 50 characters"));
        }
        if(post.getInfo().length() > 250 || post.getInfo().length() <2){
            violations.add(
                    new ConstraintViolation(post.getClass().getName(), "Info", post.getInfo(),
                            "Information of post should be between 2 and 250 characters"));
        }
        if(violations.size() > 0) {
            throw new ConstraintViolationException("Invalid post field", violations);
        }
        return post;
    }
}

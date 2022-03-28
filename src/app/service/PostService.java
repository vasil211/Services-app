package app.service;

import app.exeption.ConstraintViolationException;
import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.Post;
import app.model.User;

import java.util.Collection;

public interface PostService {
    Collection<Post> getAll();
    Collection<Post> getAllPostsByUser(Long id) throws NonexistingEntityException;
    Collection<Post> getAllPostsByCategory(Long id) throws NonexistingEntityException;
    Post getPostById(Long id) throws NonexistingEntityException;
    Post createPost(Post post);
    Post updatePost(Post post);
    boolean deletePostById(Long id) throws NonexistingEntityException;
    Long countFromCategory(Long id) throws NonexistingEntityException;
    Long countFromUser(Long id) throws NonexistingEntityException;
    float calculateRatingForPost(Long id);
    Collection<Post> getPostsByCategoryName(String name) throws NonexistingEntityException;
}

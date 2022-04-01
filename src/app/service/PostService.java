package app.service;

import app.exeption.NonexistingEntityException;
import app.model.Post;

import java.util.Collection;

public interface PostService {
    Collection<Post> getAll();
    Collection<Post> getAllDeleted();
    Collection<Post> getAllPostsByUser(Long id) throws NonexistingEntityException;
    Collection<Post> getAllPostsByCategory(Long id) throws NonexistingEntityException;
    Post getPostById(Long id) throws NonexistingEntityException;
    Post createPost(Post post);
    Post updatePost(Post post);
    boolean deletePostById(Long id, String explanation) throws NonexistingEntityException;
    Long countFromCategory(Long id) throws NonexistingEntityException;
    Long countFromUser(Long id) throws NonexistingEntityException;
    float calculateRatingForPost(Long id);
    float calculateRatingForUser(Long id);
    Collection<Post> getPostsByCategoryName(String name) throws NonexistingEntityException;

    Collection<Post> getAllUnmoderated();

    boolean markAsModerated(Long id);

    Collection<Post> getAllModerated();
}

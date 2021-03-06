package app.dao;

import app.model.Post;

import java.util.Collection;

public interface PostRepository extends Repository<Long, Post>{
    Collection<Post> findByCategory(Long id);
    Collection<Post> getAllPostsByUser(Long id);
    public Long countPostsByCategory(Long id);
    public Long countPostsByUser(Long id);
    public float calculateRatingForPost(Long id);
    Collection<Post> getAllPostsByCategory(Long id);
    float calculateRatingForUser(Long id);
    Collection<Post> findAllDeleted();

    Collection<Post> getAllUnmoderated();
    boolean deleteByIdExpl(Long id, String reason);
    boolean markAsModerated(Long id);

    Collection<Post> getAllModerated();
}

package app.service.impl;

import app.dao.CategoryRepository;
import app.dao.PostRepository;
import app.exeption.ConstraintViolationException;
import app.exeption.NonexistingEntityException;
import app.model.Post;
import app.service.PostService;
import app.service.validators.PostValidation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final CategoryRepository categoryRepo;
    private final PostValidation postValidator;

    public PostServiceImpl(PostRepository postRepo, CategoryRepository categoryRepo, PostValidation postValidator) {
        this.postRepo = postRepo;
        this.postValidator = postValidator;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Collection<Post> getAll() {
        return postRepo.findAll();
    }

    @Override
    public Collection<Post> getAllPostsByUser(Long id) throws NonexistingEntityException {
        var posts = postRepo.getAllPostsByUser(id);
        if (posts == null) {
            throw new NonexistingEntityException("No posts found for this user");
        }
        return posts;
    }

    @Override
    public Collection<Post> getAllPostsByCategory(Long id) throws NonexistingEntityException {
        var posts = postRepo.findByCategory(id);
        if (posts == null) {
            throw new NonexistingEntityException("No posts found in this category");
        }
        return posts;
    }

    //Method to get Post by id
    @Override
    public Post getPostById(Long id) throws NonexistingEntityException {
        var post = postRepo.findById(id);
        if (post == null) {
            throw new NonexistingEntityException("Post with id " + id + " does not exist");
        }
        return post;
    }

    //Method to create Post
    @Override
    public Post createPost(Post post) {
        try {
            post = postValidator.validatePost(post);
            post.setModified(LocalDateTime.now());
            post.setCreated(LocalDateTime.now());
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
        return postRepo.create(post);
    }

    //Method to update Post
    @Override
    public Post updatePost(Post post) {
        try {
            post = postValidator.validatePost(post);
            post.setModified(LocalDateTime.now());
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
        return postRepo.update(post);
    }

    //Method to delete Post
    @Override
    public boolean deletePostById(Long id) throws NonexistingEntityException {
        var postToDelete = postRepo.findById(id);
        if (postToDelete == null) {
            throw new NonexistingEntityException("Post with id " + id + " does not exist");
        }
        postRepo.deleteById(id);
        return true;
    }

    //Method to count all posts from category
    @Override
    public Long countFromCategory(Long id) throws NonexistingEntityException {
        var posts = postRepo.countPostsByCategory(id);
        if (posts == null) {
            throw new NonexistingEntityException("This category has no posts");
        }
        return posts;
    }

    @Override
    public Long countFromUser(Long id) throws NonexistingEntityException {
        var posts = postRepo.countPostsByUser(id);
        if (posts == null) {
            throw new NonexistingEntityException("This user has no posts");
        }
        return posts;

    }

    @Override
    public float calculateRatingForPost(Long id) {
        return postRepo.calculateRatingForPost(id);
    }

    @Override
    public float calculateRatingForUser(Long id) {
        return postRepo.calculateRatingForUser(id);
    }

    @Override
    public Collection<Post> getPostsByCategoryName(String name) throws NonexistingEntityException {
        var id = categoryRepo.findIdByName(name);
        if (id == null) {
            throw new NonexistingEntityException("Such category does not exist");
        }
        var posts = postRepo.getAllPostsByCategory(id);
        if (posts == null) {
            throw new NonexistingEntityException("No posts found in this category");
        }
        return posts;
    }
}

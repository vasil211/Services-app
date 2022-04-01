package app.service.impl;

import app.dao.CategoryRepository;
import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.Category;
import app.service.CategoryService;

import java.util.Collection;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepo = categoryRepository;
    }

    @Override
    public Collection<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategoryById(Long id) throws NonexistingEntityException {
        var category = categoryRepo.findById(id);
        if (category == null) {
            throw new NonexistingEntityException("Category with id: " + id + " does not exist");
        }
        return category;
    }

    @Override
    public Category getCategoryByName(String name) throws NonexistingEntityException {
        var category = categoryRepo.findByName(name);
        if (category == null) {
            throw new NonexistingEntityException("Category with name: " + name + " does not exist");
        }
        return category;
    }

    @Override
    public Category addCategory(Category category) throws InvalidEntityDataException {
        if (category.getName() == null) {
            throw new InvalidEntityDataException("Category name cannot be empty");
        }
        return categoryRepo.create(category);
    }

    @Override
    public Category updateCategory(Category category) throws InvalidEntityDataException {
        if (category.getName() == null ) {
            throw new InvalidEntityDataException("Category name cannot be empty");
        }
        return categoryRepo.update(category);
    }

    @Override
    public void deleteCategoryById(Long id) throws NonexistingEntityException {
        if(categoryRepo.deleteById(id)){
            throw new NonexistingEntityException("Category with id: " + id + " does not exist");
        }
    }

    @Override
    public long count() {
        return categoryRepo.count();
    }
}

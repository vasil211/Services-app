package app.service;

import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.Category;

import java.util.Collection;

public interface CategoryService {
    Collection<Category> getAllCategories();
    Category getCategoryById(Long id) throws NonexistingEntityException;
    Category addCategory(Category recipe) throws InvalidEntityDataException;
    Category updateCategory(Category recipe) throws NonexistingEntityException, InvalidEntityDataException;
    void deleteCategoryById();
    long count();

}

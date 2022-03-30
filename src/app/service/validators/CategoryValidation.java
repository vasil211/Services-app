package app.service.validators;

import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.service.CategoryService;

public class CategoryValidation {

    public final CategoryService categoryService;

    public CategoryValidation(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public boolean isValidCategory(String name) throws InvalidEntityDataException {
        try {
            categoryService.getCategoryByName(name);
            throw new InvalidEntityDataException("Category with name " + name + " already exists");
        } catch (NonexistingEntityException e) {
            return true;
        }

    }
}

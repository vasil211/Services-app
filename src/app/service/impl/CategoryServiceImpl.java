package app.service.impl;

import app.dao.CategoryRepository;
import app.exeption.InvalidEntityDataException;
import app.exeption.NonexistingEntityException;
import app.model.Category;
import app.service.CategoryService;

import java.util.Collection;
import java.util.Scanner;

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
    public void deleteCategoryById() {
        Scanner sc = new Scanner(System.in);
        long id = 0;
        do {
            System.out.println("Enter ID of category to delete: ");
            var idStr = sc.nextLine();
            try {
                id = Integer.parseInt(idStr);
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Error: Invalid choice. Please enter a valid number");
            }
        } while (true);
        System.out.println("Deleting: ");
        System.out.println(categoryRepo.deleteById(id));
    }

    @Override
    public long count() {
        return categoryRepo.count();
    }
}

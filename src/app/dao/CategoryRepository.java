package app.dao;

import app.model.Category;
import app.model.User;

import java.util.Optional;

public interface CategoryRepository extends Repository<Long, Category>{
    Long findIdByName(String name);
    Category findByName(String name);

}

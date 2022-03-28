package app.dao;

import app.model.Category;
import app.model.User;

public interface CategoryRepository extends Repository<Long, Category>{
    Long findIdByName(String name);

}

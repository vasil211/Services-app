package app.dao.impl;

import app.dao.CategoryRepository;
import app.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class CategoryRepositoryImpl implements CategoryRepository {

    private final Connection connection;

    public CategoryRepositoryImpl() {
        connection = app.util.Database.getConnection();
    }

    @Override
    public Collection<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM services.categories");
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Method to find category by name
    @Override
    public Category findByName(String name) {
        Category category = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM services.categories WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    // Method to find category by id
    @Override
    public Category findById(Long id) {
        Category category = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM services.categories WHERE id = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public Category create(Category entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into categories(name) values (?)");
            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category update(Category entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update categories set name = ? where id= ?");
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getId());
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Category category = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM services.categories WHERE id = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (category != null) {
            return true;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from posts where category_id =?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from categories where id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public Long count() {
        long count = 0L;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select count(*) from categories");
            rs.next();
            count = rs.getLong(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }
    }

    // Method to find category id by category name
    @Override
    public Long findIdByName(String name) {
        Long id = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select id from categories where name=?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                id = rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    // method to find category name by category id
}

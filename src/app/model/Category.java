package app.model;

import app.dao.Identifiable;

import java.util.StringJoiner;

public class Category implements Identifiable<Long> {
    private Long id;
    private String name;

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "", " ")
                .add("id: " + id)
                .add("name: '" + name + "'")
                .toString();
    }
}

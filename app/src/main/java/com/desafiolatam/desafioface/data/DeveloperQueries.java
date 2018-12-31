package com.desafiolatam.desafioface.data;

import com.desafiolatam.desafioface.models.Developer;

import java.util.List;

public class DeveloperQueries {

    public List<Developer> all() {

        return Developer.listAll(Developer.class);
    }

    public List<Developer> findByName(String name) {

        String query = "name LIKE ?";
        String argument = "%"+name+"%";

        return Developer.find(Developer.class, query, argument);
    }
}

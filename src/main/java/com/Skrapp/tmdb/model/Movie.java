package com.Skrapp.tmdb.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String director;
    @ElementCollection
    private List<String> actors = new ArrayList<>();

}


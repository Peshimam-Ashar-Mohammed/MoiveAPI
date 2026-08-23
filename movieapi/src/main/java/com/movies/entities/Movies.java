package com.movies.entities;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;
    
    @Column(nullable=false,length=200)
    @NotBlank(message="Movie title is required")
    private String movieTitle;

    @Column(nullable=false)
    @NotBlank(message="Director is required")
    private String director;

    @Column(nullable=false)
    @NotBlank(message="Genre is required")
    private String genre;

    @ElementCollection
    @CollectionTable(name="movie_cast")
    private Set<String> cast;

    @Column(nullable=false)
    private Integer releaseYear;

    @Column(nullable=false)
    @NotBlank(message="Poster is required")
    private String poster;

}

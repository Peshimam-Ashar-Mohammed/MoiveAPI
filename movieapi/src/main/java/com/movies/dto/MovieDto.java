package com.movies.dto;

import java.util.Set;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;
    
    @NotBlank(message="Movie title is required")
    private String movieTitle;

    @NotBlank(message="Director is required")
    private String director;

    @NotBlank(message="Genre is required")
    private String genre;

    private Set<String> cast;

    @NotNull(message="Release year is required")
    private Integer releaseYear;

    @NotBlank(message="Poster is required")
    private String poster;

    @NotBlank(message="Poster URL is required")
    private String posterUrl;




}

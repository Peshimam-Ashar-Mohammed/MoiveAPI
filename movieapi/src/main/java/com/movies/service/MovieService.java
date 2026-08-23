package com.movies.service;

import java.io.IOException;
import com.movies.dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface MovieService {

    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    MovieDto getMoive(Integer movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException;
    String deleteMovie(Integer movieId) throws IOException;

}

package com.movies.controller;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.dto.MovieDto;
import com.movies.service.MovieService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping({"/api/v1/movies", "/api/v1/movie"})
public class MoiveController {

    private final MovieService movieService;

    public MoiveController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping({"", "/add-movie"})
    public ResponseEntity<MovieDto> addMoiveHandler(@RequestPart MultipartFile file,@RequestPart String movieDto) throws IOException, JsonProcessingException {

        MovieDto dto= convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(dto,file),HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId) {
        return new ResponseEntity<>(movieService.getMoive(movieId),HttpStatus.OK);
    }

    @GetMapping({"/all"})
    public ResponseEntity<java.util.List<MovieDto>> getAllMoviesHandler() {
        return new ResponseEntity<>(movieService.getAllMovies(),HttpStatus.OK);
    }

    private MovieDto convertToMovieDto(String movieDto)throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDto, MovieDto.class);

    }


}

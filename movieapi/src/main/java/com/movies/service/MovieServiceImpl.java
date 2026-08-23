package com.movies.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movies.Repositories.MovieRepository;
import com.movies.dto.MovieDto;
import com.movies.entities.Movies;
import com.movies.service.FileService;
import com.movies.service.MovieService;
import com.movies.service.MovieServiceImpl;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {

        //Upload the file
        if(Files.exists(Paths.get(path+File.separator+file.getOriginalFilename()))){
            throw new RuntimeException("File already exists please enter antoher FIle name");
        }
        String uploadedFileName=fileService.uploadFile(path, file);

        //Set the value of feild 'poster' as fileName
        movieDto.setPoster(uploadedFileName);

        //Map Dto to moive object
        Movies movie = new Movies(
            null,
            movieDto.getMovieTitle(),
            movieDto.getDirector(),
            movieDto.getGenre(),
            movieDto.getCast(),
            movieDto.getReleaseYear(),
            movieDto.getPoster()
        );

        //Save the moive object
        Movies savedMovie = movieRepository.save(movie);
        
        //GEenerate the url 
        String posterUrl = baseUrl+"/file/"+uploadedFileName;
        
        //Map the movie object ot Dto object and return 
        MovieDto response = new MovieDto(
            savedMovie.getMovieId(),
            savedMovie.getMovieTitle(),
            savedMovie.getDirector(),
            savedMovie.getGenre(),
            savedMovie.getCast(),
            savedMovie.getReleaseYear(),
            savedMovie.getPoster(),
            posterUrl
        );

        return response;
    }

    @Override
    public MovieDto getMoive(Integer movieId) {

        //Check Data in DB if exists and if yes fetch it
        Movies movie = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("Movie Not Found"));

        //Generate the url of poster
        String posterUrl = baseUrl+"/file/"+movie.getPoster();


        //Map the movie object to Dto object and return it
        MovieDto response = new MovieDto(
            movie.getMovieId(),
            movie.getMovieTitle(),
            movie.getDirector(),
            movie.getGenre(),
            movie.getCast(),
            movie.getReleaseYear(),
            movie.getPoster(),
            posterUrl
        );

        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {

        //Fetch all data from DB
        List<Movies> moviesList = movieRepository.findAll();
        List<MovieDto> movieDtos= new ArrayList<>();

        //Iterate through the list and generate poster url for all of them
        for(Movies movie:moviesList){
            String posterUrl = baseUrl+"/file/"+movie.getPoster();

            //Map the movie object to Dto object and add it to the list
            MovieDto movieDto = new MovieDto(
            movie.getMovieId(),
            movie.getMovieTitle(),
            movie.getDirector(),
            movie.getGenre(),
            movie.getCast(),
            movie.getReleaseYear(),
            movie.getPoster(),
            posterUrl
        );

        movieDtos.add(movieDto);


        }

        return movieDtos;
    }


    @Override
    public MovieDto updateMovie(Integer movieId,MovieDto movieDto, MultipartFile file)throws IOException{

        //Check Data in DB if exists and if yes fetch it
        Movies mv = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("Movie Not Found"));

        //if FIle is Null if File is not null then delete old file and upload new file
        String fileName= mv.getPoster();
        if(file!=null){
            Files.deleteIfExists(Paths.get(path+File.separator+fileName));
            fileName=fileService.uploadFile(path, file);
        }

        //Set movie Dto poster value
        movieDto.setPoster(fileName);

        //Map it to a movie object
        Movies movie = new Movies(
            mv.getMovieId(),
            movieDto.getMovieTitle(),
            movieDto.getDirector(),
            movieDto.getGenre(),
            movieDto.getCast(),
            movieDto.getReleaseYear(),
            movieDto.getPoster()
        );

        //save into Databse
        Movies updatedMovie = movieRepository.save(movie);

        //Generate the poster url
        String posterUrl = baseUrl+"/file/"+fileName;

        //Map to movie Dto and retunr it
        MovieDto response = new MovieDto(
            updatedMovie.getMovieId(),
            updatedMovie.getMovieTitle(),
            updatedMovie.getDirector(),
            updatedMovie.getGenre(),
            updatedMovie.getCast(),
            updatedMovie.getReleaseYear(),
            updatedMovie.getPoster(),
            posterUrl
        );

        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {

        //Check if movie object exists in DB 
        Movies mv = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("Movie Not Found"));
        Integer id=mv.getMovieId();

        //Delete files wiht this id
        Files.deleteIfExists(Paths.get(path+File.separator+mv.getPoster()));

        //Delete object
        movieRepository.delete(mv);
        
        return "Movie with id "+id+" deleted successfully";
    }


}

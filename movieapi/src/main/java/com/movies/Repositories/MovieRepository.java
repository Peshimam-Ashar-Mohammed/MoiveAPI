package com.movies.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movies.entities.Movies;


public interface MovieRepository extends JpaRepository<Movies,Integer>{

}

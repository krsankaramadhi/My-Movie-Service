package com.Skrapp.tmdb.Service;

import com.Skrapp.tmdb.Repo.MovieRepository;

import com.Skrapp.tmdb.exception.InvalidDataException;
import com.Skrapp.tmdb.exception.NotFoundException;
import com.Skrapp.tmdb.model.Movie;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    //CRUD

    public Movie create(Movie movie){
           if (movie == null) {
               throw new InvalidDataException("Invalid movie: null");
           }
        return movieRepository.save(movie);
    }

    public Movie read(Long id) {

        return movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found with Id:" +id));

    }
    public void update(Long id, Movie update){
        if (update == null || id == null){
            throw  new InvalidDataException("Invalid movie: null");
        }
        // check if exists?
        if(movieRepository.existsById(id)){
            Movie movie = movieRepository.getReferenceById(id);
            movie.setName(update.getName());
            movie.setDirector(update.getDirector());
            movie.setActors(update.getActors());
            movieRepository.save(movie);
        } else
            throw new NotFoundException("Movie not found with Id:" +id);
    }

    public void delete(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            }
             else
                 throw new NotFoundException("Cannot delete: Movie with ID " + id + " not found.");
         }

}

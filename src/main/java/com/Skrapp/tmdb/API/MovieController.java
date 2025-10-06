package com.Skrapp.tmdb.API;


import com.Skrapp.tmdb.Service.MovieService;
import com.Skrapp.tmdb.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@Slf4j

public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")

    public ResponseEntity<Movie> getMovie(@PathVariable Long id){
        Movie movie = movieService.read(id);
        log.info(" Returned the required movie for id: {}", id );
        return ResponseEntity.ok(movie);
    }
    @PostMapping

    public ResponseEntity<Movie> createdMovie(@RequestBody Movie movie){

        Movie createdMovie = movieService.create(movie);
        log.info(" Created the required movie with id: {}", createdMovie.getId() );

        return ResponseEntity.ok(createdMovie);
    }
    @PutMapping("/{id}")

    public void updateMovie(@PathVariable Long id, @RequestBody Movie movie){

        movieService.update(id,movie);
        log.info(" Updated the  movie for id: {}", id );
    }
    @DeleteMapping("/{id}")

    public void deleteMovie(@PathVariable Long id){

        movieService.delete(id);
        log.info(" Deleted the  movie for id: {}", id );

    }
}

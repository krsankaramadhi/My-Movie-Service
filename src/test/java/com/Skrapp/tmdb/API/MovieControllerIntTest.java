package com.Skrapp.tmdb.API;

import com.Skrapp.tmdb.Repo.MovieRepository;

import com.Skrapp.tmdb.model.Movie;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.CoreMatchers.notNullValue;



import java.util.List;


@SpringBootTest

@AutoConfigureMockMvc



class MovieControllerIntTest {



    @Autowired

    MockMvc mockMvc;



    @Autowired

    ObjectMapper objectMapper;

    @Autowired

    MovieRepository movieRepository;

    @BeforeEach

    void cleanUp(){

        movieRepository.deleteAllInBatch();

    }



    @Test

    void givenMovie_WhenCreateMovie_thenReturnSavedMovie() throws Exception{



//Given



        Movie movie = new Movie();

        movie.setName("Movie1");

        movie.setDirector("Director1");

        movie.setActors(List.of("Actor1", "Actor2", "Actor3"));



// when create movie

        var response = mockMvc.perform(post("/movies")

                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsString(movie)));



//then verify saved movie

        response.andDo(print())

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id", is(notNullValue())))

                .andExpect(jsonPath("$.name", is(movie.getName())))

                .andExpect(jsonPath("$.director", is(movie.getDirector())))

                .andExpect(jsonPath("$.actors", is(movie.getActors())));



    }



    @Test



    void givenMovieId_WhenFetchMovie_thenReturnMovie() throws Exception {



//Given

        Movie movie = new Movie();

        movie.setName("Movie1");

        movie.setDirector("Director1");

        movie.setActors(List.of("Actor1", "Actor2", "Actor3"));

// when

        Movie savedMovie = movieRepository.save(movie);



//then

        var response = mockMvc.perform(get("/movies/" + savedMovie.getId()));



//then verify saved movie

        response.andDo(print())

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id", is(savedMovie.getId().intValue())))

                .andExpect(jsonPath("$.name", is(movie.getName())))

                .andExpect(jsonPath("$.director", is(movie.getDirector())))

                .andExpect(jsonPath("$.actors", is(movie.getActors())));



    }

    @SneakyThrows
    @Test
    void givenSavedMovie_WhenUpdateMovie_thenMovieUpdatedInDb(){

        //Given

        Movie movie = new Movie();
        movie.setName("Movie1");
        movie.setDirector("Director1");
        movie.setActors(List.of("Actor1", "Actor2", "Actor3"));

// when

        Movie savedMovie = movieRepository.save(movie);
        Long id = savedMovie.getId();

        //Update

        movie.setActors(List.of("Actor1", "Actor2", "Actor3", "Actor4"));

        var response = mockMvc.perform(put("/movies/" + id)

                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsString(movie)));

        //Verify update movie
        response.andDo(print())

                .andExpect(status().isOk());

        var fetchResponse = mockMvc.perform(get("/movies/" + id));


                fetchResponse.andDo(print())

                        .andExpect(status().isOk())

                        .andExpect(jsonPath("$.id", is(savedMovie.getId().intValue())))

                        .andExpect(jsonPath("$.name", is(movie.getName())))

                        .andExpect(jsonPath("$.director", is(movie.getDirector())))

                        .andExpect(jsonPath("$.actors", is(movie.getActors())));


    }
@Test
    void givenMovie_WhenDeleteRequest_ThenMovieRemovedFromDb() throws Exception {
    //Given
    Movie movie = new Movie();
    movie.setName("Movie1");
    movie.setDirector("Director1");
    movie.setActors(List.of("Actor1", "Actor2", "Actor3"));

    Movie savedMovie = movieRepository.save(movie);
    Long id = savedMovie.getId();

    //Then
    mockMvc.perform(delete("/movies/" + id))

            .andDo(print())
            .andExpect(status().isOk());
    assertFalse(movieRepository.findById(id).isPresent());

    }

}
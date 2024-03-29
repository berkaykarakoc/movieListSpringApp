package com.movielist.movieApp.services;

import com.movielist.movieApp.models.Movie;
import com.movielist.movieApp.models.MovieList;
import com.movielist.movieApp.repositories.MovieListRepository;
import com.movielist.movieApp.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private MovieListRepository movieListRepository;

    public MovieServiceImpl(MovieRepository movieRepository, MovieListRepository movieListRepository) {
        this.movieRepository = movieRepository;
        this.movieListRepository = movieListRepository;
    }

    @Override
    public Movie findByMovieListIdAndMovieId(Long movieListId, Long movieId) {
        Optional<MovieList> movieListOptional = movieListRepository.findById(movieListId);

        if (!movieListOptional.isPresent()){

        }

        MovieList movieList = movieListOptional.get();

        Optional<Movie> movieOptional = movieList.getMovies().stream()
                .filter(movie -> movie.getId().equals(movieId))
                .findFirst();

        if(!movieOptional.isPresent()){

        }

        return movieOptional.get();
    }

    @Override
    public void deleteById(Long l) {
        movieRepository.deleteById(l);
    }

    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
        movieListRepository.save(movie.getMovieList());
    }

    @Override
    public void deleteById(Long movieListId, Long movieId) {


        Optional<MovieList> movieListOptional = movieListRepository.findById(movieListId);

        if(movieListOptional.isPresent()){
            MovieList movieList = movieListOptional.get();

            Optional<Movie> movieOptional = movieList
                    .getMovies()
                    .stream()
                    .filter(movie -> movie.getId().equals(movieId))
                    .findFirst();

            if(movieOptional.isPresent()){
                Movie movieToDelete = movieOptional.get();
                movieToDelete.setMovieList(null);
                movieList.getMovies().remove(movieOptional.get());
                movieListRepository.save(movieList);
            }
        }
    }
}

package apipackage;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
class MovieService {
    private static List<Movie> movies = new ArrayList<>();

    List<Movie> GetMovies(){
        return movies;
    }

    static int GetNextId(){
        if (movies == null) return 0;
        return movies.stream()
                .map(Movie::getId)
                .max(Integer::compare)
                .orElse(0) + 1;
    }

    Movie GetMovie(int id){
        return movies.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    List<Movie> GetMoviesByTitle(String search, List<Movie> movies){
        return movies.stream()
                .filter(x -> x.getTitle().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
    }

    List<Movie> GetMoviesByYear(int age, List<Movie> movies){
        return movies.stream()
                .filter(x -> x.getYear() == age)
                .collect(Collectors.toList());
    }

    List<Movie> LimitMovies(int limit, List<Movie> movies){
        return movies.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    List<Movie> OrderMovies(String orderBy, List<Movie> movies){
        switch(orderBy){
            case "alpha":
                movies.sort(Movie::compareTo);
                break;
            case "alphadesc":
                movies.sort(Movie::compareTo);
                Collections.reverse(movies);
                break;
            case "year":
                movies.sort(Comparator.comparingInt(Movie::getYear));
                break;
            case "yeardesc":
                movies.sort(Comparator.comparingInt(Movie::getYear).reversed());
                break;
        }
        return movies;
    }

    static void AddMovie(Movie model){
        movies.add(model);
    }

    void DeleteMovie(Movie model){
        movies.remove(model);
    }
}

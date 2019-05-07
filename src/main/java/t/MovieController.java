package t;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


@RequestMapping(value="/api/movies")
@RestController()
public class MovieController {
    private MovieService service;
    public MovieController(MovieService service){
        this.service = service;
    }
    Predicate<Movie> predicate = m -> m.getYear() > 0 && !StringUtils.isEmpty(m.getTitle());

    @RequestMapping(method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> GetAllMovies(@RequestParam Map<String,String> requestParams) {
        Integer age = tryParse(requestParams.get("year"));
        Integer limit = tryParse(requestParams.get("limit"));
        String search = requestParams.get("search");
        String orderBy = requestParams.get("orderby");

        List<Movie> movies = service.GetMovies();
        if (age != null) {
            movies = service.GetMoviesByYear(age, movies);
        }
        if (!StringUtils.isEmpty(search)){
            movies = service.GetMoviesByTitle(search, movies);
        }
        if (limit != null && limit > 0){
            movies = service.LimitMovies(limit, movies);
        }
        if (!StringUtils.isEmpty(orderBy)){
            movies = service.OrderMovies(orderBy, movies);
        }
        return movies;
    }

    @RequestMapping(value="/{movieID}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Movie GetMovie(@PathVariable(value="movieID") int id) {
        return service.GetMovie(id);
    }

    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Movie AddMovie(@RequestBody Movie movie) throws Exception {
        if (!predicate.test(movie)) throw new Exception("Bad call");
        Movie newMovie = new Movie(movie.getTitle(), movie.getYear(), movie.getImdbUrl());
        MovieService.AddMovie(newMovie);
        return newMovie;
    }

    @RequestMapping(value="/{movieID}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void DeleteMovie(@PathVariable(value="movieID") int id) {
        service.DeleteMovie(service.GetMovie(id));
    }

    @RequestMapping(value="/{movieID}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Movie UpdateMovie(@PathVariable(value="movieID") String id, @RequestBody Movie movie) throws Exception {
        if (!predicate.test(movie)) throw new Exception("Bad call");
        Movie oldMovie = service.GetMovie(Integer.parseInt(id));
        oldMovie.setYear(movie.getYear());
        oldMovie.setTitle(movie.getTitle());
        oldMovie.setImdbUrl(movie.getImdbUrl());
        return oldMovie;
    }

    private static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

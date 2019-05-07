package apipackage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class App {
    public static void main( String[] args) {
        MovieService.AddMovie(new Movie("Avengers: Endgame", 2019, "https://www.imdb.com/title/tt4154796"));
        MovieService.AddMovie(new Movie("The Shawshank Redemption", 1994, "https://www.imdb.com/title/tt0111161"));
        MovieService.AddMovie(new Movie("Pulp Fiction", 1994, "https://www.imdb.com/title/tt0110912"));
        MovieService.AddMovie(new Movie("Inception", 2010, "https://www.imdb.com/title/tt1375666"));
        MovieService.AddMovie(new Movie("The Good, the Bad and the Ugly", 1966, "https://www.imdb.com/title/tt0060196"));
        SpringApplication.run(App.class, args);
    }
}

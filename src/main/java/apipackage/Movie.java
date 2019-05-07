package apipackage;


public class Movie implements Comparable<Movie>{
    private int id;
    private String title;
    private int year;
    private String imdbUrl;


    Movie(String title, int year, String imdbUrl){
        id = MovieService.GetNextId();
        this.title = title;
        this.year = year;
        this.imdbUrl = imdbUrl;
    }

    public Movie(){

    }

    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setYear(int year){
        this.year = year;
    }
    public int getYear(){
        return year;
    }
    public void setImdbUrl(String imdbUrl){this.imdbUrl = imdbUrl;}
    public String getImdbUrl() {return imdbUrl;}

    @Override
    public int compareTo(Movie o) {
        return title.compareTo(o.getTitle());
    }
}

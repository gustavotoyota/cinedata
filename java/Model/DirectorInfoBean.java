package Model;

import java.util.List;

public class DirectorInfoBean {
    private int id;
    private String name;
    private int numMovies;
    private List<String> genres;
    private List<Integer> genreMovies;    

    public DirectorInfoBean(int id) {
        this.id = id;
        this.name = "";
        this.numMovies = 0;
        this.genres = null;
        this.genreMovies = null;        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumMovies() {
        return numMovies;
    }

    public void setNumMovies(int numMovies) {
        this.numMovies = numMovies;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Integer> getGenreMovies() {
        return genreMovies;
    }

    public void setGenreMovies(List<Integer> genreMovies) {
        this.genreMovies = genreMovies;
    }
    
    
    
    
}

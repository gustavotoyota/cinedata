public class DirectorResult {
    private int id;
    private String name;
    private int numMovies;
    private int numGenres;
    private String genres;

    public DirectorResult(int id, String genres, int numGenres) {
        this.id = id;
        this.name = "";      
        this.numMovies = 0;
        this.numGenres = numGenres;
        this.genres = genres;
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

    public int getNumGenres() {
        return numGenres;
    }

    public void setNumGenres(int numGenres) {
        this.numGenres = numGenres;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
    
    
}

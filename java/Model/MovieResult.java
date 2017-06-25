public class MovieResult {
    private int id;
    private String title;
    private String director;
    private String language;
        
    public MovieResult(int id, String title) {
        this.id = id;
        this.title = title;
        this.director = "";
        this.language = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    
}

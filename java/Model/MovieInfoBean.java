package Model;

import java.util.List;

public class MovieInfoBean {
    private int id;
    private String title;
    private String year;
    private String languages;
    private String genres;
    private List<String> directorNames;
    private List<String> directorDescs;
    private List<String> actorNames;
    private List<String> actorDescs;
    

    public MovieInfoBean(int id) {
        this.id = id;
        this.title = "";
        this.year = "";
        this.languages = "";
        this.genres = "";
        this.directorNames = null;
        this.directorDescs = null;
        this.actorNames = null;
        this.actorDescs = null;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public List<String> getDirectorNames() {
        return directorNames;
    }

    public void setDirectorNames(List<String> directorNames) {
        this.directorNames = directorNames;
    }

    public List<String> getDirectorDescs() {
        return directorDescs;
    }

    public void setDirectorDescs(List<String> directorDescs) {
        this.directorDescs = directorDescs;
    }

    public List<String> getActorNames() {
        return actorNames;
    }

    public void setActorNames(List<String> actorNames) {
        this.actorNames = actorNames;
    }

    public List<String> getActorDescs() {
        return actorDescs;
    }

    public void setActorDescs(List<String> actorDescs) {
        this.actorDescs = actorDescs;
    }

}

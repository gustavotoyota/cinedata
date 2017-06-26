package Model;

import java.util.List;

public class MovieSearchBean {
    private int numPages;
    private int pageIndex;
    private List<MovieResult> movies;       

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int totalPages) {
        this.numPages = totalPages;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int currentPage) {
        this.pageIndex = currentPage;
    }

    public List<MovieResult> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieResult> movies) {
        this.movies = movies;
    }

    
    
    
}

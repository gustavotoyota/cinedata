package Model;

import java.util.List;

public class DirectorSearchBean {    
    private int numPages;
    private int pageIndex;
    private List<DirectorResult> directors;
  
    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<DirectorResult> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorResult> directors) {
        this.directors = directors;
    }
    
    
    
}

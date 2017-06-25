package Model;

import java.util.List;

public class DirectorSearchBean {    
    private int numPages;
    private int pageIndex;
    private List<DirectorResult> results;
  
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

    public List<DirectorResult> getResults() {
        return results;
    }

    public void setResults(List<DirectorResult> results) {
        this.results = results;
    }
    
    
    
}

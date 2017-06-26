package DAO;

import Model.*;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class CineDataDAO {
    private static Connection conn = null;
    
    public static void initConnection() {
        if (conn != null)
            return;
        
        try{
            Class.forName("org.postgresql.Driver").newInstance();
            
            conn = DriverManager.getConnection("jdbc:postgresql:" +
                        "//localhost/CineData?user=postgres&password=123");                        
        } catch (Exception e){
        }                
    }        
    
    public CineDataDAO() {
        initConnection();
    }
    
    public MovieInfoBean getMovieInfo(MovieInfoBean movie) {
        // Retrieve movie id from bean
        int id = movie.getId();
        String command = "";
        try {
            Statement stmt = conn.createStatement();                
            
            // Select movie general info
            command += "SELECT mov_title, mov_year FROM movie WHERE mov_id = " + id;
            ResultSet rs = stmt.executeQuery(command);
            if (rs.next()) {
                movie.setTitle(rs.getString(1));
                movie.setYear(rs.getString(2));
            }
            
            // Select movie language
            command = "SELECT COALESCE(string_agg(lan_name, ', '),'') AS languages "
                    + "FROM language l JOIN movielanguage ml ON l.lan_id = ml.lan_id "
                    + "WHERE ml.mov_id = " + id;
            rs = stmt.executeQuery(command);
            if (rs.next())
                movie.setLanguages(rs.getString(1));
            
            // Select movie genre
            command = "SELECT COALESCE(string_agg(gen_name, ', '),'') AS genres "
                    + "FROM genre g JOIN moviegenre mg ON g.gen_id = mg.gen_id "
                    + "WHERE mg.mov_id = " + id;
            rs = stmt.executeQuery(command);
            if (rs.next())
                movie.setGenres(rs.getString(1));
            
            // Select directors
            command = "SELECT d.dir_name, md.md_addition "
                    + "FROM director d JOIN moviedirector md ON d.dir_id = md.dir_id "
                    + "WHERE md.mov_id = " + id;
            rs = stmt.executeQuery(command);            
            // Instatiate lists
            List<String> directorNames = new ArrayList<>();
            List<String> directorDescs = new ArrayList<>();                                    
            while (rs.next()) {
                directorNames.add(rs.getString(1));
                directorDescs.add(rs.getString(2));
            }
            movie.setDirectorNames(directorNames);
            movie.setDirectorDescs(directorDescs);
            
            // Select actors
            command = "SELECT a.act_name, ac.character_name "
                    + "FROM actor a JOIN ascharacter ac ON a.act_id = ac.act_id "
                    + "WHERE ac.mov_id = " + id;
            rs = stmt.executeQuery(command);
            // Instantiate lists
            List<String> actorNames = new ArrayList<>();
            List<String> actorDescs = new ArrayList<>();
            while (rs.next()) {
                actorNames.add(rs.getString(1));
                actorDescs.add(rs.getString(2));
            }
            movie.setActorNames(actorNames);
            movie.setActorDescs(actorDescs);
            
            return movie;
        } catch (Exception e) {            
        }
        
        return null;
    }
    
    public DirectorInfoBean getDirectorInfo(DirectorInfoBean director) {
        // Retrieve director id from bean
        int id = director.getId();
        String command;
        
        try{
            Statement stmt = conn.createStatement();
            
            // Select director general info
            command = "SELECT dir_name FROM director WHERE dir_id = " + id;
            ResultSet rs = stmt.executeQuery(command);
            if (rs.next()) 
                director.setName(rs.getString(1));
            
            // Select number of directed movies
            command = "SELECT COUNT(*) FROM moviedirector WHERE dir_id = " + id;
            rs = stmt.executeQuery(command);
            if (rs.next())
                director.setNumMovies(rs.getInt(1));
            
            // Select genres of directed movies
            command = "SELECT g.gen_name, COUNT(*) as qt_gen "
                    + "FROM moviedirector md JOIN moviegenre mg ON md.mov_id = mg.mov_id JOIN genre g ON mg.gen_id = g.gen_id "
                    + "WHERE md.dir_id = " + id
                    + " GROUP BY g.gen_id "
                    + "ORDER BY qt_gen DESC, gen_name ASC";
            rs = stmt.executeQuery(command);
            List<String> genres = new ArrayList<>();
            List<Integer> genreMovies = new ArrayList<>();
            while (rs.next()) {
                genres.add(rs.getString(1));
                genreMovies.add(rs.getInt(2));                
            }
            director.setGenres(genres);
            director.setGenreMovies(genreMovies);
            
            return director;
        } catch (Exception e) {            
        }
        
        return null;
    }
    
    public MovieSearchBean getMovieSearch(MovieSearchBean movies, int pageIndex, String title, ArrayList<String> directors, String language, boolean match_all) {
        int totalPages = 0;
        ArrayList<MovieResult> moviesList = new ArrayList<>();
        
        try {
            // Execute command
            conn.setAutoCommit(false);
            CallableStatement stmt = conn.prepareCall("{ call movieSearch(?, ?, ?, ?, ?) }");
            stmt.setInt(1, pageIndex);
            stmt.setString(2, title);
            stmt.setArray(3, conn.createArrayOf("VARCHAR", directors.toArray()));
            stmt.setString(4, language);
            stmt.setBoolean(5, match_all);
            stmt.execute();            
            ResultSet rs = (ResultSet) stmt.getResultSet();                                    
            
            // Get number of pages
            if (rs.next()) {
                Object o = rs.getObject(1);
                if (o instanceof ResultSet) {
                    ResultSet cursorResult = (ResultSet)o;
                    if (cursorResult.next())
                        totalPages = (int)Math.ceil(cursorResult.getInt(1)/10.0);                    
                }
            }     
            
            // Get ids and titles
            if (rs.next()) {                
                Object o = rs.getObject(1);
                if (o instanceof ResultSet) {
                  ResultSet cursorResult = (ResultSet)o;
                  while (cursorResult.next())
                      moviesList.add(new MovieResult(cursorResult.getInt(1), cursorResult.getString(2)));                  
                }
            }
            
            // Get directors
            if (rs.next()) {                
                Object o = rs.getObject(1);
                if (o instanceof ResultSet) {
                  ResultSet cursorResult = (ResultSet)o;
                  int i = 0;
                  while (cursorResult.next())
                      moviesList.get(i++).setDirector(cursorResult.getString(1));  
                }
            }
            
            // Get languages
            if (rs.next()) {                
                Object o = rs.getObject(1);
                if (o instanceof ResultSet) {
                  ResultSet cursorResult = (ResultSet)o;
                  int i = 0;
                  while (cursorResult.next())
                      moviesList.get(i++).setLanguage(cursorResult.getString(1));  
                }
            }
            
            movies.setNumPages(totalPages);
            movies.setPageIndex(pageIndex);
            movies.setMovies(moviesList);
            return movies;
        } catch (Exception e) {       
            System.out.println(e);
        }
        
        return null;
    }
    
    public DirectorSearchBean getDirectorSearch(DirectorSearchBean directors, int pageIndex, String compOp, int compQt, ArrayList<String> genres, String order) {        
        ArrayList<DirectorResult> directorsList = new ArrayList<>();
        
        // Generate genre array
        String genreArray = "'{";
        for (int i = 0; i < genres.size(); ++i) {
            genreArray += "\"" + genres.get(i) + "\"";
            if (i != genres.size() - 1)
                genreArray += ",";
        }
        genreArray += "}'";    
        
        String command = "";
        
        try {
            Statement stmt = conn.createStatement();
            
            // Generate command to fetch director ids
            command += "SELECT dg.dir_id, string_agg(g.gen_name, ', '), COUNT(g.gen_name) as qt_gen, COUNT(*) OVER() as total "
                    + "FROM directorgenre dg JOIN ( "
                    + "SELECT gen_id, gen_name FROM genre ";
            if (genres.size() > 0)
                command += "WHERE gen_name = ANY (" + genreArray + "::VARCHAR[])";            
            command += ") g ON dg.gen_id = g.gen_id "
                    + "GROUP BY dg.dir_id "
                    + "HAVING COUNT(g.gen_name) " + compOp + " " + Integer.toString(compQt) + " "
                    + "ORDER BY qt_gen " + order + ", dg.dir_id "
                    + "LIMIT 10 OFFSET (" + Integer.toString(pageIndex) + "-1)*10";

            // Execute command
            ResultSet rs = stmt.executeQuery(command);
            
            // Save results
            directors.setPageIndex(pageIndex);
            int rank = (pageIndex-1)*10;
            while(rs.next()) {                         
                directorsList.add(new DirectorResult(rs.getInt(1), ++rank, rs.getString(2), rs.getInt(3)));
                directors.setNumPages((int)Math.ceil(rs.getInt(4)/10.0)); 
            }
            
            // Fecth director names and movie quantity
            CallableStatement stmt_director = conn.prepareCall("{ call directorSearch(?) }");
            for (int i = 0; i < directorsList.size(); ++i) {                
                stmt_director.setInt(1, directorsList.get(i).getId());
                stmt_director.execute();
                rs = (ResultSet) stmt_director.getResultSet();
                if (rs.next()) {
                    directorsList.get(i).setName(rs.getString(1));
                    directorsList.get(i).setNumMovies(rs.getInt(2));
                }
            }
            
            directors.setDirectors(directorsList);
            return directors;
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
        
        /*String command = "SELECT dir_id, string_agg(gen_name, ', '), COUNT(gen_name), COUNT(*) OVER() as qt_gen FROM ("
                + "SELECT dir_id, gen_name FROM moviedirector md JOIN ("
                + "SELECT mov_id, gen_name FROM moviegenre mg JOIN ("
                + "SELECT gen_id, gen_name FROM genre WHERE gen_name = ANY (" +  + "::VARCHAR[]))g ON mg.gen_id = g.gen_id)g ON md.mov_id = g.mov_id GROUP BY dir_id, gen_name;)mg GROUP BY dir_id HAVING COUNT(gen_name) > 0 ORDER BY qt_gen ASC dir_id "
        + "LIMIT 10 OFFSET (" + Integer.toString(pageNumber) + "-1)*10;"*/
    }

}

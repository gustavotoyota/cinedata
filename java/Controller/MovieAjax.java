/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import DAO.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pedro Pires
 */
public class MovieAjax extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String[] directors = request.getParameterValues("directors");
        String language = request.getParameter("language");
        String exact = request.getParameter("exact");
        String page = request.getParameter("page");
        
        boolean matchAll = false;
        if ("on".equals(exact))
            matchAll = true;
        
        int pageIndex;      
        try{
            pageIndex = Integer.parseInt(page);
        }catch(Exception e){            
            pageIndex = 1;
        }
        
        MovieSearchBean bean = new MovieSearchBean();
        CineDataDAO dao = new CineDataDAO();
        
        ArrayList<String> directorList = new ArrayList<>(Arrays.asList(directors));        
        for (int i = directorList.size()-1; i >= 0; --i)
            if ("".equals(directorList.get(i)))
                directorList.remove(i);
        
        bean = dao.getMovieSearch(bean, pageIndex, title, directorList, language, matchAll);
        
        request.setAttribute("bean", bean);
        request.getRequestDispatcher("movie-ajax.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

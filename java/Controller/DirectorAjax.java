/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.*;
import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DirectorAjax extends HttpServlet {

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
        String compOp = request.getParameter("compop");
        String compQt = request.getParameter("compqt");
        String[] genres = request.getParameterValues("genres");
        String order = request.getParameter("order");
        String page = request.getParameter("page");
        
        DirectorSearchBean bean = new DirectorSearchBean();
        CineDataDAO dao = new CineDataDAO();
        
        int pageIndex;
        try{
            pageIndex = Integer.parseInt(page);            
        }catch(Exception e){            
            pageIndex = 1;            
        }
        
        int comparisonQty;   
        try{            
            comparisonQty = Integer.parseInt(compQt);
        }catch(Exception e){                    
            comparisonQty = 0;
        }
        
        ArrayList<String> genreList = new ArrayList<>(Arrays.asList(genres));
        for (int i = genreList.size()-1; i >= 0; --i)
            if ("".equals(genreList.get(i)))
                genreList.remove(i);
                
        bean = dao.getDirectorSearch(bean, pageIndex, compOp, comparisonQty, genreList, order);
        
        request.setAttribute("bean", bean);
        request.getRequestDispatcher("director-ajax.jsp").forward(request, response);
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

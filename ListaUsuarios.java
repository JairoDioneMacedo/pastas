/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.DAO.UsuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jairo
 */
public class ListaUsuarios extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        int numPagina = 1;

        if(request.getParameter("numpagina") != null){
            numPagina = Integer.parseInt(request.getParameter("numpagina"));
        }

        UsuarioDAO usuariosDAO = new UsuarioDAO();

        try {
            String ordenacao = request.getParameter("ordenacao");
                if(ordenacao == null)
                    ordenacao = "usuario";
                
                String localizar = request.getParameter("localizar");
                
                if(localizar == null)
                    localizar = "usuario";
                
                String campoPesquisa = request.getParameter("campopesquisa");
                
                if(campoPesquisa == null)
                    campoPesquisa = "usuario";
                
            List listaUsuarios = usuariosDAO.getListaUsuarioPaginada(numPagina, ordenacao,localizar,campoPesquisa);
            String qtdTotalRegistros = usuariosDAO.totalRegistros(localizar,campoPesquisa);
            request.setAttribute("sessaoListaUsuarios", listaUsuarios);
            request.setAttribute("sessaoQtdTotalRegistros", qtdTotalRegistros);
            RequestDispatcher rd = request.getRequestDispatcher("/listausuarios.jsp");
            rd.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ListaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ListaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ListaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
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

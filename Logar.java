/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.DAO.UsuarioDAO;
import br.com.jairo.modelo.Usuarios;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jairo
 */
public class Logar extends HttpServlet {

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
        
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
                
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuarios usuarios = usuarioDAO.getUsuario(usuario, senha);
        
        RequestDispatcher rd = null;
        HttpSession sessao = request.getSession();
        
         if (usuarios != null && usuarios.getNivel() == 1) {
            sessao.setAttribute("sessaoUsuario", usuario);
            sessao.setAttribute("nomeCompleto", usuarios.getNomeCompleto());
            sessao.setAttribute("nome", usuarios.getUsuario());
            sessao.setAttribute("nivel", usuarios.getNivel());
            rd = request.getRequestDispatcher("/index1.jsp");
            //System.out.println(usuarios.getNivel());
            rd.forward(request, response);
        } else if (usuarios != null && usuarios.getNivel() == 2) {
            sessao.setAttribute("sessaoUsuario", usuario);
            sessao.setAttribute("nomeCompleto", usuarios.getNomeCompleto());
            sessao.setAttribute("nome", usuarios.getUsuario());
            sessao.setAttribute("nivel", usuarios.getNivel());
            rd = request.getRequestDispatcher("/index1.jsp");
            rd.forward(request, response);
        } else if (usuarios != null && usuarios.getNivel() == 3) {
            sessao.setAttribute("sessaoUsuario", usuario);
            sessao.setAttribute("nomeCompleto", usuarios.getNomeCompleto());
            sessao.setAttribute("nome", usuarios.getUsuario());
            sessao.setAttribute("nivel", usuarios.getNivel());
            rd = request.getRequestDispatcher("/index2.jsp");
            rd.forward(request, response);
        } else {
            request.setAttribute("mensagem", "Usuario ou Senha Invalido!");
            rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
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
            Logger.getLogger(Logar.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Logar.class.getName()).log(Level.SEVERE, null, ex);
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

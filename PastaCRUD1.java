/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.DAO.PastasDAO;
import br.com.jairo.modelo.Pastas;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class PastaCRUD1 extends HttpServlet {

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
            throws ServletException, IOException, ParseException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        RequestDispatcher rd = null;
        String movimento = request.getParameter("movimento");
        String data = request.getParameter("data");
        String usuario = request.getParameter("usuario");
        String imovel = request.getParameter("imovel");
        String obsUsuario = request.getParameter("obsusuario");
        String obsArquivo = request.getParameter("obsarquivo");
        String entregue = request.getParameter("entregue");
        String devolvida = request.getParameter("devolvida");
        String naoLocalizada = request.getParameter("naolocalizada");

        Pastas pastas = new Pastas();

        if (movimento != null) {
            pastas.setMovimento(Integer.parseInt(movimento));
        }

        if (data != null) {
            DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            Date dataFormatada = formatoData.parse(data);
            pastas.setData(dataFormatada);
        }

        pastas.setUsuario(usuario);
        pastas.setImovel(imovel);
        pastas.setObsUsuario(obsUsuario);
        pastas.setObsArquivo(obsArquivo);

        if (entregue != null) {
            pastas.setEntregue(true);
        }

        if (devolvida != null) {
            pastas.setDevolvido(true);
        }

        if (naoLocalizada != null) {
            pastas.setNaoLocalizada(true);
        }

        PastasDAO pastasDAO = new PastasDAO();

        //verifica qual é a ação
        String acao = request.getParameter("acao");
        if (acao == null) {
            acao = "listaPastas";
        }
        if (acao.equals("alterar")) {
            pastasDAO.alteraPasta(pastas);
            rd = request.getRequestDispatcher("PastaCRUD1?acao=listaPastas");
        } else if (acao.equals("excluir")) {
            pastasDAO.excluiPasta(pastas);
            rd = request.getRequestDispatcher("PastaCRUD1?acao=listaPastas");
        } else if (acao.equals("listaPastas")) {
            int numPagina = 1;

            if (request.getParameter("numpagina") != null) {
                numPagina = Integer.parseInt(request.getParameter("numpagina"));
            }
            try {
                String ordenacao = request.getParameter("ordenacao");

                String localizar = request.getParameter("localizar");

                if (localizar == null) {
                    localizar = "";
                }

                if (ordenacao == null) {
                    ordenacao = "movimento";
                }

                String campoPesquisa = request.getParameter("campopesquisa");

                if (campoPesquisa == null) {
                    campoPesquisa = "movimento";
                }

                List listaPastas = pastasDAO.getListaPastaPaginada(numPagina, ordenacao, localizar, campoPesquisa);
                String qtdTotalRegistros = pastasDAO.totalRegistros(localizar, campoPesquisa);
                request.setAttribute("sessaoListaPastas", listaPastas);
                request.setAttribute("sessaoQtdTotalRegistros", qtdTotalRegistros);
                rd = request.getRequestDispatcher("/listapastasolicitadas.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(PastaCRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.equals("inserir")) {
            pastasDAO.novaPasta(pastas);
            rd = request.getRequestDispatcher("PastaCRUD1?acao=listaPastas");
        }

        rd.forward(request, response);
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
        } catch (ParseException ex) {
            Logger.getLogger(PastaCRUD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PastaCRUD1.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ParseException ex) {
            Logger.getLogger(PastaCRUD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PastaCRUD1.class.getName()).log(Level.SEVERE, null, ex);
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

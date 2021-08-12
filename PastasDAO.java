/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.DAO;

import br.com.jairo.conexao.FabricaConexao;
import br.com.jairo.modelo.Pastas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jairo
 */
public class PastasDAO {

    private Connection conexao;

    public PastasDAO() {
        this.conexao = new FabricaConexao().getConnection();
    }

    //lista pastas paginada cadastrados no sistema
    public List getListaPastaPaginada(int pagina, String ordenacao, String localizar, String campopesquisa) throws SQLException {

        int limite = 6;

        int offset = (pagina * limite) - limite;

        String sql = null;

        if (campopesquisa.equals("movimento")) {
            if (localizar.equals("")) {
                sql = "select * from pastas where " + campopesquisa + " > 0 order by " + ordenacao + " desc LIMIT 6 OFFSET " + offset;
            } else {
                sql = "select * from pastas where " + campopesquisa + " = " + localizar + " order by " + ordenacao + " desc LIMIT 6 OFFSET " + offset;
            }
        } else {
            sql = "select * from pastas where " + campopesquisa + " like '%" + localizar + "%' order by " + ordenacao + " desc LIMIT 6 OFFSET " + offset;
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Pastas> listaPastas = new ArrayList<Pastas>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Pastas pastas = new Pastas();
                pastas.setMovimento(rs.getInt("movimento"));
                pastas.setData(rs.getDate("data"));
                pastas.setUsuario(rs.getString("usuario"));
                pastas.setImovel(rs.getString("imovel"));
                pastas.setObsUsuario(rs.getString("obsusuario"));
                pastas.setObsArquivo(rs.getString("obsarquivo"));
                pastas.setEntregue(rs.getBoolean("entregue"));
                pastas.setDevolvido(rs.getBoolean("devolvida"));
                pastas.setNaoLocalizada(rs.getBoolean("naolocalizada"));
                listaPastas.add(pastas);
            }
            return listaPastas;
        } catch (SQLException ex) {
            Logger.getLogger(PastasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //lista pendencia paginada no sistema
    public List getListaPastaPendentePaginada(int pagina, String ordenacao) throws SQLException {

        int limite = 6;

        int offset = (pagina * limite) - limite;

        String sql = sql = "select * from pastas where entregue = false and devolvida = false and naolocalizada = false order by " + ordenacao + " desc LIMIT 6 OFFSET " + offset;

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Pastas> listaPastasPendente = new ArrayList<Pastas>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Pastas pastas = new Pastas();
                pastas.setMovimento(rs.getInt("movimento"));
                pastas.setData(rs.getDate("data"));
                pastas.setUsuario(rs.getString("usuario"));
                pastas.setImovel(rs.getString("imovel"));
                pastas.setObsUsuario(rs.getString("obsusuario"));
                pastas.setObsArquivo(rs.getString("obsarquivo"));
                pastas.setEntregue(rs.getBoolean("entregue"));
                pastas.setDevolvido(rs.getBoolean("devolvida"));
                pastas.setNaoLocalizada(rs.getBoolean("naolocalizada"));
                listaPastasPendente.add(pastas);
            }
            return listaPastasPendente;
        } catch (SQLException ex) {
            Logger.getLogger(PastasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo que conta a quantidade de registros
    public String totalRegistros(String localizar, String campopesquisa) throws SQLException {
        PreparedStatement psConta = null;
        ResultSet rsConta = null;
        String sqlConta = null;
        try {
            if (campopesquisa.equals("movimento")) {
                if (localizar.equals("")) {
                    sqlConta = "select count(*) as contaRegistros from pastas where " + campopesquisa + " > 0";
                } else {
                    sqlConta = "select count(*) as contaRegistros from pastas where " + campopesquisa + " = " + localizar;
                }
            } else {
                sqlConta = "select count(*) as contaRegistros from pastas where " + campopesquisa + " like '%" + localizar + "%'";
            }
            psConta = conexao.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistros");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(PastasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //psConta.close();
            //rsConta.close();
        }
        return null;
    }

    //metodo que conta a quantidade de registros pendentes
    public String totalRegistrosPendente() throws SQLException {
        PreparedStatement psContaPendente = null;
        ResultSet rsContaPendente = null;
        String sqlContaPendente = null;
        try {
            sqlContaPendente = "select count(*) AS pastaPendente from pastas where entregue = false and devolvida = false and naolocalizada = false";

            psContaPendente = conexao.prepareStatement(sqlContaPendente);
            rsContaPendente = psContaPendente.executeQuery();
            rsContaPendente.next();
            String qtdTotalRegistros = rsContaPendente.getString("pastaPendente");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(PastasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //psContaPendente.close();
            //rsContaPendente.close();
        }
        return null;
    }

    //metodo para excluir pasta usando modelo
    public boolean excluiPasta(Pastas pastas) throws SQLException {
        String sql = "delete from pastas where movimento=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        try {
            ps.setInt(1, pastas.getMovimento());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PastasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
        }
        return false;
    }

    //metodo para atualizar pasta
    public void alteraPasta(Pastas pastas) throws SQLException {
        String sql = "update pastas set data=?,usuario=?,imovel=?,obsusuario=?,obsarquivo=?,entregue=?,devolvida=?,naolocalizada=? where movimento=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        try {
            ps.setDate(1, new java.sql.Date(pastas.getData().getTime()));
            ps.setString(2, pastas.getUsuario());
            ps.setString(3, pastas.getImovel());
            ps.setString(4, pastas.getObsUsuario());
            ps.setString(5, pastas.getObsArquivo());
            ps.setBoolean(6, pastas.isEntregue());
            ps.setBoolean(7, pastas.isDevolvido());
            ps.setBoolean(8, pastas.isNaoLocalizada());
            ps.setInt(9, pastas.getMovimento());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PastasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
        }
    }

    //metodo para inserir pastas
    public void novaPasta(Pastas pastas) throws SQLException {
        String sql = "insert into pastas (data,usuario,imovel,obsusuario,obsarquivo,entregue,devolvida,naolocalizada) values(?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conexao.prepareStatement(sql);
        try {
            ps.setDate(1, new java.sql.Date(pastas.getData().getTime()));
            ps.setString(2, pastas.getUsuario());
            ps.setString(3, pastas.getImovel());
            ps.setString(4, pastas.getObsUsuario());
            ps.setString(5, pastas.getObsArquivo());
            ps.setBoolean(6, pastas.isEntregue());
            ps.setBoolean(7, pastas.isDevolvido());
            ps.setBoolean(8, pastas.isNaoLocalizada());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PastasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.DAO;

import br.com.jairo.conexao.FabricaConexao;
import br.com.jairo.modelo.Usuarios;
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
public class UsuarioDAO {

    private Connection conexao;

    public UsuarioDAO() {
        this.conexao = new FabricaConexao().getConnection();
    }

    //retorna se existe ou não o usuario
    public boolean verificaUsuario(Usuarios usuarios) {
        String sql = "select * from usuarios where usuario=? and senha=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuarios.getUsuario());
            ps.setString(2, usuarios.getSenha());
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //retorna o usuario
    public Usuarios getUsuario(String usuario, String senha) throws SQLException {
        String sql = "select * from usuarios where usuario=? and senha=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, senha);
            rs = ps.executeQuery();
            if (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setUsuario(usuario);
                usuarios.setSenha(senha);
                usuarios.setNivel(rs.getInt("nivel"));
                usuarios.setNomeCompleto(rs.getString("nomecompleto"));
                return usuarios;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
            rs.close();
        }
        return null;
    }

    //lista usuarios cadastrados no sistema
    public List getListaUsuario() throws SQLException {
        String sql = "select * from usuarios";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setUsuario(rs.getString("usuario"));
                usuarios.setSenha(rs.getString("senha"));
                usuarios.setNivel(rs.getInt("nivel"));
                usuarios.setNomeCompleto(rs.getString("nomecompleto"));
                listaUsuarios.add(usuarios);
            }
            return listaUsuarios;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
            rs.close();
        }
        return null;
    }

    //lista usuarios paginada cadastrados no sistema
    public List getListaUsuarioPaginada(int pagina, String ordenacao, String localizar, String campopesquisa) throws SQLException {

        int limite = 8;

        int offset = (pagina * limite) - limite;
        
        String sql = null;
        
        if(campopesquisa.equals("nivel")){
            if(localizar.equals("")){
                sql = "select * from usuarios where " + campopesquisa + " > 0 order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            }else{
                sql = "select * from usuarios where " + campopesquisa + " = " + localizar + " order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            }
        }else{
            sql = "select * from usuarios where " + campopesquisa + " like '%" + localizar + "%' order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setUsuario(rs.getString("usuario"));
                usuarios.setSenha(rs.getString("senha"));
                usuarios.setNivel(rs.getInt("nivel"));
                usuarios.setNomeCompleto(rs.getString("nomecompleto"));
                listaUsuarios.add(usuarios);
            }
            return listaUsuarios;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            if(campopesquisa.equals("nivel")){
                if (localizar.equals("")) {
                    sqlConta = "select count(*) as contaRegistros from usuarios where " + campopesquisa + " > 0";
                } else {
                    sqlConta = "select count(*) as contaRegistros from usuarios where " + campopesquisa + " = " + localizar;
                }
            }else{
                sqlConta = "select count(*) as contaRegistros from usuarios where " + campopesquisa + " like '%" + localizar + "%'";
            }
            psConta = conexao.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistros");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            psConta.close();
            rsConta.close();
        }
        return null;
    }

    //metodo para excluir usuario usando modelo
    public boolean excluiUsuario(Usuarios usuarios) throws SQLException {
        String sql = "delete from usuarios where usuario=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        try {
            ps.setString(1, usuarios.getUsuario());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
        }
        return false;
    }

    //metodo para atualizar usuario
    public void alteraUsuario(Usuarios usuarios) throws SQLException {
        String sql = "update usuarios set senha=?,nivel=?,nomecompleto=? where usuario=?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        try {
            ps.setString(1, usuarios.getSenha());
            ps.setInt(2, usuarios.getNivel());
            ps.setString(3, usuarios.getNomeCompleto());
            ps.setString(4, usuarios.getUsuario());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
        }
    }

    //metodo para inserir usuario
    public void novoUsuario(Usuarios usuarios) throws SQLException {
        String sql = "insert into usuarios values(?,?,?,?)";
        PreparedStatement ps = conexao.prepareStatement(sql);
        try {
            ps.setString(1, usuarios.getUsuario());
            ps.setString(2, usuarios.getSenha());
            ps.setInt(3, usuarios.getNivel());
            ps.setString(4, usuarios.getNomeCompleto());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.modelo;

import java.util.Date;

/**
 *
 * @author Jairo
 */
public class Pastas {
    private int movimento;
    private Date data;
    private String usuario;
    private String imovel;
    private String obsUsuario;
    private String obsArquivo;
    private boolean entregue;
    private boolean devolvido;
    private boolean naoLocalizada;

    /**
     * @return the movimento
     */
    public int getMovimento() {
        return movimento;
    }

    /**
     * @param movimento the movimento to set
     */
    public void setMovimento(int movimento) {
        this.movimento = movimento;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the imovel
     */
    public String getImovel() {
        return imovel;
    }

    /**
     * @param imovel the imovel to set
     */
    public void setImovel(String imovel) {
        this.imovel = imovel;
    }

    /**
     * @return the obsUsuario
     */
    public String getObsUsuario() {
        return obsUsuario;
    }

    /**
     * @param obsUsuario the obsUsuario to set
     */
    public void setObsUsuario(String obsUsuario) {
        this.obsUsuario = obsUsuario;
    }

    /**
     * @return the obsArquivo
     */
    public String getObsArquivo() {
        return obsArquivo;
    }

    /**
     * @param obsArquivo the obsArquivo to set
     */
    public void setObsArquivo(String obsArquivo) {
        this.obsArquivo = obsArquivo;
    }

    /**
     * @return the entregue
     */
    public boolean isEntregue() {
        return entregue;
    }

    /**
     * @param entregue the entregue to set
     */
    public void setEntregue(boolean entregue) {
        this.entregue = entregue;
    }

    /**
     * @return the devolvido
     */
    public boolean isDevolvido() {
        return devolvido;
    }

    /**
     * @param devolvido the devolvido to set
     */
    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }

    /**
     * @return the naoLocalizada
     */
    public boolean isNaoLocalizada() {
        return naoLocalizada;
    }

    /**
     * @param naoLocalizada the naoLocalizada to set
     */
    public void setNaoLocalizada(boolean naoLocalizada) {
        this.naoLocalizada = naoLocalizada;
    }
}

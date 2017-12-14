package mateomartinelli.user2cadem.it.provafinale.Model;

import java.io.Serializable;

/**
 * Created by utente2.academy on 12/14/2017.
 */

public class Pacco implements Serializable{
    private String idPacco;
    private String nomeCorriere;
    private String data;
    private String deposito;
    private String destinazione;
    private String dimensione;

    public Pacco(String idPacco, String nomeCorriere, String data, String deposito, String destinazione, String dimensione) {
        this.idPacco = idPacco;
        this.nomeCorriere = nomeCorriere;
        this.data = data;
        this.deposito = deposito;
        this.destinazione = destinazione;
        this.dimensione = dimensione;
    }

    public String getIdPacco() {
        return idPacco;
    }

    public void setIdPacco(String idPacco) {
        this.idPacco = idPacco;
    }

    public String getNomeCorriere() {
        return nomeCorriere;
    }

    public void setNomeCorriere(String nomeCorriere) {
        this.nomeCorriere = nomeCorriere;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public String getDimensione() {
        return dimensione;
    }

    public void setDimensione(String dimensione) {
        this.dimensione = dimensione;
    }
}

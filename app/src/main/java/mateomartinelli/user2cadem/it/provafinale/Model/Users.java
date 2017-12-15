package mateomartinelli.user2cadem.it.provafinale.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by utente2.academy on 12/14/2017.
 */

public abstract class Users implements Serializable{
    private String userName;
    private ArrayList<String> idPacchi;

    public Users(String userName, ArrayList<String> idPacchi) {
        this.userName = userName;
        this.idPacchi = idPacchi;
    }

    public Users(String userName) {
        this.userName = userName;
        idPacchi = new ArrayList<String>();
    }

    public Users() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getIdPacchi() {
        return idPacchi;
    }

    public void setIdPacchi(ArrayList<String> idPacchi) {
        this.idPacchi = idPacchi;
    }
}

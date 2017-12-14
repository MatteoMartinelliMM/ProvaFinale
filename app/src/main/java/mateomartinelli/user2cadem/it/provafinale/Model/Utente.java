package mateomartinelli.user2cadem.it.provafinale.Model;

import java.io.Serializable;
import java.util.ArrayList;

import mateomartinelli.user2cadem.it.provafinale.Model.Users;

/**
 * Created by utente2.academy on 12/14/2017.
 */

public class Utente extends Users implements Serializable {
    public Utente(String userName, ArrayList<String> idPacchi) {
        super(userName, idPacchi);
    }

    public Utente(String userName) {
        super(userName);
    }
}

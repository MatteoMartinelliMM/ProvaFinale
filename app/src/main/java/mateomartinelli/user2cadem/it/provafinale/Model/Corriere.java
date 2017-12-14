package mateomartinelli.user2cadem.it.provafinale.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by utente2.academy on 12/14/2017.
 */

public class Corriere extends Users implements Serializable {
    public Corriere(String userName, ArrayList<String> idPacchi) {
        super(userName, idPacchi);
    }

    public Corriere(String userName) {
        super(userName);
    }
}

package mateomartinelli.user2cadem.it.provafinale.Contoller;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by utente2.academy on 12/14/2017.
 */

public class JSONParser {

    public static String getPwd(String toParse){
        String pwd=null;
        try {
            JSONObject jsonObject = new JSONObject(toParse);
            pwd = jsonObject.getString("pwd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pwd;
    }
}

package mateomartinelli.user2cadem.it.provafinale.Contoller;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import mateomartinelli.user2cadem.it.provafinale.Model.Pacco;

/**
 * Created by utente2.academy on 12/14/2017.
 */

public class JSONParser {

    public static String getPwd(String toParse) {
        String pwd = null;
        try {
            JSONObject jsonObject = new JSONObject(toParse);
            pwd = jsonObject.getString("pwd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pwd;
    }

    public static ArrayList<String> getCurriersIdPackages(String toParse, Context context) {
        ArrayList<String> idPacchi = new ArrayList<>();
        String currentPackageId = "";
        try {
            JSONObject packages = new JSONObject(toParse);
            Iterator<String> packagesId = packages.keys();
            while (packagesId.hasNext()) {
                currentPackageId = packagesId.next();
                idPacchi.add(currentPackageId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!currentPackageId.equals(""))
            UtilitySharedPreference.saveLastDeliverId(context, currentPackageId);
        return idPacchi;
    }

    public static ArrayList<Pacco> getCurriersPackagesToDeliver(String toParse, ArrayList<String> idPacchi) {
        ArrayList<Pacco> packages = new ArrayList<>();
        Iterator<String> keys;
        String currentPackageId = "";
        int addedPackages=0;
        try {
            JSONObject packagesId = new JSONObject(toParse);
            keys = packagesId.keys();
            while (keys.hasNext()) {
                currentPackageId = keys.next();
                if(addedPackages<idPacchi.size()) {
                    for (String id : idPacchi) {
                        if (currentPackageId.equals(id)) {
                            JSONObject packageToAdd = packagesId.getJSONObject(currentPackageId);
                            Iterator<String> packageFields = packageToAdd.keys();
                            Pacco pacco = new Pacco();
                            while (packageFields.hasNext()) {
                                String field = packageFields.next();
                                switch (field) {
                                    case "Corriere":
                                        String currierName = packageToAdd.getString(field);
                                        pacco.setNomeCorriere(currierName);
                                        break;
                                    case "DataConsegna":
                                        String dataConsegna = packageToAdd.getString(field);
                                        pacco.setData(dataConsegna);
                                        break;
                                    case "Deposito":
                                        String deposito = packageToAdd.getString(field);
                                        pacco.setDeposito(deposito);
                                        break;
                                    case "Destinatario":
                                        String destinatario = packageToAdd.getString(field);
                                        pacco.setNomeCorriere(destinatario);
                                        break;
                                    case "Destinazione":
                                        String destinazione = packageToAdd.getString(field);
                                        pacco.setNomeCorriere(destinazione);
                                        break;
                                    case "Dimensione":
                                        String dimensione = packageToAdd.getString(field);
                                        pacco.setNomeCorriere(dimensione);
                                        break;
                                    case "Stato":
                                        String stato = packageToAdd.getString(field);
                                        pacco.setStato(stato);
                                    default:
                                        break;
                                }
                                pacco.setIdPacco(currentPackageId);
                                packages.add(pacco);
                                addedPackages++;
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return packages;
    }
}

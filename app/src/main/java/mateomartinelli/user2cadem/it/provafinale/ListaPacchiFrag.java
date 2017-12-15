package mateomartinelli.user2cadem.it.provafinale;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mateomartinelli.user2cadem.it.provafinale.Contoller.JSONParser;
import mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject;
import mateomartinelli.user2cadem.it.provafinale.Contoller.RestCall;
import mateomartinelli.user2cadem.it.provafinale.Contoller.SimplePackageAdapter;
import mateomartinelli.user2cadem.it.provafinale.Contoller.TaskWaiting;
import mateomartinelli.user2cadem.it.provafinale.Contoller.UserPackageAdapter;
import mateomartinelli.user2cadem.it.provafinale.Contoller.UtilitySharedPreference;
import mateomartinelli.user2cadem.it.provafinale.Model.Pacco;
import mateomartinelli.user2cadem.it.provafinale.Model.Utente;

import static mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject.LOGGED_USER;


public class ListaPacchiFrag extends Fragment implements TaskWaiting {
    private RecyclerView listaPacchi;
    private ArrayList<Pacco> pacchiUtente;
    private UserPackageAdapter packageAdapter;
    private ArrayList<String> idPacchi;
    private Utente loggedUtente;
    private Context context;
    private LinearLayoutManager lm;
    private ProgressDialog dialog;
    private TaskWaiting taskWaiting;
    private View v;

    public ListaPacchiFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userName = null;
        gettingTheContext();
        pacchiUtente = new ArrayList<>();
        idPacchi = new ArrayList<>();
        taskWaiting = this;
        userName = UtilitySharedPreference.getLoggedUsername(context);
        loggedUtente = (Utente) RWObject.readObject(context, LOGGED_USER);

        RestCall.get("Users/Utente/" + userName + "/Pacchi.json", new AsyncHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String toParse = new String(responseBody);
                    idPacchi = JSONParser.getCurriersIdPackages(toParse, context);
                    loggedUtente.setIdPacchi(idPacchi);
                }
                if (loggedUtente.getIdPacchi().size() != 0) {

                    RestCall.get("Pacchi.json", new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200) {
                                String toParse = new String(responseBody);
                                pacchiUtente = JSONParser.getCurriersPackagesToDeliver(toParse, loggedUtente.getIdPacchi());
                            }
                            taskWaiting.waitToComplete("");

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            taskWaiting.waitToComplete("ERROR: " + statusCode);
                        }
                    });
                }
            }
            @Override
            public void onFailure ( int statusCode, Header[] headers,byte[] responseBody, Throwable error){
                taskWaiting.waitToComplete("ERROR: " + statusCode);
            }
        });
        }


    private void gettingTheContext() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
            context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_lista_pacchi, container, false);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void waitToComplete(String s) {
        listaPacchi = v.findViewById(R.id.userPackageList);
        lm = new LinearLayoutManager(getActivity());
        packageAdapter = new UserPackageAdapter(pacchiUtente);
        listaPacchi.setLayoutManager(lm);
        listaPacchi.setAdapter(packageAdapter);
    }
}

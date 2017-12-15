package mateomartinelli.user2cadem.it.provafinale;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mateomartinelli.user2cadem.it.provafinale.Contoller.CourrierAdapter;
import mateomartinelli.user2cadem.it.provafinale.Contoller.JSONParser;
import mateomartinelli.user2cadem.it.provafinale.Contoller.RestCall;
import mateomartinelli.user2cadem.it.provafinale.Contoller.TaskWaiting;
import mateomartinelli.user2cadem.it.provafinale.Contoller.UserPackageAdapter;
import mateomartinelli.user2cadem.it.provafinale.Model.Corriere;


public class ListaCorrieriFrag extends Fragment implements TaskWaiting{
    private ListView currierList;
    private ArrayList<Corriere> corrieri;
    private CourrierAdapter adapter;
    private Context context;
    private ProgressDialog dialog;
    private View v;

    public ListaCorrieriFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gettingTheContext();
        corrieri = new ArrayList<>();
        dialog = new ProgressDialog(context);
        final TaskWaiting taskWaiting = this;
        dialog = new ProgressDialog(getActivity());
        dialog.onStart();
        RestCall.get("Users/Corriere.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String toParse = new String(responseBody);
                    corrieri = JSONParser.getCurriers(toParse);
                }
                taskWaiting.waitToComplete("");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                taskWaiting.waitToComplete("Error:"+statusCode);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_lista_corrieri, container, false);
        getActivity().setTitle("Corrieri Disponibili");
        currierList = v.findViewById(R.id.currierList);
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

    private void gettingTheContext() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
            context = getContext();
    }


    @Override
    public void waitToComplete(String s) {
        dialog.dismiss();
        dialog.cancel();
        currierList = v.findViewById(R.id.currierList);
        adapter = new CourrierAdapter(context,R.layout.currier_list,corrieri);
        currierList.setAdapter(adapter);
    }
}

package mateomartinelli.user2cadem.it.provafinale;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import mateomartinelli.user2cadem.it.provafinale.Contoller.JSONParser;
import mateomartinelli.user2cadem.it.provafinale.Contoller.RestCall;
import mateomartinelli.user2cadem.it.provafinale.Contoller.TaskWaiting;
import mateomartinelli.user2cadem.it.provafinale.Contoller.UtilitySharedPreference;
import mateomartinelli.user2cadem.it.provafinale.Model.Corriere;
import mateomartinelli.user2cadem.it.provafinale.Model.DataUtils;


public class AggiungiPaccoFrag extends Fragment implements TaskWaiting, View.OnClickListener, OnMapReadyCallback {
    private Spinner chooseCurrier, sizeChoose;
    private EditText address, pickingAddress;
    private TextView next, arrivalDate;
    private ArrayList<String> curriersName;
    private ProgressDialog dialog;
    private Context context;
    private ArrayAdapter adapter;
    private Button conferma;
    private String nextId;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private MapFragment mapFragment;
    private double longitude, latitude;

    public AggiungiPaccoFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curriersName = new ArrayList<>();
        final TaskWaiting taskWaiting = this;
        dialog = new ProgressDialog(getActivity());
        dialog.onStart();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReferenceFromUrl("https://provafinale-93015.firebaseio.com/");
        RestCall.get("Users/Corriere.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String toParse = new String(responseBody);
                    curriersName = JSONParser.getCurriersName(toParse);
                }
                if (!curriersName.isEmpty()) {
                    RestCall.get("Pacchi.json", new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200) {
                                String toParse = new String(responseBody);
                                nextId = JSONParser.getNextAvailablePackageId(toParse);
                            }
                            taskWaiting.waitToComplete("");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            taskWaiting.waitToComplete("ERRORE" + statusCode);
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                taskWaiting.waitToComplete("Error " + statusCode);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_aggiungi_pacco, container, false);
        getActivity().setTitle("Aggiungi un pacco");
        address = v.findViewById(R.id.address);
        next = v.findViewById(R.id.paccId);
        chooseCurrier = v.findViewById(R.id.chooseCurrier);
        conferma = v.findViewById(R.id.conferma);
        arrivalDate = v.findViewById(R.id.deliverD);
        sizeChoose = v.findViewById(R.id.sizeChoose);
        pickingAddress = v.findViewById(R.id.picking);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
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
        dialog.dismiss();
        if (!s.equals("")) Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        dialog.cancel();
        adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, curriersName);
        chooseCurrier.setAdapter(adapter);
        conferma.setOnClickListener(this);
        next.setText(nextId);
        arrivalDate.setText("24-12-2017");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.conferma) {
            String indirizzoDestinazione = address.getText().toString();
            String currierName = chooseCurrier.getSelectedItem().toString();
            String deliverDate = arrivalDate.getText().toString();
            String size = sizeChoose.getSelectedItem().toString();
            String pick = pickingAddress.getText().toString();
            String user = UtilitySharedPreference.getLoggedUsername(getActivity().getApplicationContext());
            if (!(indirizzoDestinazione.equals("") && currierName.equals("") && deliverDate.equals("") && pick.equals("") && user.equals(""))) {
                DatabaseReference pacchiSubTree = myRef.child("Pacchi").child(nextId);
                pacchiSubTree.child("Corriere").setValue(currierName);
                pacchiSubTree.child("DataConsegna").setValue(deliverDate);
                pacchiSubTree.child("Deposito").setValue(pick);
                pacchiSubTree.child("Destinatario").setValue(user);
                pacchiSubTree.child("Destinaione").setValue(indirizzoDestinazione);
                pacchiSubTree.child("Dimensione").setValue(size);
                pacchiSubTree.child("Stato").setValue("In Consegna");

                DatabaseReference corriereSubTree = myRef.child("Users").child("Corriere");
                corriereSubTree.child(currierName).child("Pacchi").child(nextId).setValue("blank");

                myRef.child("Users").child("Utente").child(user).child("Pacchi").child(nextId).setValue("blank");
                decodingFromAddress(pick);
                //AGGIUNGI MARKER ALLA MAPPA
                decodingFromAddress(indirizzoDestinazione);
                //AGGIUNGI MARKER ALLA MAPPA

            } else
                Toast.makeText(getActivity(), "Per aggiungere un pacco compilare tutti i campi", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private void decodingFromAddress(String address) {
        Geocoder coder = new Geocoder(getActivity());
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
            for (Address add : adresses) {
                if (!address.isEmpty()) {
                    longitude = add.getLongitude();
                    latitude = add.getLatitude();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

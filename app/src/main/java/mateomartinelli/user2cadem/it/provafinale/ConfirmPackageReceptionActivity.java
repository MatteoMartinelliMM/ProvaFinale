package mateomartinelli.user2cadem.it.provafinale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject;
import mateomartinelli.user2cadem.it.provafinale.Contoller.UtilitySharedPreference;
import mateomartinelli.user2cadem.it.provafinale.Model.Pacco;

import static mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject.PACK_TO_PASS;

public class ConfirmPackageReceptionActivity extends AppCompatActivity {
    private TextView destinatario, dataConsegna, indirizzoDestinazione, deposito, dimensioni, stato, packagedId, currierName;
    private ImageView packageImg;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    String idPacco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_package_reception);
        setTitle("Conferma di consegna a"+ UtilitySharedPreference.getUserReciver(this));

        db = FirebaseDatabase.getInstance();
        myRef = db.getReferenceFromUrl("https://provafinale-93015.firebaseio.com/Pacchi");

        Pacco pacco =(Pacco) RWObject.readObject(this, PACK_TO_PASS);

        settingXmlWidgets();
        fillInTheView(pacco);
    }

    public void conferma(View v){
        myRef.child(idPacco).child("Stato").setValue("Consegnato");
        Intent intent = new Intent();
        setResult(2,intent);
        finish();

    }

    private void fillInTheView(Pacco pacco) {
        String destination= pacco.getDestinazione();
        String paccoData = pacco.getData();
        String wareHouse = pacco.getDeposito();
        String sDestinatario = pacco.getDestinatario();
        String dim = pacco.getDimensione();
        String sStato = pacco.getStato();
        idPacco = pacco.getIdPacco();
        String nomeCorriere = pacco.getNomeCorriere();

        destinatario.setText(sDestinatario);
        dataConsegna.setText(paccoData);
        deposito.setText(wareHouse);
        dimensioni.setText(dim);
        indirizzoDestinazione.setText(destination);
        packagedId.setText(idPacco);
        loadBoxesDimensionImage(dim);
    }

    private void settingXmlWidgets() {
        destinatario = findViewById(R.id.destinatario);
        dataConsegna = findViewById(R.id.data);
        deposito = findViewById(R.id.deposito);
        dimensioni = findViewById(R.id.dimensioni);
        indirizzoDestinazione = findViewById(R.id.indirizzo);
        packagedId = findViewById(R.id.idPacco);
        currierName = findViewById(R.id.nomeCorriere);
        packageImg = findViewById(R.id.confirmImg);
    }

    private void loadBoxesDimensionImage(String dim) {
        switch (dim.toLowerCase()) {
            case "small":
                packageImg.setImageResource(R.drawable.box_small_confirm);
                break;
            case "medium":
                packageImg.setImageResource(R.drawable.box_medium_confirm);
                break;
            case "large":
                packageImg.setImageResource(R.drawable.box_large_confirm);
                break;
        }
    }
}

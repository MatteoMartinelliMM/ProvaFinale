package mateomartinelli.user2cadem.it.provafinale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject;
import mateomartinelli.user2cadem.it.provafinale.Contoller.UtilitySharedPreference;
import mateomartinelli.user2cadem.it.provafinale.Model.Corriere;
import mateomartinelli.user2cadem.it.provafinale.Model.Users;
import mateomartinelli.user2cadem.it.provafinale.Model.Utente;

import static mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject.LOGGED_USER;

public class RegisterActivity extends AppCompatActivity {

    EditText userName, pwd, pwd2;
    TextView noMatch;
    private Users utenti;
    private FirebaseDatabase db;
    private DatabaseReference refDb;
    private String sPwd, sUserName;
    private Spinner userType;
    private Users user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Sing Up");
        db = FirebaseDatabase.getInstance();
        refDb = db.getReferenceFromUrl("https://provafinale-93015.firebaseio.com/Users");
        userName = findViewById(R.id.userName);
        pwd = findViewById(R.id.choosePwd);
        pwd2 = findViewById(R.id.confirmPwd);
        noMatch = findViewById(R.id.noMatch);
        userType = findViewById(R.id.usersType);
    }

    public void done(View v){
        sPwd = pwd.getText().toString();
        String sPwd2 = pwd.getText().toString();
        sUserName = userName.getText().toString();
        String userTypeChoose = userType.getSelectedItem().toString();
        Intent intent;
        if(sPwd.equals(sPwd2)){

            if(userTypeChoose.equals("Corriere")) user = new Corriere(sUserName);
            else user = new Utente(sUserName);

            refDb.child(userTypeChoose).child(sUserName).child("pwd").setValue(sPwd);
            RWObject.writeObject(this, LOGGED_USER,user);
            Toast.makeText(this,"Registration succesfull",Toast.LENGTH_SHORT).show();
            UtilitySharedPreference.addLoggedUser(this, user);
            if(user instanceof Corriere)
                intent = new Intent(this,CorriereActivity.class);
            else
                intent = new Intent(this,UtenteActivity.class);
            startActivity(intent);
            finish();
        }else noMatch.setText("Password non corrispondenti");

    }
}

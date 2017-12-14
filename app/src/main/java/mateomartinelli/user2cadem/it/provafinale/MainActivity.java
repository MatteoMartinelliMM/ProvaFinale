package mateomartinelli.user2cadem.it.provafinale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject;
import mateomartinelli.user2cadem.it.provafinale.Contoller.UtilitySharedPreference;
import mateomartinelli.user2cadem.it.provafinale.Model.Corriere;
import mateomartinelli.user2cadem.it.provafinale.Model.Users;

import static mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject.LOGGED_USER;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent;
        if (UtilitySharedPreference.checkIfUserIsLogged(this)) {
            Users u = (Users) RWObject.readObject(this, LOGGED_USER);
            if (u instanceof Corriere) {
                intent = new Intent(this, CorriereActivity.class);
            }else{
                intent = new Intent(this, UtenteActivity.class);
            }
        } else intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

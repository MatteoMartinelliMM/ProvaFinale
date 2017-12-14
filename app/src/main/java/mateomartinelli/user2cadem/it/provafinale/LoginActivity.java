package mateomartinelli.user2cadem.it.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import mateomartinelli.user2cadem.it.provafinale.Contoller.JSONParser;
import mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject;
import mateomartinelli.user2cadem.it.provafinale.Contoller.RestCall;
import mateomartinelli.user2cadem.it.provafinale.Contoller.TaskWaiting;
import mateomartinelli.user2cadem.it.provafinale.Contoller.UtilitySharedPreference;
import mateomartinelli.user2cadem.it.provafinale.Model.Corriere;
import mateomartinelli.user2cadem.it.provafinale.Model.Users;
import mateomartinelli.user2cadem.it.provafinale.Model.Utente;

import static mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject.LOGGED_USER;

public class LoginActivity extends AppCompatActivity implements TaskWaiting {
    private RadioButton utente,corriere;
    private EditText userName, pwd;
    private ProgressDialog dialog;
    private TaskWaiting task;
    private boolean pwdMatching;
    private Users u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hidingTheTitleBar();
        setContentView(R.layout.activity_login);
        utente = findViewById(R.id.utente);
        corriere = findViewById(R.id.corriere);
        userName = findViewById(R.id.userName);
        pwd = findViewById(R.id.pwd);
        task = this;
        pwdMatching = false;
        dialog = new ProgressDialog(this);
    }

    public void register(View v){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }



    public void login(View v){
        dialog.onStart();
        String sUserName = userName.getText().toString();
        final String sPwd = pwd.getText().toString();
        String typeOfUser = "";
        boolean user = utente.isChecked();
        boolean curr = corriere.isChecked();
        if(!sUserName.equals("")){
            if(user) {
                typeOfUser = "Utente/";
                u = new Utente(sUserName);
            }else if(curr){
                typeOfUser = "Corriere/";
                u = new Corriere(sUserName);
            }
            dialog.onStart();
            checkNStartLogin(sUserName, sPwd, typeOfUser);
        }
    }

    private void checkNStartLogin(String sUserName, final String sPwd, String typeOfUser) {
        RestCall.get("Users/" + typeOfUser + sUserName + ".json", new AsyncHttpResponseHandler() {
            String pwdToCompare;
            Intent intent;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    String toParse = new String(responseBody);
                    pwdToCompare = JSONParser.getPwd(toParse);
                }
                task.waitToComplete("");
                if(pwdToCompare.equals(sPwd)){
                    Toast.makeText(getApplicationContext(),"Login Succesful",Toast.LENGTH_SHORT).show();
                    RWObject.writeObject(getApplicationContext(), LOGGED_USER,u);
                    UtilitySharedPreference.addLoggedUser(getApplicationContext(),u);
                    if(u instanceof Corriere) intent = new Intent(getApplicationContext(),CorriereActivity.class);
                    else intent = new Intent(getApplicationContext(),UtenteActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                task.waitToComplete("Username errato");
            }
        });
    }

    @Override
    public void waitToComplete(String s) {
        dialog.dismiss();
        if(!s.equals("")) Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        dialog.cancel();
    }
    private void hidingTheTitleBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }
}

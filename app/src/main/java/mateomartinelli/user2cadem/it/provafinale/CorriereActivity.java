package mateomartinelli.user2cadem.it.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mateomartinelli.user2cadem.it.provafinale.Contoller.FirebasePush;
import mateomartinelli.user2cadem.it.provafinale.Contoller.JSONParser;
import mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject;
import mateomartinelli.user2cadem.it.provafinale.Contoller.RestCall;
import mateomartinelli.user2cadem.it.provafinale.Contoller.SimplePackageAdapter;
import mateomartinelli.user2cadem.it.provafinale.Contoller.TaskWaiting;
import mateomartinelli.user2cadem.it.provafinale.Contoller.UtilitySharedPreference;
import mateomartinelli.user2cadem.it.provafinale.Model.Corriere;
import mateomartinelli.user2cadem.it.provafinale.Model.Pacco;

import static mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject.LOGGED_USER;

public class CorriereActivity extends AppCompatActivity implements TaskWaiting{
    private Corriere loggedCorriere;
    private RecyclerView assignedPackage;
    private ArrayList<Pacco> pacchi;
    private ProgressDialog dialog;
    private LinearLayoutManager lm;
    private SimplePackageAdapter packageAdapter;
    private ArrayList<String> idPacchi;
    private String classCaller;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==2)
                packageAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                UtilitySharedPreference.logoutTheCurrentUser(this);
                Intent intent = new Intent(this, LoginActivity.class);
                stopService(new Intent(this, FirebasePush.class));
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corriere);
        setTitle("Lista Pacchi");

        startToInizializeTheRecycleView();
        String userName = getTheLoggedCurrier();

        classCaller = getClass().getSimpleName();

        final TaskWaiting taskWaiting = this;
        dialog = new ProgressDialog(this);
        dialog.onStart();

        getTheCurrierPackageId(taskWaiting, userName);
    }

    private String getTheLoggedCurrier() {
        String userName = UtilitySharedPreference.getLoggedUsername(this);
        loggedCorriere = (Corriere) RWObject.readObject(this, LOGGED_USER);
        return userName;
    }

    private void startToInizializeTheRecycleView() {
        pacchi = new ArrayList<>();
        assignedPackage = findViewById(R.id.assignedPackage);
        lm = new LinearLayoutManager(this);
    }

    private void getTheCurrierPackageId(final TaskWaiting taskWaiting, String userName) {
        RestCall.get("Users/Corriere/"+userName+"/Pacchi.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    String toParse = new String(responseBody);
                    idPacchi = JSONParser.getCurriersIdPackages(toParse,getApplicationContext());
                    loggedCorriere.setIdPacchi(idPacchi);
                }
                if(loggedCorriere.getIdPacchi().size()!=0) {
                    getThePackageInfoFromRestCall(taskWaiting);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                taskWaiting.waitToComplete("ERROR: "+statusCode);
            }
        });
    }

    private void getThePackageInfoFromRestCall(final TaskWaiting taskWaiting) {
        RestCall.get("Pacchi.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String toParse = new String(responseBody);
                    pacchi = JSONParser.getCurriersPackagesToDeliver(toParse, loggedCorriere.getIdPacchi());
                }
                taskWaiting.waitToComplete("");
                packageAdapter = new SimplePackageAdapter(pacchi,classCaller);
                assignedPackage.setLayoutManager(lm);
                assignedPackage.setAdapter(packageAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                taskWaiting.waitToComplete("ERROR: " + statusCode);
            }
        });
    }

    @Override
    public void waitToComplete(String s) {
        dialog.dismiss();
        if(!s.equals("")) Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        dialog.cancel();
    }
}

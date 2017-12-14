package mateomartinelli.user2cadem.it.provafinale;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corriere);
        setTitle("Lista Pacchi");

        pacchi = new ArrayList<>();
        assignedPackage = findViewById(R.id.assignedPackage);
        lm = new LinearLayoutManager(this);

        final TaskWaiting taskWaiting = this;

        dialog = new ProgressDialog(this);
        dialog.onStart();

        String userName = UtilitySharedPreference.getLoggedUsername(this);
        loggedCorriere = (Corriere) RWObject.readObject(this, LOGGED_USER);

        RestCall.get("Users/Corriere/"+userName+"/Pacchi.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    String toParse = new String(responseBody);
                    idPacchi = JSONParser.getCurriersIdPackages(toParse,getApplicationContext());
                    loggedCorriere.setIdPacchi(idPacchi);
                }
                if(loggedCorriere.getIdPacchi().size()!=0) {
                    RestCall.get("Pacchi.json", new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200) {
                                String toParse = new String(responseBody);
                                pacchi = JSONParser.getCurriersPackagesToDeliver(toParse, loggedCorriere.getIdPacchi());
                            }
                            taskWaiting.waitToComplete("");
                            packageAdapter = new SimplePackageAdapter(pacchi);
                            assignedPackage.setLayoutManager(lm);
                            assignedPackage.setAdapter(packageAdapter);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            taskWaiting.waitToComplete("ERROR: " + statusCode);
                        }
                    });
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                taskWaiting.waitToComplete("ERROR: "+statusCode);
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

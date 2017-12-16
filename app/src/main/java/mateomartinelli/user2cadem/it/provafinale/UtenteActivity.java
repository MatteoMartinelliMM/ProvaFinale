package mateomartinelli.user2cadem.it.provafinale;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import mateomartinelli.user2cadem.it.provafinale.Contoller.FirebasePush;
import mateomartinelli.user2cadem.it.provafinale.Contoller.UtilitySharedPreference;

public class UtenteActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private float x1, x2;
    static final int MIN_DISTANCE = 150;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private boolean start = false;
    private BottomNavigationView navigation;
    private boolean done;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                done = UtilitySharedPreference.logoutTheCurrentUser(this);
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction ft = fm.beginTransaction();
                    ListaCorrieriFrag f = new ListaCorrieriFrag();
                    if (start)
                        ft.replace(R.id.fragContainer, f, "aggiungiPacco|listaPacchi");
                    else
                        ft.add(R.id.fragContainer, f, "listaCorrieri");
                    ft.commit();

                    return true;
                case R.id.navigation_dashboard:
                    ListaPacchiFrag f2 = new ListaPacchiFrag();
                    ft = fm.beginTransaction();
                    if (start)
                        ft.replace(R.id.fragContainer, f2, "listaCorrieri|aggiungiPacco");
                    else
                        ft.add(R.id.fragContainer, f2, "listaPacchi");
                    ft.commit();
                    return true;
                case R.id.navigation_notifications:
                    AggiungiPaccoFrag f3 = new AggiungiPaccoFrag();
                    ft = fm.beginTransaction();
                    if (start)
                        ft.replace(R.id.fragContainer, f3, "listaCorrieri|listaPacchi");
                    else
                        ft.add(R.id.fragContainer, f3, "aggiungiPacco");
                    ft.commit();
                    return true;
            }
            return false;
        }
    };

  /*  @Override

    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if (x2 > x1) {
                        Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show();
                        int currentFrame = navigation.getSelectedItemId();
                        switch (currentFrame) {
                            case R.id.navigation_dashboard:
                                navigation.setSelectedItemId(R.id.navigation_home);
                                return true;
                            case R.id.navigation_notifications:
                                navigation.setSelectedItemId(R.id.navigation_dashboard);
                                return true;
                        }

                    }
                } else {
                    Toast.makeText(this, "right2left swipe", Toast.LENGTH_SHORT).show();
                    int currentFrame = navigation.getSelectedItemId();

                    switch (currentFrame) {
                        case R.id.navigation_home:
                            navigation.setSelectedItemId(R.id.navigation_dashboard);
                            return true;
                        case R.id.navigation_dashboard:
                            navigation.setSelectedItemId(R.id.navigation_notifications);
                            return true;
                    }

                }
        }
        return super.dispatchTouchEvent(event);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente);
        fm = getFragmentManager();
        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        start = true;


    }


}

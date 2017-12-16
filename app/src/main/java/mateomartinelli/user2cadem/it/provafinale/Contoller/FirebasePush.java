package mateomartinelli.user2cadem.it.provafinale.Contoller;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mateomartinelli.user2cadem.it.provafinale.CorriereActivity;
import mateomartinelli.user2cadem.it.provafinale.Model.Corriere;
import mateomartinelli.user2cadem.it.provafinale.Model.Users;
import mateomartinelli.user2cadem.it.provafinale.R;
import mateomartinelli.user2cadem.it.provafinale.UtenteActivity;

import static mateomartinelli.user2cadem.it.provafinale.Contoller.RWObject.LOGGED_USER;

/**
 * Created by utente2.academy on 12/15/2017.
 */

public class FirebasePush extends Service {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersReference = database.getReferenceFromUrl("https://provafinale-93015.firebaseio.com/Pacchi");
    private ChildEventListener handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        handler = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    activePushValidation(dataSnapshot.getKey());
                }
            }


            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        usersReference.addChildEventListener(handler);
    }

    public void activePushValidation(String commListener) {
        Users u = (Users) RWObject.readObject(getApplicationContext(), LOGGED_USER);
        Intent intent;
        if(u instanceof Corriere)
            intent = new Intent(this, CorriereActivity.class);
        else
            intent = new Intent(this, UtenteActivity.class);
        sendNotification(intent, "Modifica pacco avvenuta con successo", commListener);

    }

    public void sendNotification(Intent intent, String title, String body) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.truck_delivery);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri dsUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setAutoCancel(true);
        builder.setSound(dsUri);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(bitmap);
        builder.setShowWhen(true);
        builder.setContentIntent(activity);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


}

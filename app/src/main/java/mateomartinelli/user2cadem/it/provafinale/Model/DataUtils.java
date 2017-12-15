package mateomartinelli.user2cadem.it.provafinale.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by utente2.academy on 12/15/2017.
 */

public class DataUtils {

    public static String getDeliverDate(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Random rand = new Random();
        int offset = rand.nextInt(5);
        String dayOffset = Integer.toString(offset);
        String deliverDate = dt.format(currentTime);
        String split[] = deliverDate.split("-");
        for(int i = 0; i<split.length ; i++){
            Integer unity = Integer.parseInt(split[i]);
            Integer delta = Integer.parseInt(dayOffset);
            if(unity<31) {
                unity = delta+unity;
                int year = Integer.parseInt(split[2]);
                int month = Integer.parseInt(split[1]);
                currentTime = new Date(year,month,unity);
                deliverDate = dt.format(currentTime);
                break;
            }
            else break;

        }
        return deliverDate;
    }
}

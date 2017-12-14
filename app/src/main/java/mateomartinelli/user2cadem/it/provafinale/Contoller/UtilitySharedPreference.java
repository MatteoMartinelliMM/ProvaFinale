package mateomartinelli.user2cadem.it.provafinale.Contoller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import mateomartinelli.user2cadem.it.provafinale.Model.Users;

/**
 * Created by utente2.academy on 12/14/2017.
 */

public class UtilitySharedPreference {


        public static final String LOGGED_USER = "LoggedUser";
        public static final String USER_TYPE = "UserType";
    public static final String LAST_PACKAGE_TO_DELIVER = "lastPackageToDeliver";


    public static boolean checkIfUserIsLogged(Context context){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.contains(LOGGED_USER);
        }

        public static void addLoggedUser(Context context, Users u){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(LOGGED_USER,u.getUserName());
            editor.commit();
        }

        public static String getLoggedUsername(Context context){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(LOGGED_USER,"");
        }

        public static void saveUserType(Context context, String groupName){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(USER_TYPE,groupName);
            editor.commit();
        }



        public static String getUserType(Context context){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(USER_TYPE,"");
        }

        public static void removeLoggedUser(Context context){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(LOGGED_USER);
            editor.commit();
        }

        public static String getLastDeliverId(Context context,String groupName){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(groupName,"");
        }

        public static void saveLastDeliverId(Context context, String id){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(LAST_PACKAGE_TO_DELIVER,id);
            editor.commit();
        }
        public static String getLastCommentId(Context context){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString("lastId","");
        }

        public static void saveLastCommentId(Context context, String id){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("lastId",id);
            editor.commit();
        }
}

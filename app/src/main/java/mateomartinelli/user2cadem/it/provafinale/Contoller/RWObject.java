package mateomartinelli.user2cadem.it.provafinale.Contoller;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by utente2.academy on 12/14/2017.
 */

public class RWObject {
    public final static String SAVE_POST = "savedPost";
    public final static String LOGGED_USER = "loggedUser";
    public static Object readObject(Context context, String fileName) {
        FileInputStream inputStream = null;
        ObjectInputStream objReader = null;
        Object readedObject = null;
        try {
            inputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = objReader = new ObjectInputStream(inputStream);
            readedObject = objReader.readObject();
            objectInputStream.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return readedObject;
    }

    public static void writeObject(Context context, String fileName, Object toWrite) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectWriter = null;
        try {
            fileOutputStream = context.openFileOutput(fileName, context.MODE_PRIVATE);
            objectWriter = new ObjectOutputStream(fileOutputStream);
            objectWriter.writeObject(toWrite);
            objectWriter.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

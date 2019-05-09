package net.beingup.simpletodo.pippo;

import org.json.JSONException;

import java.io.IOException;

public interface exceptions {
    /**
     * This varibales store exceptions message text data.
     */
    // message for IO
    String ms101 = "Android OS can not create binary files for the database.";
    String ms102 = "Java IO system do not work properly when the system tries to read database binary files.";
    String ms103 = "Java IO system do not work properly when the system tries to write database binary files.";

    // message for JSON
    String ms201 = "Unreadable database binary files. It may be corrupted.";
    String ms202 = "Pool default structure can not create right now.";


    /**
     * @param i close number
     * @param e exception
     * @param i1 message number
     * @param s text message
     *
     * These methods give exceptions and other info.
     */
    void IOException(int i, IOException e, int i1, String s);
    void JSONException(int i, JSONException e, int i1, String s);
}

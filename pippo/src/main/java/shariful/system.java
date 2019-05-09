package net.beingup.simpletodo.pippo;

import android.content.Context;
import android.content.ContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class system implements exceptions{
    private Context context;

    /**
     * This variable store data as a close request.
     * It will be updated when the user invokes <mark>close(int)</mark> method.
     * The system will return this value with exceptions.
     */
    int cn = 0;

    // constructor
    system(Context context){this.context = context;}

    /**
     * This method used for reading database binary files from the .android folder.
     *
     * @param i database define number
     *
     * @return target database as JSONObject
     */
    JSONObject rbf(int i){
        ContextWrapper cw = new ContextWrapper(context);
        File dc = cw.getDir("pippo", Context.MODE_PRIVATE);
        File f;
        switch (i){
            case 0: f = new File(dc, K.kd); break;
            case 1: f = new File(dc, K.sd); break;
            default: f = new File(dc, K.pd);
        }
        int l = (int) f.length();
        byte[] bytes = new byte[l];
        try {
            FileInputStream fns = new FileInputStream(f);
            try {
                fns.read(bytes);
                try{
                    return new JSONObject(new String(bytes));
                }
                catch (JSONException e){
                    JSONException(cn, e, 301, ms201);
                    return new JSONObject();
                }
            } finally {
                fns.close();
            }
        }
        catch (FileNotFoundException e){
            cf(i);
            // create default pool structure
            if(i==2){
                try{return new JSONObject(K.st2);}
                catch (JSONException e1){JSONException(cn, e1, 303, ms202);}
            }
            return new JSONObject();
        }
        catch (IOException e){
            IOException(cn, e, 202, ms102);
            return new JSONObject();
        }
    }

    /**
     * This method used for write updated data into the target database binary file.
     *
     * @param i database define number
     * @param ob updated data for target database
     *
     * @return if success returns true, else return false and invoke exceptions.
     */
    boolean wbf(int i, JSONObject ob){
        ContextWrapper cw = new ContextWrapper(context);
        File dc = cw.getDir("pippo", Context.MODE_PRIVATE);
        File f;
        switch (i){
            case 0: f = new File(dc, K.kd); break;
            case 1: f = new File(dc, K.sd); break;
            default: f = new File(dc, K.pd);
        }
        try {
            FileOutputStream s = new FileOutputStream(f);
            s.write(ob.toString().getBytes());
            s.close();
            return true;
        }
        catch (IOException e) {
            IOException(cn, e, 203, ms103);
            return false;
        }
    }

    /**
     * This method used to creating binary files for the databases.
     *
     * @param i database json file define number
     */
    private void cf(int i){
        ContextWrapper cw = new ContextWrapper(context);
        File dc = cw.getDir("pippo", Context.MODE_PRIVATE);
        File f;
        switch(i){
            case 0: f = new File(dc, K.kd); break;
            case 1: f = new File(dc, K.sd); break;
            default: f = new File(dc, K.pd);
        }
        try {
            FileOutputStream s = new FileOutputStream(f);
            switch(i){
                case 0:
                case 1:
                    s.write(K.st1.getBytes());
                    break;
                case 2: s.write(K.st2.getBytes());
            }
            s.close();
        }
        catch (IOException e) {
            IOException(cn, e, 201, ms101);
        }
    }

    @Override
    public void IOException(int i, IOException e, int i1, String s) {}

    @Override
    public void JSONException(int i, JSONException e, int i1, String s) {}
}

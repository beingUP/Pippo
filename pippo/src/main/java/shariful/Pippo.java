package net.beingup.simpletodo.pippo;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pippo extends process{

    private Query _query;

    private final JSONArray ar = new JSONArray();
    private final JSONObject ob = new JSONObject();
    private final ArrayList<Boolean> a0 = new ArrayList<>();
    private final ArrayList<Integer> a1 = new ArrayList<>();
    private final ArrayList<Double> a2 = new ArrayList<>();
    private final ArrayList<String> a3 = new ArrayList<>();
    private final ArrayList<JSONArray> a4 = new ArrayList<>();
    private final ArrayList<JSONObject> a5 = new ArrayList<>();

    /**
     * This is primary constructor.
     *
     *
     * If a user uses this, the keep, store and pool database will be active for action and every time when the user updates data, the binary database files will be updated in real-time.
     *
     * #Advantage
     * Do more, write less code.
     */
    public Pippo(Context context) {
        super(context);
        _query = new Query(context);
    }

    /**
     * This is closeable constructor.
     *
     * @param isClose true for active the close() method.
     *
     * If a user uses this as <mark>isClose == true</mark>, the keep, store and pool database will be active for action but the binary database files will not be updated in real time.
     * In this case, the user needs to invoke the <mark>close()</mark> method to save all updated data.
     *
     * #Advantage
     * Work super fast!
     * -> If a user needs to store a lot number of data again and again (like a loop) then we suggest using this constructor.
     * -> After the looping work the user just invoke the <mark>close()</mark> method. It will work super fast and CPU will be minimum busy.
     */
    public Pippo(Context context, boolean isClose) {
        super(context);
        c = isClose;
        _query = new Query(context);
    }

    /**
     * This is RAM friendly constructor.
     *
     * @param keep true for active keep database.
     * @param store true for active store database.
     * @param pool true for active pool database.
     *
     * Using this, the user can select a specific database to work with it.
     * -> Think, a user wants to work with only <b>store database</b>. Then not need to activate the <b>keep and pool database</b>.
     * -> In this case, user can create object like <mark>new Pippo(this, false, true, false);</mark>.
     *
     * #Advantage
     * Work using minimum RAM.
     */
    public Pippo(Context context, boolean keep, boolean store, boolean pool){
        super(context);
        k = keep;
        s = store;
        p = pool;
        _query = new Query(context);
    }

    /**
     * This is RAM friendly closeable constructor.
     *
     * @param keep true for active keep database.
     * @param store true for active store database.
     * @param pool true for active pool database.
     * @param isClose true for active the close() method.
     *
     * Using this, the user can select a specific database to work with it.
     * -> Think, a user wants to work with only <b>store database</b>. Then not need to activate the <b>keep and pool database</b>.
     * -> In this case, a user can create an object like,
     * ---> new Pippo(this, false, true, false, false); [for real-time update data]
     * ---> new Pippo(this, false, true, false, true); [for super fast insert a lot number of data]
     *
     * #Advantage
     * Work using minimum RAM and less presser to CPU.
     */
    public Pippo(Context context, boolean keep, boolean store, boolean pool, boolean isClose){
        super(context);
        k = keep;
        s = store;
        p = pool;
        c = isClose;
        _query = new Query(context);
    }

    /**
     * The refresh method work for refresh database.
     *
     * * when it will use?
     * If user create more then one pippo object and want to transfer data from one pippo object to another then
     * user need to invoke this method before send and get updated data.
     */
    public void refresh(){
        start(true, true, true);
    }
    public void refreshKeep(){
        start(true, false, false);
    }
    public void refreshStore(){
        start(false, true, false);
    }
    public void refreshPool(){
        start(false, false, true);
    }

    /**
     * These methods are used for store data into keep database.
     *
     * * @param group data group name
     * * @param name data name
     * * @param boo boolean data
     * * @param num integer data
     * * @param double_num double data
     * * @param data string data
     * * @param array JSONArray data
     * * @param object JSONObject data
     *
     * @return if success true, else false.
     */
    // boolean
    public boolean keep(String group, String name, boolean boo){
        return kpd(group, name, boo, 0, 0.0, "", ar, ob, 0);
    }
    public boolean keep(String name, boolean boo){
        return kpd(K.un, name, boo, 0, 0.0, "", ar, ob, 0);
    }
    public boolean keep(boolean boo){
        return kpd(K.un, K.un, boo, 0, 0.0, "", ar, ob, 0);
    }
    // integer
    public boolean keep(String group, String name, int num){
        return kpd(group, name, false, num, 0.0, "", ar, ob, 1);
    }
    public boolean keep(String name, int num){
        return kpd(K.un, name, false, num, 0.0, "", ar, ob, 1);
    }
    public boolean keep(int num){
        return kpd(K.un, K.un, false, num, 0.0, "", ar, ob, 1);
    }
    // double
    public boolean keep(String group, String name, double double_num){
        return kpd(group, name, false, 0, double_num, "", ar, ob, 2);
    }
    public boolean keep(String name, double double_num){
        return kpd(K.un, name, false, 0, double_num, "", ar, ob, 2);
    }
    public boolean keep(double double_num){
        return kpd(K.un, K.un, false, 0, double_num, "", ar, ob, 2);
    }
    // String
    public boolean keep(String group, String name, String data){
        return kpd(group, name, false, 0, 0.0, data, ar, ob, 3);
    }
    public boolean keep(String name, String data){
        return kpd(K.un, name, false, 0, 0.0, data, ar, ob, 3);
    }
    public boolean keep(String data){
        return kpd(K.un, K.un, false, 0, 0.0, data, ar, ob, 3);
    }
    // JSONArray
    public boolean keep(String group, String name, JSONArray array){
        return kpd(group, name, false, 0, 0.0, "", array, ob, 4);
    }
    public boolean keep(String name, JSONArray array){
        return kpd(K.un, name, false, 0, 0.0, "", array, ob, 4);
    }
    public boolean keep(JSONArray array){
        return kpd(K.un, K.un, false, 0, 0.0, "", array, ob, 4);
    }
    // JSONObject
    public boolean keep(String group, String name, JSONObject object){
        return kpd(group, name, false, 0, 0.0, "", ar, object, 5);
    }
    public boolean keep(String name, JSONObject object){
        return kpd(K.un, name, false, 0, 0.0, "", ar, object, 5);
    }
    public boolean keep(JSONObject object){
        return kpd(K.un, K.un, false, 0, 0.0, "", ar, object, 5);
    }

    /**
     * These methods used to getting data form <i>keep database<i> when user store data without group name and data name.
     */
    public boolean getBoolean(){
        try{return kd.getJSONObject(K.un).getBoolean(K.un);}
        catch(JSONException e){return false;}
    }
    public int getInt(){
        try{return kd.getJSONObject(K.un).getInt(K.un);}
        catch(JSONException e){return di;}
    }
    public double getDouble(){
        try{return kd.getJSONObject(K.un).getDouble(K.un);}
        catch(JSONException e){return dd;}
    }
    public String getString(){
        try{return kd.getJSONObject(K.un).getString(K.un); }
        catch(JSONException e){return ds;}
    }
    public JSONArray getJSONArray(){
        try{return kd.getJSONObject(K.un).getJSONArray(K.un);}
        catch(JSONException e){return dja;}
    }
    public JSONObject getJSONObject(){
        try{return kd.getJSONObject(K.un).getJSONObject(K.un);}
        catch(JSONException e){return dj;}
    }


    /**
     * These methods used to getting data form <i>keep database</i> when user store data without group name.
     * 
     * @param name data name
     *
     * @return data from keep database.
     */
    public boolean getBoolean(String name){
        try{return kd.getJSONObject(K.un).getBoolean(name);}
        catch(JSONException e){return false;}
    }
    public int getInt(String name){
        try{return kd.getJSONObject(K.un).getInt(name);}
        catch(JSONException e){return di;}
    }
    public double getDouble(String name){
        try{return kd.getJSONObject(K.un).getDouble(name);}
        catch(JSONException e){return dd;}
    }
    public String getString(String name){
        try{return kd.getJSONObject(K.un).getString(name); }
        catch(JSONException e){return ds;}
    }
    public JSONArray getJSONArray(String name){
        try{return kd.getJSONObject(K.un).getJSONArray(name);}
        catch(JSONException e){return dja;}
    }
    public JSONObject getJSONObject(String name){
        try{return kd.getJSONObject(K.un).getJSONObject(name);}
        catch(JSONException e){return dj;}
    }


    /**
     * These methods are used for store data into store database.
     *
     * * @param address data storage address.
     * * @param boo boolean data
     * * @param num integer data
     * * @param double_num double data
     * * @param data string data
     * * @param array JSONArray data
     * * @param object JSONObject data
     * * @param override true for override old data
     *
     * @return if success true, else false.
     */
    // boolean
    public boolean store(String address, boolean boo){
        return spd(address, boo, 0, 0.0, "", ar, ob, false, 0);
    }
    public boolean store(String address, boolean boo, boolean override){
        return spd(address, boo, 0, 0.0, "", ar, ob, override, 0);
    }
    // int
    public boolean store(String address, int num){
        return spd(address, false, num, 0.0, "", ar, ob, false, 1);
    }
    public boolean store(String address, int num, boolean override){
        return spd(address, false, num, 0.0, "", ar, ob, override, 1);
    }
    // double
    public boolean store(String address, double double_num){
        return spd(address, false, 0, double_num, "", ar, ob, false, 2);
    }
    public boolean store(String address, double double_num, boolean override){
        return spd(address, false, 0, double_num, "", ar, ob, override, 2);
    }
    // String
    public boolean store(String address, String text){
        return spd(address, false, 0, 0.0, text, ar, ob, false, 3);
    }
    public boolean store(String address, String text, boolean override){
        return spd(address, false, 0, 0.0, text, ar, ob, override, 3);
    }
    // JSONArray
    public boolean store(String address, JSONArray array){
        return spd(address, false, 0, 0.0, "", array, ob, false, 4);
    }
    public boolean store(String address, JSONArray array, boolean override){
        return spd(address, false, 0, 0.0, "", array, ob, override, 4);
    }
    // JSONObject
    public boolean store(String address, JSONObject object){
        return spd(address, false, 0, 0.0, "", ar, object, false, 5);
    }
    public boolean store(String address, JSONObject object, boolean override){
        return spd(address, false, 0, 0.0, "", ar, object, override, 5);
    }


    /**
     * These methods used to store data into pool database.
     *
     * * @param name data name
     * * @param boo boolean data
     * * @param num integer data
     * * @param double_num double data
     * * @param data string data
     * * @param array JSONArray data
     * * @param object JSONObject data
     * * @param isStabel true for <b>pool stable database</b>, false for <b>pool non stable database</b>.
     *
     * @return if success true, else false.
     */
    public boolean pool(String name, boolean boo, boolean isStable){
        if(isStable) return ips(name, boo, 0, 0.0, "", ar, ob, 0);
        return ipns(name, boo, 0, 0.0, "", ar, ob, 0);
    }
    public boolean pool(String name, int num, boolean isStable){
        if(isStable) return ips(name, false, num, 0.0, "", ar, ob, 1);
        return ipns(name, false, num, 0.0, "", ar, ob, 1);
    }
    public boolean pool(String name, double double_num, boolean isStable){
        if(isStable) return ips(name, false, 0, double_num, "", ar, ob, 2);
        return ipns(name, false, 0, double_num, "", ar, ob, 2);
    }
    public boolean pool(String name, String data, boolean isStable){
        if(isStable) return ips(name, false, 0, 0.0, data, ar, ob, 3);
        return ipns(name, false, 0, 0.0, data, ar, ob, 3);
    }
    public boolean pool(String name, JSONArray array, boolean isStable){
        if(isStable) return ips(name, false, 0, 0.0, "", array, ob, 4);
        return ipns(name, false, 0, 0.0, "", array, ob, 4);
    }
    public boolean pool(String name, JSONObject object, boolean isStable){
        if(isStable) return ips(name, false, 0, 0.0, "", ar, object, 5);
        return ipns(name, false, 0, 0.0, "", ar, object, 5);
    }


    /**
     * These methods used to update data into pool database.
     *
     * * @param name data name
     * * @param boo boolean data
     * * @param num integer data
     * * @param double_num double data
     * * @param data string data
     * * @param array JSONArray data
     * * @param object JSONObject data
     * * @param index pool data index number
     * * @param isStabel true for <b>pool stable database</b>, false for <b>pool non stable database</b>.
     *
     * @return if success true, else false.
     */
    public boolean updatePool(String name, boolean boo, int index, boolean isStable){
        return up(boo, 0, 0.0, "", ar, ob, name, index, isStable, 0);
    }
    public boolean updatePool(String name, int num, int index, boolean isStable){
        return up(false, num, 0.0, "", ar, ob, name, index, isStable, 1);
    }
    public boolean updatePool(String name, double double_num, int index, boolean isStable){
        return up(false, 0, double_num, "", ar, ob, name, index, isStable, 2);
    }
    public boolean updatePool(String name, String data, int index, boolean isStable){
        return up(false, 0, 0.0, data, ar, ob, name, index, isStable, 3);
    }
    public boolean updatePool(String name, JSONArray array, int index, boolean isStable){
        return up(false, 0, 0.0, "", array, ob, name, index, isStable, 4);
    }
    public boolean updatePool(String name, JSONObject object, int index, boolean isStable){
        return up(false, 0, 0.0, "", ar, object, name, index, isStable, 5);
    }

    /**
     * Those methods used to update full pool data
     */
    public boolean updatePoolBooleans(String name, ArrayList<Boolean> boo, boolean isStable){
        return up(boo, a1, a2, a3, a4, a5, name, isStable, 0);
    }
    public boolean updatePoolIntegers(String name, ArrayList<Integer> num, boolean isStable){
        return up(a0, num, a2, a3, a4, a5, name, isStable, 1);
    }
    public boolean updatePoolDoubles(String name, ArrayList<Double> double_num, boolean isStable){
        return up(a0, a1, double_num, a3, a4, a5, name, isStable, 2);
    }
    public boolean updatePoolStrings(String name, ArrayList<String> data, boolean isStable){
        return up(a0, a1, a2, data, a4, a5, name, isStable, 3);
    }
    public boolean updatePoolJSONArrays(String name, ArrayList<JSONArray> array, boolean isStable){
        return up(a0, a1, a2, a3, array, a5, name, isStable, 4);
    }
    public boolean updatePoolJSONObjects(String name, ArrayList<JSONObject> object, boolean isStable){
        return up(a0, a1, a2, a3, a4, object, name, isStable, 5);
    }

    /**
     * This method used to create system query from user's string query.
     */
    public Query query(String query_text){
        _query.create_query(query_text);
        return _query;
    }

    /**
     * These methods used to get data form store database using query.
     *
     * @param query Query class
     * @return If user want to single JSON key data then these methods will return data as ArrayList.
     * On the other-hand the 'getStoreMultiDataList()' method will return data as JSONArray.
     */
    public ArrayList<Boolean> getStoreBooleanList(Query query){
        if (query.isQueryOk){
            return query.out_bool(sd);
        }
        else return new ArrayList<>();
    }

    public ArrayList<Integer> getStoreIntList(Query query){
        if (query.isQueryOk){
            return query.out_int(sd);
        }
        else return new ArrayList<>();
    }

    public ArrayList<Double> getStoreDoubleList(Query query){
        if (query.isQueryOk){
            return query.out_dub(sd);
        }
        else return new ArrayList<>();
    }

    public ArrayList<String> getStoreStringList(Query query){
        if (query.isQueryOk){
            return query.out_str(sd);
        }
        else return new ArrayList<>();
    }

    public ArrayList<JSONArray> getStoreJSONArrayList(Query query){
        if (query.isQueryOk){
            return query.out_arr(sd);
        }
        else return new ArrayList<>();
    }

    public ArrayList<JSONObject> getStoreJSONObjectList(Query query){
        if (query.isQueryOk){
            return query.out_ob(sd);
        }
        else return new ArrayList<>();
    }

    public JSONArray getStoreMultiDataList(Query query){
        if (query.isQueryOk){
            return query.out_col(sd);
        }
        else return new JSONArray();
    }


    /**
     * These methods used to get data form pool database using query.
     * Note : These methods will be return any value if pool data is JSONObject or JSONArray.
     *
     * @param query Query class
     * @param isStable true for stable pool tree and false for non-stable pool tree.
     * @return If user want to single JSON key data then these methods will return data as ArrayList.
     * On the other-hand the 'getPoolMultiDataList()' method will return data as JSONArray.
     */
    public ArrayList<Boolean> getPoolBooleanList(Query query, boolean isStable){
        if (query.isQueryOk){
            JSONObject ob = rapingPoolArray(isStable, query.link);
            // System created a auto link for JSONArray into rapingPoolArray() method.
            // Here system update query link like auto link.
            query.link = "data[]";
            return query.out_bool(ob);
        }
        else return new ArrayList<>();
    }

    public ArrayList<Integer> getPoolIntList(Query query, boolean isStable){
        if (query.isQueryOk){
            JSONObject ob = rapingPoolArray(isStable, query.link);
            query.link = "data[]";
            return query.out_int(ob);
        }
        else return new ArrayList<>();
    }

    public ArrayList<Double> getPoolDoubleList(Query query, boolean isStable){
        if (query.isQueryOk){
            JSONObject ob = rapingPoolArray(isStable, query.link);
            query.link = "data[]";
            return query.out_dub(ob);
        }
        else return new ArrayList<>();
    }

    public ArrayList<String> getPoolStringList(Query query, boolean isStable){
        if (query.isQueryOk){
            JSONObject ob = rapingPoolArray(isStable, query.link);
            query.link = "data[]";
            return query.out_str(ob);
        }
        else return new ArrayList<>();
    }

    public ArrayList<JSONArray> getPoolJSONArrayList(Query query, boolean isStable){
        if (query.isQueryOk){
            JSONObject ob = rapingPoolArray(isStable, query.link);
            query.link = "data[]";
            return query.out_arr(ob);
        }
        else return new ArrayList<>();
    }

    public ArrayList<JSONObject> getPoolJSONObjectList(Query query, boolean isStable){
        if (query.isQueryOk){
            JSONObject ob = rapingPoolArray(isStable, query.link);
            query.link = "data[]";
            return query.out_ob(ob);
        }
        else return new ArrayList<>();
    }

    public JSONArray getPoolMultiDataList(Query query, boolean isStable){
        if (query.isQueryOk){
            JSONObject ob = rapingPoolArray(isStable, query.link);
            query.link = "data[]";
            return query.out_col(ob);
        }
        else return new JSONArray();
    }
}

package net.beingup.simpletodo.pippo;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class process extends system {
    /**
     * c -> isCloseAble
     * k -> isKeepInvoke
     * s -> isStoreInvoke
     * p -> isPoolInvoke
     *
     * These booleans (without <mark>c</mark>) give permission to activate the covetable database. Like, keep, store and pool database.
     * Boolean <mark>k</mark> for keep, <mark>s</mark> for store and <mark>p</mark> for pool.
     * If k, s and p boolean are <mark>true</mark>, the covetable database will active for action.
     *
     * The boolean <mark>c</mark> give permission to save data into the deep database (<i>binary files</i>).
     * If <mark>c == false</mark> the deep database will update real time when the user edits any part of a database.
     * On the other hand, if <mark>c == true</mark> the deep database will update when the user invokes the <mark>close()</mark> method.
     * If <mark>c == true</mark>, user can use and edit data without invoke <mark>close()</mark> method.
     * In this case, if user change activity without invoking <mark>close()</mark> method, the user will lose the update data.
     */
    boolean c, k = true, s = true, p = true;

    /**
     * kd -> keep database
     * sd -> store database
     * pd -> pool database
     *
     * These JSON objects store database when user create <mark>new Pippo()</mark> object on their activity.
     * In <u>section header 1</u> if <mark>k == true</mark> then <mark>kd</mark> store keep database. Like this <mark>sp</mark> and <mark>pd</mark> store database when <mark>s == true</mark> and <mark>p == true</mark>.
     */
    JSONObject kd, sd;
    private JSONObject pd;

    /**
     * The <mark>ic</mark> boolean give permission to <mark>clear()</mark> method for clear all database from system ram.
     * The <mark>clear()</mark> method will work when <mark>ic == true</mark>.
     * By default <mark>ic = false</mark>. If user invoke <mark>close()</mark> method the <mark>ic</mark> will be true.
     */
    private boolean ic;

    /**
     * These are the five default data return Pippo when user want a data and there are no covetable data for the user on the database.
     *
     * di -> default int
     * dd -> default double
     * ds -> default string
     * dja -> default JSON Array
     * dj -> default JSON Object
     */
    int di = 0;
    double dd = 0.0;
    String ds = "_0";
    JSONArray dja = new JSONArray();
    JSONObject dj = new JSONObject();

    process(Context context) {
        super(context);
        start(k, s, p);
    }

    /**
     * These methods used for set default result.
     * Note : boolean default data always false. User can't set boolean default data.
     */
    public void Default(int di){this.di=di;}
    public void Default(double dd){this.dd=dd;}
    public void Default(String ds){this.ds=ds;}
    public void Default(JSONArray dja){this.dja=dja;}
    public void Default(JSONObject dj){this.dj=dj;}

    /**
     * These methods used for check existence of keep group and data
     * @param group group name
     * @param name data name
     * @return true if exist else false.
     */
    public boolean isKeepExist(String group, String name){
        try{
            kd.getJSONObject(group).get(name);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean isKeepExist(String name){
        try{
            kd.getJSONObject(K.un).get(name);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean isKeepGroupExist(String group){
        try{
            kd.get(group);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * This method return all data from <i>keep database</i> as JSONObject.
     */
    public JSONObject getKeep(){return kd;}

    /**
     * These methods used to getting data from <i>keep database<i> with group and data name.
     *
     * @param group data group name
     * @param name data name
     *
     * @return data form keep database
     */
    public boolean getBoolean(String group, String name){
        try{return kd.getJSONObject(group).getBoolean(name);}
        catch(JSONException e){return false;}
    }
    public int getInt(String group, String name){
        try{return kd.getJSONObject(group).getInt(name);}
        catch(JSONException e){return di;}
    }
    public double getDouble(String group, String name){
        try{return kd.getJSONObject(group).getDouble(name);}
        catch(JSONException e){return dd;}
    }
    public String getString(String group, String name){
        try{return kd.getJSONObject(group).getString(name); }
        catch(JSONException e){return ds;}
    }
    public JSONArray getJSONArray(String group, String name){
        try{return kd.getJSONObject(group).getJSONArray(name);}
        catch(JSONException e){return dja;}
    }
    public JSONObject getJSONObject(String group, String name){
        try{return kd.getJSONObject(group).getJSONObject(name);}
        catch(JSONException e){return dj;}
    }

    /**
     * These methods use for delete data from <i>keep database<i>.
     *
     * @param group data group name
     * @param name data name
     *
     * @return true if success else return false.
     */
    public boolean deleteKeep(String group, String name){
        try{
            kd.getJSONObject(group).remove(name);
            return save(0, kd, c);
        }
        catch (JSONException e){return false;}
    }
    public boolean deleteKeep(String name){
        try{
            kd.getJSONObject(K.un).remove(name);
            return save(0, kd, c);
        }
        catch (JSONException e){return false;}
    }
    public boolean deleteKeepGroup(String group){
        kd.remove(group);
        return save(0, kd, c);
    }
    public boolean deleteKeepDefaultGroup(){
        kd.remove(K.un);
        return save(0, kd, c);
    }

    /**
     * This method used to keep data into <i>keep database</i>.
     *
     * @param g group name
     * @param n data name
     * @param b boolean data
     * @param i integer data
     * @param i1 double data
     * @param s string data
     * @param a JSONArray data
     * @param ob JSONObject data
     * @param i2 data type number.
     *
     * @return true if success else return false.
     */
    boolean kpd(String g, String n, boolean b, int i, double i1, String s, JSONArray a, JSONObject ob, int i2){
        try{
            JSONObject cd = kd.getJSONObject(g);
            switch(i2){
                case 0: cd.put(n, b); break;
                case 1: cd.put(n, i); break;
                case 2: cd.put(n, i1); break;
                case 3: cd.put(n, s); break;
                case 4: cd.put(n, a); break;
                case 5: cd.put(n, ob);
            }
            return save(0, kd, c);
        }
        catch (Exception e){
            try{
                JSONObject nc = new JSONObject();
                switch(i2){
                    case 0: nc.put(n, b); break;
                    case 1: nc.put(n, i); break;
                    case 2: nc.put(n, i1); break;
                    case 3: nc.put(n, s); break;
                    case 4: nc.put(n, a); break;
                    case 5: nc.put(n, ob);
                }
                kd.put(g, nc);
                return save(0, kd, c);
            }
            catch(JSONException e2){
                return false;
            }
        }
    }

    /**
     * This method used for check new store. If the <mark>store name</mark> is exist it will return <mark>true</mark>. Else it will return <mark>false</mark>.
     *
     * @param name store name
     * @return true if the store name is exist, else return false.
     */
    public boolean isStoreExist(String name){
        try{
            String n[] = name.split(K.ls);
            sd.getJSONObject(n[0]);
            return true;
        }catch(JSONException e){return false;}
    }
    /**
     * This method used for check store link is exist or not. If the <mark>store link</mark> is exist it will return <mark>true</mark>. Else it will return <mark>false</mark>.
     *
     * @param address store link
     * @return true if the store link is exist, else return false.
     */
    public boolean isStoreAddressExist(String address){
        String k[] = address.split(K.ls);
        int i = k.length-1;
        try{
            gsj(k, i).get(k[i]);
            return true;
        }
        catch(JSONException e){
            return false;
        }
    }
    /**
     * This method used for count store child number.
     *
     * @param address store link
     * @return child number. if link not found it will return -1
     */
    public int countStoreChild(String address){
        String k[] = address.split(K.ls);
        int i = k.length-1;
        JSONObject targetOb = gsj(k, i);

        try{
            return targetOb.getJSONObject(k[i]).length();
        }
        catch(JSONException e){
            try{
                return targetOb.getJSONArray(k[i]).length();
            }
            catch(JSONException e1){
                return -1;
            }
        }
    }

    /**
     * These methods use for getting data from <i>store database<i>.
     *
     * @param address store data address
     *
     * @return data from store database.
     */
    public boolean getStoreBoolean(String address){
        String k[] = address.split(K.ls); // k = Key
        int i = k.length-1; // i = last index
        try{return gsj(k, i).getBoolean(k[i]);}
        catch(JSONException ignored){}
        return false;
    }
    public int getStoreInt(String address){
        String k[] = address.split(K.ls);
        int i = k.length-1;
        try{ return gsj(k, i).getInt(k[i]);}
        catch(JSONException ignored){}
        return di;
    }
    public double getStoreDouble(String address){
        String k[] = address.split(K.ls);
        int i = k.length-1;
        try{return gsj(k, i).getDouble(k[i]);}
        catch(JSONException ignored){}
        return dd;
    }
    public String getStoreString(String address){
        String k[] = address.split(K.ls);
        int i = k.length-1;
        try{return gsj(k, i).getString(k[i]);}
        catch(JSONException ignored){}
        return ds;
    }
    public JSONArray getStoreJSONArray(String address){
        String k[] = address.split(K.ls);
        int i = k.length-1;
        try{return gsj(k, i).getJSONArray(k[i]);}
        catch(JSONException ignored){}
        return dja;
    }
    public JSONObject getStoreJSONObject(String address){
        String k[] = address.split(K.ls);
        int i = k.length-1;
        try{return gsj(k, i).getJSONObject(k[i]);}
        catch(JSONException ignored){}
        return dj;
    }

    /**
     * This method used for get all data from store database.
     * @return full store database
     */
    public JSONObject getStore(){return sd;}

    /**
     * This method used for delete data from <i>store database</i>.
     *
     * @param address store data address
     * @return true if success else return false.
     */
    public boolean deleteStore(String address){
        String k[] = address.split(K.ls);
        if(k.length>1){
            JSONObject tob = getStoreJSONObject(clws(k, k.length-2)); //tob = target_ob
            tob.remove(k[k.length-1]);
        }
        else{sd.remove(address);}
        return save(1, sd, c);
    }

    /**
     * This method used to put data into the store database.
     * If <mark>ov == true</mark>, this method update data in old address.
     * If keeping data is the success, this method will return true.
     *
     * @param s Store Link
     * @param b boolean data
     * @param i integer data
     * @param i1 double data
     * @param s1 string data
     * @param a JSONArray data
     * @param ob JSONObject data
     * @param ov isOverrideData
     * @param i2 data type number.
     *
     * @return true if success else return false.
     */
    boolean spd(String s, boolean b, int i, double i1, String s1, JSONArray a, JSONObject ob, boolean ov, int i2){
        String keys[] = s.split(K.ls);
        int len = keys.length-1;
        try{
            JSONObject store = sd.getJSONObject(keys[0]);
            if(len>0){
                for(int x=1; x<len; x++){
                    store = store.getJSONObject(keys[x]);
                }
                if(ov){
                    boolean check = sw(store, keys[len], i2, b, i, i1, s1, a, ob);
                    if(check){return save(1, sd, c);}
                    else{return false;}
                }
                else{
                    if(nd(store, keys[len])){
                        boolean check = sw(store, keys[len], i2, b, i, i1, s1, a, ob);
                        if(check){return save(1, sd, c);}
                        else{return false;}
                    }
                    else{return false;}
                }
            }
            else{
                if(ov){
                    boolean check = sw(sd, keys[0], i2, b, i, i1, s1, a, ob);
                    if(check){return save(1, sd, c);}
                    else{return false;}
                }
                else{return false;}
            }
        }
        catch(JSONException e){
            if(len == 0){
                boolean check = sw(sd, keys[0], i2, b, i, i1, s1, a, ob);
                if(check){return save(1, sd, c);}
                else{return false;}
            }
            else{
                try{
                    JSONObject ob1 = new JSONObject();
                    switch (i2) {
                        case 0: ob1 = ob1.put(keys[len], b); break;
                        case 1: ob1 = ob1.put(keys[len], i); break;
                        case 2: ob1 = ob1.put(keys[len], i1); break;
                        case 3: ob1 = ob1.put(keys[len], s1); break;
                        case 4: ob1 = ob1.put(keys[len], a); break;
                        case 5: ob1 = ob1.put(keys[len], ob);
                    }
                    for(int x=len-1; x>0; x--){
                        JSONObject ob2 = new JSONObject();
                        ob2.put(keys[x],ob1);
                        ob1 = ob2;
                    }
                    sd.put(keys[0], ob1);
                    return save(1, sd, c);
                }
                catch(JSONException e3){return false;}
            }
        }
    }


    /**
     * This method used for check new pool name.
     *
     * @param pool pool name/address
     * @param isStable is stable pool database or non_stable database. <mark>true</mark> for stable database.
     *
     * @return true if pool name is a new pool. Else return false.
     */
    public boolean isPoolExist(String pool, boolean isStable){
        String s = isStable?K.pool1:K.pool2;
        try{
            pd.getJSONObject(s).getJSONObject(pool);
            return true;
        }catch(JSONException e){return false;}
    }

    /**
     * @param name pool name
     * @return stable pool type.
     */
    public String poolType(String name){
        try{return pd.getJSONObject(K.pool1).getJSONObject(name).getString(K.type);}
        catch (JSONException e){return "PoolNotFound";}
    }

    /**
     * This method return full pool database.
     * @return pool database as JSONObject
     */
    public JSONObject getPool(){return pd;}

    /**
     * @param isStable true for K.pool1 section and false for K.pool2 section
     *
     * @return pool database
     */
    public JSONObject getPool(boolean isStable){
        try{
            if (isStable) return pd.getJSONObject(K.pool1);
            else return pd.getJSONObject(K.pool2);
        }catch (Exception e){return dj;}
    }

    /**
     * @param pool_name pool name
     * @param index data index number
     * @param isStable true for stable database and false for non_stable database.
     *
     * @return data from <i>pool database</i>.
     */
    public boolean getPoolBoolean(String pool_name, int index, boolean isStable){
        try {
            if (isStable) return pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items).getBoolean(index);
            return pd.getJSONObject(K.pool2).getJSONArray(pool_name).getBoolean(index);
        }
        catch (JSONException e) {return false;}
    }

    public int getPoolInt(String pool_name, int index, boolean isStable){
        try {
            if (isStable) return pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items).getInt(index);
            return pd.getJSONObject(K.pool2).getJSONArray(pool_name).getInt(index);
        }
        catch (JSONException e) {return di;}
    }

    public double getPoolDouble(String pool_name, int index, boolean isStable){
        try {
            if (isStable) return pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items).getDouble(index);
            return pd.getJSONObject(K.pool2).getJSONArray(pool_name).getDouble(index);
        }
        catch (JSONException e) {return dd;}
    }

    public String getPoolString(String pool_name, int index, boolean isStable){
        try {
            if (isStable) return pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items).getString(index);
            return pd.getJSONObject(K.pool2).getJSONArray(pool_name).getString(index);
        }
        catch (JSONException e) {return ds;}
    }

    public JSONArray getPoolJSONArray(String pool_name, int index, boolean isStable){
        try {
            if (isStable) return pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items).getJSONArray(index);
            return pd.getJSONObject(K.pool2).getJSONArray(pool_name).getJSONArray(index);
        }
        catch (JSONException e) {return dja;}
    }

    public JSONObject getPoolJSONObject(String pool_name, int index, boolean isStable){
        try {
            if (isStable) return pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items).getJSONObject(index);
            return pd.getJSONObject(K.pool2).getJSONArray(pool_name).getJSONObject(index);
        }
        catch (JSONException e) {return dj;}
    }


    /**
     * These methods used to get all data form a <mark>stable pool database</mark>.
     */
    public ArrayList getPoolList(String pool_name){
        ArrayList<Object> r = new ArrayList<>();
        try{
            JSONArray t = pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items);
            int l = t.length();
            for(int i=0; i<l; i++){
                r.add(t.get(i));
            }
        }
        catch(JSONException ignored){}
        return r;
    }

    public ArrayList<Boolean> getPoolBooleanList(String pool_name){
        ArrayList<Boolean> r = new ArrayList<>();
        try{
            JSONArray t = pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items);
            int l = t.length();
            for(int i=0; i<l; i++){
                r.add(t.getBoolean(i));
            }
        }
        catch(JSONException ignored){}
        return r;
    }

    public ArrayList<Integer> getPoolIntList(String pool_name){
        ArrayList<Integer> r = new ArrayList<>();
        try{
            JSONArray t = pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items);
            int l = t.length();
            for(int i=0; i<l; i++){
                r.add(t.getInt(i));
            }
        }
        catch(JSONException ignored){}
        return r;
    }

    public ArrayList<Double> getPoolDoubleList(String pool_name){
        ArrayList<Double> r = new ArrayList<>();
        try{
            JSONArray t = pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items);
            int l = t.length();
            for(int i=0; i<l; i++){
                r.add(t.getDouble(i));
            }
        }
        catch(JSONException ignored){}
        return r;
    }

    public ArrayList<String> getPoolStringList(String pool_name){
        ArrayList<String> r = new ArrayList<>();
        try{
            JSONArray t = pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items);
            int l = t.length();
            for(int i=0; i<l; i++){
                r.add(t.getString(i));
            }
        }
        catch(JSONException ignored){}
        return r;
    }

    public ArrayList<JSONArray> getPoolJSONArrayList(String pool_name){
        ArrayList<JSONArray> r = new ArrayList<>();
        try{
            JSONArray t = pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items);
            int l = t.length();
            for(int i=0; i<l; i++){
                r.add(t.getJSONArray(i));
            }
        }
        catch(JSONException ignored){}
        return r;
    }

    public ArrayList<JSONObject> getPoolJSONObjectList(String pool_name){
        ArrayList<JSONObject> r = new ArrayList<>();
        try{
            JSONArray t = pd.getJSONObject(K.pool1).getJSONObject(pool_name).getJSONArray(K.items);
            int l = t.length();
            for(int i=0; i<l; i++){
                r.add(t.getJSONObject(i));
            }
        }
        catch(JSONException ignored){}
        return r;
    }

    /**
     * @param pool_name pool name
     * @param index data index number
     * @param isStable true for stable database and false for non_stable database.
     *
     * This method used for <mark>delete</mark> data from pool database.
     *
     * @return true if success else return false.
     */
    public boolean deletePoolData(String pool_name, int index, boolean isStable){
        try{
            if(isStable){
                JSONObject ob = pd.getJSONObject(K.pool1).getJSONObject(pool_name);
                JSONArray it = ob.getJSONArray(K.items);
                it.remove(index);
                return save(2, pd, c);
            }
            else{
                JSONArray ob1 = pd.getJSONObject(K.pool2).getJSONArray(pool_name);
                ob1.remove(index);
                return save(2, pd, c);
            }
        }
        catch(JSONException e){return false;}
    }

    /**
     * @param pool_name pool name
     * @param isStable true for stable database and false for non_stable database.
     *
     * This method used for <mark>delete</mark> all data from pool database.
     *
     * @return true if success else return false.
     */
    public boolean deletePoolAllData(String pool_name, boolean isStable){
        try{
            if(isStable){
                pd.getJSONObject(K.pool1).remove(pool_name);
                return save(2, pd, c);
            }
            else{
               pd.getJSONObject(K.pool2).remove(pool_name);
                return save(2, pd, c);
            }
        }
        catch(JSONException e){return false;}
    }


    /**
     * This method used for keep data into <i>stable pool database</i>.
     *
     * @param s address
     * @param b boolean data
     * @param i integer data
     * @param i1 double data
     * @param s1 string data
     * @param a JSONArray data
     * @param ob JSONObject data
     * @param i2 data type number.
     *
     * @return true if success else return false.
     */
    boolean ips(String s, boolean b, int i, double i1, String s1, JSONArray a, JSONObject ob, int i2){
        try{
            JSONObject p = pd.getJSONObject(K.pool1).getJSONObject(s);
            String t = p.getString(K.type);

            JSONArray it = p.getJSONArray(K.items);
            switch(t){
                case K.dt1:
                    if(i2 == 0){
                        it.put(b);
                        return save(2, pd, c);
                    }
                    else return false;
                case K.dt2:
                    if(i2 == 1){
                        it.put(i);
                        return save(2, pd, c);
                    }
                    else return false;
                case K.dt3:
                    if(i2 == 2){
                        it.put(i1);
                        return save(2, pd, c);
                    }
                    else return false;
                case K.dt4:
                    if(i2 == 3){
                        it.put(s1);
                        return save(2, pd, c);
                    }
                    else return false;
                case K.dt5:
                    if(i2 == 4){
                        it.put(a);
                        return save(2, pd, c);
                    }
                    else return false;
                case K.dt6:
                    if(i2 == 5){
                        it.put(ob);
                        return save(2, pd, c);
                    }
                    else return false;
            }
        }
        catch(JSONException e){ // if pool address not found
            try{
                JSONObject p2 = pd.getJSONObject(K.pool1);
                JSONObject n2 = new JSONObject();
                JSONArray il = new JSONArray();
                switch (i2) {
                    case 0:
                        n2.put(K.type, K.dt1);
                        il.put(b);
                        break;
                    case 1:
                        n2.put(K.type, K.dt2);
                        il.put(i);
                        break;
                    case 2:
                        n2.put(K.type, K.dt3);
                        il.put(i1);
                        break;
                    case 3:
                        n2.put(K.type, K.dt4);
                        il.put(s1);
                        break;
                    case 4:
                        n2.put(K.type, K.dt5);
                        il.put(a);
                        break;
                    case 5:
                        n2.put(K.type, K.dt6);
                        il.put(ob);
                }
                n2.put(K.items, il);
                p2.put(s, n2);
                return save(2, pd, c);
            }
            catch(JSONException e1){
                return false;
            }
        }
        return false;
    }

    /**
     * This method use for keep data into <i>non_stable pool database</i>.
     *
     * @return true if success else return false.
     */
    boolean ipns(String s, boolean b, int i, double i1, String s1, JSONArray a, JSONObject ob, int i2){
        try{
            JSONArray obj = pd.getJSONObject(K.pool2).getJSONArray(s);
            switch (i2) {
                case 0: obj.put(b); break;
                case 1: obj.put(i); break;
                case 2: obj.put(i1); break;
                case 3: obj.put(s1); break;
                case 4: obj.put(a); break;
                case 5: obj.put(ob);
            }
            return save(2, pd, c);
        }
        catch(JSONException e){ // if pool address not found
            try{
                JSONArray z = new JSONArray();
                switch (i2) {
                    case 0: z.put(b); break;
                    case 1: z.put(i); break;
                    case 2: z.put(i1); break;
                    case 3: z.put(s1); break;
                    case 4: z.put(a); break;
                    case 5: z.put(ob);
                }
                JSONObject z1 = pd.getJSONObject(K.pool2);
                z1.put(s, z);
                pd.put(K.pool2, z1);
                return save(2, pd, c);
            }
            catch(JSONException e2){return false;}
        }
    }

    /**
     * These method used for update pool database.
     * 
     * @param b boolean data
     * @param i integer data
     * @param i1 double data
     * @param s string data
     * @param a JSONArray data
     * @param ob JSONObject data
     * @param s1 pool name
     * @param i2 data index
     * @param b1 true for stable database
     * @param i3 data type number
     *
     * @return true if success else return false.
     */
    boolean up(boolean b, int i, double i1, String s, JSONArray a, JSONObject ob, String s1, int i2, boolean b1, int i3){
        try{
            if (b1) {
                JSONObject st = pd.getJSONObject(K.pool1).getJSONObject(s1);
                String t = st.getString(K.type);
                JSONArray it = st.getJSONArray(K.items);
                if (i2<0 || i2>it.length()-1) return false;
                switch (t) {
                    case K.dt1:
                        if (i3 == 0) it.put(i2, b);
                        else return false;
                        break;
                    case K.dt2:
                        if (i3 == 1) it.put(i2, i);
                        else return false;
                        break;
                    case K.dt3:
                        if (i3 == 2) it.put(i2, i1);
                        else return false;
                        break;
                    case K.dt4:
                        if (i3 == 3) it.put(i2, s);
                        else return false;
                        break;
                    case K.dt5:
                        if (i3 == 4) it.put(i2, a);
                        else return false;
                        break;
                    case K.dt6:
                        if (i3 == 5) it.put(i2, ob);
                        else return false;
                }
                return save(2, pd, c);
            }
            else {
                JSONArray st = pd.getJSONObject(K.pool2).getJSONArray(s1);
                if (i2<0 || i2>st.length()-1) return false;
                switch (i3) {
                    case 0: st.put(i2, b); break;
                    case 1: st.put(i2, i); break;
                    case 2: st.put(i2, i1); break;
                    case 3: st.put(i2, s); break;
                    case 4: st.put(i2, a); break;
                    case 5: st.put(i2, ob);
                }
                return save(2, pd, c);
            }
        }
        catch(JSONException e){return false;}
    }

    /**
     * These method used for update full pool.
     *
     * @param b boolean list
     * @param i integer list
     * @param i1 double list
     * @param s string list
     * @param a JSONArray list
     * @param ob JSONObject list
     * @param s1 pool name
     * @param b1 true for stable database
     * @param i2 data type number
     *
     * @return true if success else return false.
     */
    boolean up(ArrayList<Boolean> b, ArrayList<Integer> i, ArrayList<Double> i1, ArrayList<String> s, ArrayList<JSONArray> a, ArrayList<JSONObject> ob, String s1, boolean b1, int i2){
        try{
            if (b1) {
                JSONObject st = pd.getJSONObject(K.pool1).getJSONObject(s1);
                String t = st.getString(K.type);
                st.remove(K.items);
                JSONArray it = new JSONArray();
                switch (t) {
                    case K.dt1:
                        if (i2 == 0) for(boolean x : b) it.put(x);
                        else return false;
                        break;
                    case K.dt2:
                        if (i2 == 1) for(int x : i) it.put(x);
                        else return false;
                        break;
                    case K.dt3:
                        if (i2 == 2) for(double x : i1) it.put(x);
                        else return false;
                        break;
                    case K.dt4:
                        if (i2 == 3) for(String x : s) it.put(x);
                        else return false;
                        break;
                    case K.dt5:
                        if (i2 == 4) for(JSONArray x : a) it.put(x);
                        else return false;
                        break;
                    case K.dt6:
                        if (i2 == 5) for(JSONObject x : ob) it.put(x);
                        else return false;
                }
                st.put(K.items, it);
                return save(2, pd, c);
            }
            else {
                pd.getJSONObject(K.pool2).remove(s1);
                JSONArray st = new JSONArray();
                switch (i2) {
                    case 0: for(boolean x : b) st.put(x); break;
                    case 1: for(int x : i) st.put(x); break;
                    case 2: for(double x : i1) st.put(x); break;
                    case 3: for(String x : s) st.put(x); break;
                    case 4: for(JSONArray x : a) st.put(x); break;
                    case 5: for(JSONObject x : ob) st.put(x);
                }
                pd.put(s1, st);
                return save(2, pd, c);
            }
        }
        catch(JSONException e){return false;}
    }

    /**
     * These method allow use Pippo Query for Pool database.
     * @param b true for K.pool1 section and false for K.pool2 section
     * @param name pool name
     * @return Pool data as JSONArray
     */
    JSONArray poolData(boolean b, String name){
        if (b){
            try{
                String type = pd.getJSONObject(K.pool1).getJSONObject(name).getString(K.type);
                if (type.equals(K.dt5) || type.equals(K.dt6))
                    return pd.getJSONObject(K.pool1).getJSONObject(name).getJSONArray(K.items);
                else return new JSONArray();
            }
            catch (JSONException e){
                return new JSONArray();
            }
        }
        else{
            try{
                return pd.getJSONObject(K.pool2).getJSONArray(name);
            }
            catch (JSONException e){
                return new JSONArray();
            }
        }
    }

    /**
     * This method raping poolData()'s array into JSONObject for Query class.
     * @param b true for K.pool1 section and false for K.pool2 section
     * @param name pool name
     * @return Pool data as JSONObject
     */
    JSONObject rapingPoolArray(boolean b, String name){
        JSONObject ob = new JSONObject();
        try{
            JSONArray ar = poolData(b, name);
            if (ar != new JSONArray())
                ob.put("data", ar);
        }
        catch (JSONException ignored){}
        return ob;
    }



    /**
     * If a user creates <mark>new Pippo()</mark> object with <mark>isClose == true</mark>, then the user need to invoke this method to saving updated data into binary files.
     * Unfortunately, if the user doesn't invoke this method after creating <b>Pippo object</b> with <mark>isClose == true</mark> and reload activity, the user will <mark>lose</mark> all update data.
     * This system for work super fast data exchange with Pippo database in an activity.
     *
     * @return true if success else return false.
     */
    public boolean close(){
        ic = true;
        return swc();
    }

    /**
     * @param close_num An integer number to mark close() method.
     *
     * If the user uses the <b>close()</b> method <mark>more than one time</mark> then it safe to use with integer parameter.
     * If the system faces any problem to <b>save update data</b> into binary files then the system will be <mark>throw an exception with this integer parameter</mark>.
     *
     * @return true if success else return false.
     */
    public boolean close(int close_num){
        cn = close_num;
        ic = true;
        return swc();
    }

    /**
     * This is a helper method of close() method.
     * This method saves all update data into binary files.
     *
     * @return true if success else return false.
     */
    private boolean swc(){
        boolean a, b, c;
        a = b = c = false;
        if(k) a = save(0, kd, false);
        if(s) b = save(1, sd, false);
        if(p) c = save(2, pd, false);

        return k == a && s == b && p == c;
    }

    /**
     * This method will remove all active database from system ram.
     * Before clear data it will check do user invoke <i>[if isClose == true]</i> close() method or not.
     * If the user doesn't invoke <mark>close()</mark> method <i>[when isClose == true]</i> then the <mark>clear()</mark> method don't clear data from ram and it will <b>return false</b>.
     * After <b>clear()</b> <mark>success</mark>, if the user wants to get any data then the user must need to invoke <mark>start()</mark> method.
     *
     * @return true if success else return false.
     */
    public boolean clear(){
        if(ic) {
            kd = sd = pd = new JSONObject();
            ic = false;
            return true;
        }
        return false;
    }

    /**
     * This is process constructor helper method.
     * This method used for load database into the system ram for action.
     * The user also can use this method for an active new database for action.
     * It this method face any problem to run, the system will throw an exception for this problem.
     */
    public void start(boolean keep, boolean store, boolean pool){
        if(keep) kd = rbf(0);
        if(store) sd = rbf(1);
        if(pool) pd = rbf(2);
    }

    /**
     * This method saves the database into binary files.
     *
     * @param i database type number
     * @param ob database in JSONObject formate
     * @param b data will save when <mark>b == false</mark>.
     *
     * @return true if success else return false.
     */
    private boolean save(int i, JSONObject ob, boolean b){
        if (!b){
            boolean b1 = false;
            switch (i){
                case 0: if(k) b1 = wbf(0, ob); break;
                case 1: if(s) b1 = wbf(1, ob); break;
                case 2: if(p) b1 = wbf(2, ob);
            }
            return b1;
        }
        return true;
    }

    /**
     * This is a helper method of store database to store data into the database.
     *
     * @param ob database
     * @param k data name
     * @param i data type number
     * @param b boolean data
     * @param i1 integer data
     * @param i2 double data
     * @param s string data
     * @param a JSONArray data
     * @param ob2 JSONObject data
     *
     * @return true if success else return false.
     */
    private boolean sw(JSONObject ob, String k, int i, boolean b, int i1, double i2, String s, JSONArray a, JSONObject ob2){
        try{
            switch (i) {
                case 0: ob.put(k, b); break;
                case 1: ob.put(k, i1); break;
                case 2: ob.put(k, i2); break;
                case 3: ob.put(k, s); break;
                case 4: ob.put(k, a); break;
                case 5: ob.put(k, ob2); break;
                default: return false;
            }
            return true;
        }
        catch(JSONException e){return false;}
    }

    /**
     * This is a helper method of store database to check address empty or not.
     *
     * @param ob database
     * @param k data name
     *
     * @return true if empty else return false.
     */
    private boolean nd(JSONObject ob, String k){
        try{
            ob.get(k);
            return false;
        }catch(JSONException e){return true;}
    }

    /**
     * This is a helper method of store database to get target data.
     *
     * @param k Key
     * @param i1 Key length
     *
     * @return target JSONObject 
     */
    private JSONObject gsj(String k[], int i1){
        JSONObject rob = sd;
        try{
            for (int i = 0; i < i1; i++) {
                rob = rob.getJSONObject(k[i]);
            }
            return rob;
        }catch(JSONException e){return new JSONObject();}
    }

    /**
     * This is a helper method of store database to create new data link.
     *
     * @param a Key
     * @param i1 Key length
     *
     * @return new string link 
     */
    private String clws(String a[], int i1){
        StringBuilder link = new StringBuilder();
        link.append(a[0]);
        if (i1>0){
            for(int i=1; i<=i1; i++){
                link.append(K.ls);
                link.append(a[i]);
            }
        }
        return link.toString();
    }
}

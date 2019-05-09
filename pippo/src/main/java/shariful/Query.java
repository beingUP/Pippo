package net.beingup.simpletodo.pippo;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.*;

/**
 * Query for store database without space sensitive!!!
 */

class Query extends process {

    /**
     * If user write a query with link it will true.
     */
    boolean isQueryOk = false;

    /**
     * store query data
     */
    String link = "";
    private String column = "";
    private String first_condition_set = ""; // This is store first 'and' conditions set for checking data row type.
    private JSONArray all_conditions = new JSONArray();
    private int limit_start = -1, limit_end = -1;
    private String order_by = "asc";

    /**
     * process link and store data
     */
    private JSONArray store_array = new JSONArray();
    private JSONObject store_object = new JSONObject();
    private boolean is_array = false; // is past key array or object

    /**
     * process condition
     */
    private String condition_data_name = "";
    private String condition_string = "0"; // condition_string = condition string
    private int condition_int = -1; // condition_int = condition integer
    private double condition_double = -1.0; // condition_double = condition double
    private boolean condition_boolean = false, ib = false; // condition_double = condition boolean, isCb = do user use boolean in condition?
    private int condition_action_number = -1; // condition_action_number = number action [>=,0 <=,1 =,2 <,3 >,4]

    /**
     * store result data
     */
    private ArrayList<Boolean> boolean_list = new ArrayList<>();
    private ArrayList<Integer> integer_list = new ArrayList<>();
    private ArrayList<Double> double_list = new ArrayList<>();
    private ArrayList<String> string_list = new ArrayList<>();
    private ArrayList<JSONArray> array_list = new ArrayList<>();
    private ArrayList<JSONObject> object_list = new ArrayList<>();
    private JSONArray multi_column_data = new JSONArray();

    /**
     * valid data
     */
    private int valid_value = -1; //valid_value check how much data is valid in condition. (it's work for limit->2,5)

    Query(Context context) { super(context); }

    /**
     * porcess query
     * @param s
     * @return
     */
    void create_query(String s){
        clear_old_query();
        String q = s.trim();
        if(q.contains(K.where)){
            String lc[] = q.split(K.where);
            String link_part[] = lc[0].split(K.space);

            // link
            try{
                if (link_part[0].equals(K.select)) link_part[0] = "";

                if (link_part.length>1){
                    StringBuilder create_link = new StringBuilder();
                    for (String word : link_part){
                        create_link.append(word);
                    }
                    link = create_link.toString();
                }
                else link = link_part[0];

                if (link.contains(K.d)){
                    String lac[] = link.split(K.d); // lac = link and column
                    link = lac[0];
                    try{column = lac[1];}
                    catch (Exception ignored){}
                }
            }catch (Exception e){return;}

            // and or condition
            String and_or;
            if (lc[1].contains(K.limit)){
                String ld[] = lc[1].split(K.limit);
                and_or = ld[0];
            }
            else{
                if (lc[1].contains(K.by)){
                    String bd[] = lc[1].split(K.by);
                    and_or = bd[0];
                }
                else and_or = lc[1];
            }
            and_or = and_or.trim();

            String all_or_conditions[] = and_or.split(K.or);
            first_condition_set = all_or_conditions[0]; // data row type helper.
            for(String x : all_or_conditions){
                String aa[] = x.split(K.and);
                JSONArray ob = new JSONArray();
                for(String y : aa){ob.put(y);}
                all_conditions.put(ob);
            }

            // limit
            if (lc[1].contains(K.limit)){
                String limit;
                if (lc[1].contains(K.by))
                    limit = lc[1].substring(lc[1].indexOf(K.limit), lc[1].indexOf(K.by));
                else
                    limit = lc[1].substring(lc[1].indexOf(K.limit), lc[1].length());

                String ll[] = limit.split(K.d);
                if (ll[1].contains(K.d1)){
                    String se[] = ll[1].split(K.d1); // se = start end
                    try{
                        limit_start = Integer.parseInt(se[0].trim());
                        limit_end =  Integer.parseInt(se[1].trim());
                        limit_end = limit_end > limit_start ? limit_end : -1; // limit end will not work if it not larger then limit start
                    }
                    catch (Exception ignored){}
                }
                else{
                    try{
                        limit_start = Integer.parseInt(ll[1].trim());}
                    catch (Exception ignored){}
                }
            }

            // by
            if (lc[1].contains(K.by)){
                String lw = lc[1].substring(lc[1].indexOf(K.by), lc[1].length());

                String ll[] = lw.split(K.d);
                try{
                    order_by = ll[1].trim();}
                catch (Exception ignored){}
            }

            isQueryOk = true;
        }
    }

    private boolean check(boolean row, boolean is_arr, JSONArray tar, JSONObject tob, String s, int ot){
        clear_all_list();
        try{
            // object
            ArrayList<String> ke = new ArrayList<>();
            JSONArray arr = new JSONArray();
            if (row){
                Iterator<String> k = tob.keys();
                while(k.hasNext()){ke.add(k.next());}
                if (order_by.equals(K.desc)) Collections.reverse(ke);
            }
            else{
                if (order_by.equals(K.desc)){
                    for (int a=tar.length()-1; a>=0; a--){
                        try{
                            if (is_arr) arr.put(tar.getJSONArray(a));
                            else arr.put(tar.getJSONObject(a));
                        }
                        catch (JSONException ignored){}
                    }
                    tar = arr;
                }
                else{
                    for (int b=0; b<tar.length(); b++){
                        try{
                            if (is_arr) arr.put(tar.getJSONArray(b));
                            else arr.put(tar.getJSONObject(b));
                        }
                        catch (JSONException ignored){}
                    }
                    tar = arr;
                }
                arr = new JSONArray();
            }

            int len = row? ke.size() : tar.length();

            get_all_object :
            for (int c=0; c<len; c++){
                JSONArray arr1 = new JSONArray();
                JSONObject ob = new JSONObject();

                try{
                    if (is_arr) arr1 = row? tob.getJSONArray(ke.get(c)) : tar.getJSONArray(c);
                    else ob = row? tob.getJSONObject(ke.get(c)) : tar.getJSONObject(c);
                }
                catch (JSONException e){
                    continue;
                }

                get_conditions :
                for(int d = 0; d< all_conditions.length(); d++){
                    JSONArray con = all_conditions.getJSONArray(d); // one set conditions
                    JSONObject sob = new JSONObject(); // store object
                    JSONArray soa = new JSONArray(); // store array
                    int ipct = 0; // ipct = is past condition true

                    //check_single_condition
                    for(int f=0; f<con.length(); f++){
                        String sc = con.getString(f); // sc = single condition

                        if (sc.contains(K.c0)){
                            process_single_condition(sc.split(K.c0)); condition_action_number = 0;}
                        else if (sc.contains(K.c1)){
                            process_single_condition(sc.split(K.c1)); condition_action_number = 1;}
                        else if (sc.contains(K.c2)){
                            process_single_condition(sc.split(K.c2)); condition_action_number = 2;}
                        else if (sc.contains(K.c3)){
                            process_single_condition(sc.split(K.c3)); condition_action_number = 3;}
                        else if (sc.contains(K.c4)){
                            process_single_condition(sc.split(K.c4)); condition_action_number = 4;}
                        else if (sc.contains(K.c5)){
                            process_single_condition(sc.split(K.c5)); condition_action_number = 5;}


                        int ind = -1;
                        if (is_arr){
                            try{ind = Integer.parseInt(condition_data_name);}
                            catch (Exception e){
                                valid_value = -1;
                                return true;
                            }
                        }

                        if (!condition_string.equals("0")){
                            boolean first, last;
                            first = condition_string.indexOf(K.like) == 0;
                            last = condition_string.lastIndexOf(K.like) == condition_string.length()-1;
                            condition_string = condition_string.replace(K.like,' ').trim();
                            try{
                                String target_string = is_arr? arr1.getString(ind) : ob.getString(condition_data_name);
                                switch (condition_action_number){
                                    case 2 :
                                        if (first && last && target_string.contains(condition_string)) ipct += 1;
                                        else if (first && !last && (target_string.lastIndexOf(condition_string) == target_string.length()- condition_string.length())) ipct += 1;
                                        else if (!first && last && target_string.indexOf(condition_string)==0) ipct += 1;
                                        else if (!first && !last && condition_string.equals(target_string)) ipct += 1;
                                        break;
                                    case 5 :
                                        if (first && last && !target_string.contains(condition_string)) ipct += 1;
                                        else if (first && !last && !(target_string.lastIndexOf(condition_string) == target_string.length()- condition_string.length())) ipct += 1;
                                        else if (!first && last && !(target_string.indexOf(condition_string)==0)) ipct += 1;
                                        else if (!first && !last && !condition_string.equals(target_string)) ipct += 1;

                                }
                            }catch (JSONException ignored){}

                            // set default data after action
                            condition_string = "0";
                        }
                        else{
                            if (condition_int != -1){
                                try{
                                    int m = is_arr? arr1.getInt(ind) : ob.getInt(condition_data_name);
                                    switch(condition_action_number){
                                        case 0 : if (m >= condition_int) ipct += 1; break;
                                        case 1 : if (m <= condition_int) ipct += 1; break;
                                        case 2 : if (m == condition_int) ipct += 1; break;
                                        case 3 : if (m > condition_int) ipct += 1; break;
                                        case 4 : if (m < condition_int) ipct += 1; break;
                                        case 5 : if (m != condition_int) ipct += 1;
                                    }
                                }catch (JSONException ignored){}

                                // set default data after action
                                condition_int = -1;
                            }
                            else{
                                if (condition_double != -1.0){
                                    try{
                                        double m = is_arr? arr1.getDouble(ind) : ob.getDouble(condition_data_name);
                                        switch(condition_action_number){
                                            case 0 : if (m >= condition_double) ipct += 1; break;
                                            case 1 : if (m <= condition_double) ipct += 1; break;
                                            case 2 : if (m == condition_double) ipct += 1; break;
                                            case 3 : if (m > condition_double) ipct += 1; break;
                                            case 4 : if (m < condition_double) ipct += 1; break;
                                            case 5 : if (m != condition_double) ipct += 1;
                                        }
                                    }catch (JSONException ignored){}

                                    // set default data after action
                                    condition_double = -1.0;
                                }
                                else{
                                    if (ib){
                                        try{
                                            boolean m = is_arr? arr1.getBoolean(ind) : ob.getBoolean(condition_data_name);
                                            switch(condition_action_number){
                                                case 2 : if (m == condition_boolean) ipct += 1; break;
                                                case 5 : if (m != condition_boolean) ipct += 1;
                                            }
                                        }catch (JSONException ignored){}

                                        // set default data after action
                                        ib = false;
                                    }
                                }
                            }
                        }

                        // add data to list
                        if (ipct == con.length()) {
                            if (is_arr) soa = arr1;
                            else sob = ob;
                        }
                        else {
                            soa = new JSONArray();
                            sob = new JSONObject();
                        }
                    }
                    // if get data in 'and' condition then break conditions and get new data for check.
                    int dl = is_arr? soa.length() : sob.length();

                    if (dl>0){
                        valid_value += 1;

                        if (limit_start !=-1){
                            if (limit_end != -1){
                                if (valid_value>= limit_start && valid_value< limit_end){
                                    if (is_arr) store_data(ot, s, soa, new JSONObject(), true);
                                    else store_data(ot, s, new JSONArray(), sob, false);
                                    break get_conditions;
                                }
                                if (valid_value>= limit_end){break get_all_object;}
                            }
                            else{
                                if (length(ot)< limit_start){
                                    if (is_arr) store_data(ot, s, soa, new JSONObject(), true);
                                    else store_data(ot, s, new JSONArray(), sob, false);
                                    break get_conditions;
                                }
                                else{break get_all_object;}
                            }
                        }
                        else{
                            if (is_arr) store_data(ot, s, soa, new JSONObject(), true);
                            else store_data(ot, s, new JSONArray(), sob, false);
                            break get_conditions;
                        }
                    }
                }
            }

            // restart after activity
            valid_value = -1;
            // return output after check data
            return true;
        }
        catch (JSONException e){return false;}
    }

    /**
     * hope this method help to store target object or array for process
     * @param s
     * @param ob
     * @param is_arr
     */
    private void target(String s, JSONObject ob, boolean is_arr){
        store_object = ob;
        String keys[] = s.split(K.ls);
        if (s.length() == 0){
            store_object = new JSONObject();
            return;
        }

        for (int i=0; i<keys.length; i++){
            String k = keys[i];
            int ind;
            try{ // check key is number or not
                ind = Integer.parseInt(k);
                try{ // if key is number and after it show any exception
                    inAr(store_array.getJSONArray(ind));
                }
                catch(Exception e){
                    store_array = new JSONArray();
                    store_object = new JSONObject();
                    break;
                }
            }
            catch(Exception x){
                if (k.lastIndexOf(K.ar2) == k.length()-1){ // if array
                    if (k.indexOf(K.ar1) == k.length()-2){ // if pure array[]

                        String kk = k.replace(K.ar, ""); // array[]->array (array name)
                        try{
                            inAr(store_object.getJSONArray(kk));
                        }
                        catch (JSONException e){
                            store_array = new JSONArray();
                            store_object = new JSONObject();
                            break;
                        }
                    }
                    else{ // if array like my_array[123]
                        int len = k.length();
                        int ki = k.indexOf(K.ar1); // index of '['
                        String kk = k.substring(0, ki); // array[123]->array
                        int index = -1;
                        try{
                            index = Integer.parseInt(k.substring((ki+1), (len-1)));
                        }catch (Exception ignored){}

                        try{
                            if (is_array) inAr(store_array.getJSONArray(index));
                            else inAr(store_object.getJSONArray(kk));
                            if (index< store_array.length()) {
                                if (i == keys.length-1){ // if last key
                                    if (is_arr){
                                        if (is_array) inAr(store_array.getJSONArray(index));
                                        else inAr(store_object.getJSONArray(kk));
                                    }
                                    else{
                                        if (is_array) inOb(store_array.getJSONObject(index));
                                        else inOb(store_object.getJSONObject(kk));
                                    }
                                }
                                else{
                                    try {
                                        inOb(store_array.getJSONObject(index));
                                    }catch (JSONException e){
                                        try {
                                            inAr(store_array.getJSONArray(index));
                                        }catch (JSONException e1){
                                            store_array = new JSONArray();
                                            store_object = new JSONObject();
                                        }
                                    }
                                }
                            }
                            else{
                                store_array = new JSONArray();
                                break;
                            }
                        }catch (JSONException e){
                            store_array = new JSONArray();
                            store_object = new JSONObject();
                        }
                    }
                }
                else{ // if json object
                    try{ // only json object can come here
                        inOb(store_object.getJSONObject(k));
                    }
                    catch (JSONException e){
                        store_array = new JSONArray();
                        store_object = new JSONObject();
                        break;
                    }
                }
            }
        }
    }

    /**
     * this method clear previous query data
     */
    private void clear_old_query(){
        link = "";
        all_conditions = new JSONArray();
        limit_start = -1;
        limit_end = -1;
        order_by = "asc";
    }

    /**
     * clear all list
     */
    private void clear_all_list(){
        boolean_list.clear();
        integer_list.clear();
        double_list.clear();
        string_list.clear();
        array_list.clear();
        object_list.clear();
    }

    /**
     * Only four data type array_list allow in condition. Boolean, Int, Double and String
     * process_single_condition = process single condition
     * @param s
     */
    private void process_single_condition(String s[]){
        if (s[0].contains(K.dt)) {
            String ss[] = s[0].split(K.dt);
            condition_data_name = ss[0].trim();
            s[1] = s[1].trim();
            switch(ss[1].trim()){
                case "Boolean":
                    condition_boolean = Boolean.parseBoolean(s[1]);
                    ib = true;
                    break;
                case "Int":
                    try{
                        condition_int = Integer.parseInt(s[1]);
                    } catch (Exception ignored){}
                    break;
                case "Double":
                    try{
                        condition_double = Double.parseDouble(s[1]);
                    }catch (Exception ignored){}
                    break;
                case "String": condition_string = s[1];
            }
        }
        else{
            condition_data_name = s[0];
            condition_string = s[1];
        }
    }

    /**
     * store data to target field
     */
    private void store_data(int i, String s, JSONArray arr, JSONObject ob, boolean is_arr){
        String ss[] = s.split(K.d1);
        if (ss.length>1){
            JSONArray arr1 = new JSONArray();
            for (String a:ss){
                if (a.contains(K.dt)) a = a.substring(0, a.indexOf(K.dt));
                try {
                    if (is_arr) arr1.put(arr.get(Integer.parseInt(a)));
                    else arr1.put(ob.get(a));
                }
                catch (JSONException ignored){}
            }
            multi_column_data.put(arr1);
        }
        else{ // if column number 1 or 0
            if (s.contains(K.dt)) s = s.substring(0, s.indexOf(K.dt));
            int in = -1;
            if (is_arr){
                if (s.length()==0) s = "-1";
                try{in = Integer.parseInt(s);}
                catch (Exception e){return;}
            }
            try{
                switch (i){
                    case 0:
                        boolean bz;
                        if (is_arr) bz = arr.getBoolean(in);
                        else bz = ob.getBoolean(s);
                        boolean_list.add(bz);
                        break;
                    case 1:
                        int iz;
                        if (is_arr) iz = arr.getInt(in);
                        else iz = ob.getInt(s);
                        integer_list.add(iz);
                        break;
                    case 2:
                        double dz;
                        if (is_arr) dz = arr.getDouble(in);
                        else dz = ob.getDouble(s);
                        double_list.add(dz);
                        break;
                    case 3:
                        String sz;
                        if (is_arr) sz = arr.getString(in);
                        else sz = ob.getString(s);
                        string_list.add(sz);
                        break;
                    case 4:
                        JSONArray az;
                        if (is_arr) az = arr;
                        else az = ob.getJSONArray(s);
                        array_list.add(az);
                        break;
                    case 5:
                        if (is_arr){
                            JSONObject obz = arr.getJSONObject(in);
                            object_list.add(obz);
                        }
                        else object_list.add(ob);
                    default:
                        if (is_arr) multi_column_data.put(arr.get(in));
                        else multi_column_data.put(ob.get(s));
                }
            }catch (JSONException e){}
        }
    }

    /**
     * list length
     */
    private int length(int i){
        switch (i){
            case 0: return boolean_list.size();
            case 1: return integer_list.size();
            case 2: return double_list.size();
            case 3: return string_list.size();
            case 4: return array_list.size();
            case 5: return object_list.size();
            case 6: return multi_column_data.length();
            default: return 0;
        }
    }

    /**
     * output helper
     * return true if row is json array
     */
    private boolean arr_row(){
        if (first_condition_set.contains(K.dt)) first_condition_set = first_condition_set.substring(0, first_condition_set.indexOf(K.dt));
        try {
            Integer.parseInt(first_condition_set);
            return true;
        }
        catch (Exception e){return false;}
    }

    /**
     * is last number
     * return true if last element of link is number
     */
    private boolean last(){
        try{
            String s = link.substring(link.lastIndexOf('>')+1, link.length());
            Integer.parseInt(s);
            return true;
        }catch (Exception e){return false;}
    }

    /**
     * true if last element of link is array
     */
    private boolean last_ar(){
        return link.lastIndexOf(K.ar2) == link.length() - 1;
    }

    /**
     * insert data into store_array and store_object
     */
    private void inAr(JSONArray ar){
        store_array = ar;
        is_array = true;
        store_object = new JSONObject();
    }
    private void inOb(JSONObject ob){
        store_object = ob;
        is_array = false;
        store_array = new JSONArray();
    }

    /**
     * These methods used for output of query result.
     * @param db database or JSONObject that need to process.
     * @return ArrayList for single data type and JSONArray for multiple data type.
     */
    ArrayList<Boolean> out_bool(JSONObject db){
        if (arr_row()){ // array row
            if (last() || last_ar()){ // if last key is number then previous must will be array
                target(link, db, true);
                check(false, true, store_array, new JSONObject(), column, 0); // not need output type for multy column
            }
            else{
                target(link, db, false);
                check(true, true, new JSONArray(), store_object, column, 0); // not need output type for multy column
            }
        }
        else{ // object row
            if (last() || last_ar()){
                target(link, db, true);
                check(false, false, store_array, new JSONObject(), column, 0);
            }
            else{
                target(link, db, false);
                check(true, false, new JSONArray(), store_object, column, 0);
            }
        }
        return boolean_list;
    }

    ArrayList<Integer> out_int(JSONObject db){
        if (arr_row()){
            if (last() || last_ar()){
                target(link, db, true);
                check(false, true, store_array, new JSONObject(), column, 1);
            }
            else{
                target(link, db, false);
                check(true, true, new JSONArray(), store_object, column, 1);
            }
        }
        else{
            if (last() || last_ar()){
                target(link, db, true);
                check(false, false, store_array, new JSONObject(), column, 1);
            }
            else{
                target(link, db, false);
                check(true, false, new JSONArray(), store_object, column, 1);
            }
        }
        return integer_list;
    }

    ArrayList<Double> out_dub(JSONObject db){
        if (arr_row()){
            if (last() || last_ar()){
                target(link, db, true);
                check(false, true, store_array, new JSONObject(), column, 2);
            }
            else{
                target(link, db, false);
                check(true, true, new JSONArray(), store_object, column, 2);
            }
        }
        else{
            if (last() || last_ar()){
                target(link, db, true);
                check(false, false, store_array, new JSONObject(), column, 2);
            }
            else{
                target(link, db, false);
                check(true, false, new JSONArray(), store_object, column, 2);
            }
        }
        return double_list;
    }

    ArrayList<String> out_str(JSONObject db){
        if (arr_row()){
            if (last() || last_ar()){
                target(link, db, true);
                check(false, true, store_array, new JSONObject(), column, 3);
            }
            else{
                target(link, db, false);
                check(true, true, new JSONArray(), store_object, column, 3);
            }
        }
        else{
            if (last() || last_ar()){
                target(link, db, true);
                check(false, false, store_array, new JSONObject(), column, 3);
            }
            else{
                target(link, db, false);
                check(true, false, new JSONArray(), store_object, column, 3);
            }
        }
        return string_list;
    }

    ArrayList<JSONArray> out_arr(JSONObject db){
        if (arr_row()){
            if (last() || last_ar()){
                target(link, db, true);
                check(false, true, store_array, new JSONObject(), column, 4);
            }
            else{
                target(link, db, false);
                check(true, true, new JSONArray(), store_object, column, 4);
            }
        }
        else{
            if (last() || last_ar()){
                target(link, db, true);
                check(false, false, store_array, new JSONObject(), column, 4);
            }
            else{
                target(link, db, false);
                check(true, false, new JSONArray(), store_object, column, 4);
            }
        }
        return array_list;
    }

    ArrayList<JSONObject> out_ob(JSONObject db){
        if (arr_row()){
            if (last() || last_ar()){
                target(link, db, true);
                check(false, true, store_array, new JSONObject(), "", 5);
            }
            else{
                target(link, db, false);
                check(true, true, new JSONArray(), store_object, "", 5);
            }
        }
        else{
            if (last() || last_ar()){
                target(link, db, true);
                check(false, false, store_array, new JSONObject(), "", 5);
            }
            else{
                target(link, db, false);
                check(true, false, new JSONArray(), store_object, "", 5);
            }
        }
        return object_list;
    }

    /**
     * this method can out single and multi column
     */
    JSONArray out_col(JSONObject db){
        if (arr_row()){
            if (last() || last_ar()){
                target(link, db, true);
                check(false, true, store_array, new JSONObject(), column, 6);
            }
            else{
                target(link, db, false);
                check(true, true, new JSONArray(), store_object, column, 6);
            }
        }
        else{
            if (last() || last_ar()){
                target(link, db, true);
                check(false, false, store_array, new JSONObject(), column, 6);
            }
            else{
                target(link, db, false);
                check(true, false, new JSONArray(), store_object, column, 6);
            }
        }
        return multi_column_data;
    }
}

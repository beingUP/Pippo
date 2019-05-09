package net.beingup.simpletodo.pippo;

abstract class K {
    /**
     * kd -> keep binary file name.
     * sd -> store binary file name.
     * pd -> pool binary file name.
     *
     * These variables store file name for database binary files and assert folder JSON files.
     */
    final static String kd = "keep.bin", sd = "store.bin", pd = "pool.bin";

    /**
     * These variables store pool database section name.
     */
    final static String pool1 = "stable", pool2 = "non_stable";

    /**
     * st1 -> default json data for keep and store database.
     * st2 -> default json data for pool database.
     *
     * At the first time when user will create Pippo object, the system will be created <b>kd</b>, <b>sd</b> and <b>pd</b> files with these default data.
     */
    final static String st1 = "{}", st2 = "{"+pool1+":{},"+pool2+":{}}";

    /**
     * un -> unknown keep database group name and data name.
     *
     * If the user doesn't set group name for keeping data into keep database, then the <mark>un</mark> will be the group name.
     * Like before, if the user doesn't set data name for keeping data into keep database, then the <mark>un</mark> will be the data name.
     */
    final static String un = "un_known";

    /**
     * These keywords store data for pool database.
     *
     * The 'dt = data type' defined data type name of pool database.
     */
    final static String type = "type", items = "items";
    final static String dt1 = "boolean", dt2 = "int", dt3 = "double", dt4 = "string", dt5 = "array", dt6 = "object";

    /**
     * Query Keys
     */
    final static String select = "select", where = "where";
    final static String space = " ", d = "->", d1 = ",", desc = "desc";
    final static String dt = ":", c0 = ">=", c1 = "<=", c2 = "==", c3 = ">", c4 = "<", c5 = "!=", ls = ">", ar = "[]";
    final static char like = '%', ar1 = '[', ar2 = ']';
    final static String or = space+"or"+space, and = space+"and"+space, limit = "limit"+d, by = "by"+d;
}

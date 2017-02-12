package listexmobile.listex.info.listexmobile.helpers;

import android.provider.BaseColumns;

import java.util.Set;

import listexmobile.listex.info.listexmobile.models.Good;

/**
 * Created by oleg-note on 08.05.2016.
 */
public final class DBContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DBContract() {}

    /* Inner class that defines the table contents */
    public static abstract class GoodEntry implements BaseColumns {
        public static final String TABLE_NAME = "Lst_Goods";
        public static final String COLUMN_NAME_GOOD_ID = "GoodId";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_DTC = "DTC";
        public static final String COLUMN_NAME_ATTRS = "Attrs";
        public static final String COLUMN_NAME_PHOTO = "Photo";
        public static final String COLUMN_NAME_RATING = "Rating";
        public static final String COLUMN_NAME_VOTES = "Votes";
    }

    /* Inner class that defines the table contents */
    public static abstract class SetEntry implements BaseColumns {
        public static final String TABLE_NAME = "Lst_Sets";
        public static final String COLUMN_NAME_SET_ID = "SetId";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_DTC = "DTC";
    }

    /* Inner class that defines the table contents */
    public static abstract class GoodToSetEntry implements BaseColumns {
        public static final String TABLE_NAME = "Lst_GoodToSet";
        public static final String COLUMN_NAME_GOODTOSET_ID = "GoodToSetId";
        public static final String COLUMN_NAME_IS_CHECKED = "IsChecked";
        public static final String COLUMN_NAME_DTC = "DTC";
        public static final String COLUMN_NAME_SET_ID = "SetId";
        public static final String COLUMN_NAME_GOOD_ID = "GoodId";
    }

    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_GOODS =
            "CREATE TABLE IF NOT EXISTS " + GoodEntry.TABLE_NAME + " (" +
                    GoodEntry.COLUMN_NAME_GOOD_ID + " INTEGER PRIMARY KEY," +
                    GoodEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    GoodEntry.COLUMN_NAME_RATING + TEXT_TYPE + COMMA_SEP +
                    GoodEntry.COLUMN_NAME_ATTRS + TEXT_TYPE + COMMA_SEP +
                    GoodEntry.COLUMN_NAME_VOTES + TEXT_TYPE + COMMA_SEP +
                    GoodEntry.COLUMN_NAME_PHOTO + TEXT_TYPE + COMMA_SEP +
                    GoodEntry.COLUMN_NAME_DTC + TEXT_TYPE +
            " )";

    public static final String SQL_CREATE_SETS =
            "CREATE TABLE IF NOT EXISTS " + SetEntry.TABLE_NAME + " (" +
                    SetEntry.COLUMN_NAME_SET_ID + " INTEGER PRIMARY KEY," +
                    SetEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    SetEntry.COLUMN_NAME_DTC + TEXT_TYPE +
                    " )";

    public static final String SQL_CREATE_GOODSTOSETS =
            "CREATE TABLE IF NOT EXISTS " + GoodToSetEntry.TABLE_NAME + " (" +
                    GoodToSetEntry.COLUMN_NAME_GOODTOSET_ID + " INTEGER PRIMARY KEY," +
                    GoodToSetEntry.COLUMN_NAME_IS_CHECKED + "INTEGER" + COMMA_SEP +
                    GoodToSetEntry.COLUMN_NAME_SET_ID + " INTEGER," +
                    GoodToSetEntry.COLUMN_NAME_GOOD_ID + " INTEGER," +
                    GoodToSetEntry.COLUMN_NAME_DTC + TEXT_TYPE + COMMA_SEP +
                    " FOREIGN KEY ("+GoodToSetEntry.COLUMN_NAME_GOOD_ID+") REFERENCES "+GoodEntry.TABLE_NAME+"("+GoodEntry.COLUMN_NAME_GOOD_ID+")," +
                    " FOREIGN KEY ("+GoodToSetEntry.COLUMN_NAME_SET_ID+") REFERENCES "+SetEntry.TABLE_NAME+"("+SetEntry.COLUMN_NAME_SET_ID+")" +
                    " )";

    public static final String SQL_DELETE_GOODS =
            "DROP TABLE IF EXISTS " + GoodEntry.TABLE_NAME;

    public static final String SQL_DELETE_SETS =
            "DROP TABLE IF EXISTS " + SetEntry.TABLE_NAME;

    public static final String SQL_DELETE_GOODSTOSETS =
            "DROP TABLE IF EXISTS " + GoodToSetEntry.TABLE_NAME;

    public static final String SQL_SAVE_GOOD =
            "INSERT OR REPLACE INTO " + GoodEntry.TABLE_NAME + "(" + GoodEntry.COLUMN_NAME_NAME + "," +
                    GoodEntry.COLUMN_NAME_DTC + "," +
                    GoodEntry.COLUMN_NAME_PHOTO + "," +
                    GoodEntry.COLUMN_NAME_ATTRS + "," +
                    GoodEntry.COLUMN_NAME_RATING + "," +
                    GoodEntry.COLUMN_NAME_VOTES + "," +
                    ") VALUES (%1$s, %2$s, %3$s, %4$s, %5$s)";

}
package listexmobile.listex.info.listexmobile.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by oleg-note on 08.05.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "goods.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.SQL_CREATE_GOODS);
        db.execSQL(DBContract.SQL_CREATE_SETS);
        db.execSQL(DBContract.SQL_CREATE_GOODSTOSETS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.SQL_DELETE_GOODSTOSETS);
        db.execSQL(DBContract.SQL_DELETE_GOODS);
        db.execSQL(DBContract.SQL_DELETE_SETS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

package pl.pancor.android.air.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PollutionDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Pollution.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_POLLUTERS =
            "CREATE TABLE " + PollutionsContract.Polluters.TABLE_NAME + " (" +
                    PollutionsContract.Polluters._ID + TEXT_TYPE + "PRIMARY KEY," +
                    PollutionsContract.Polluters.COLUMN_NAME_POLLUTER + TEXT_TYPE + COMMA_SEP +
                    PollutionsContract.Polluters.COLUMN_NAME_MAX_AQI + INTEGER_TYPE +
            " )";

    public PollutionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}

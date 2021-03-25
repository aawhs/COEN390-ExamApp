package edu.coen390.androidapp.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Database helper class serves as the controller and main API through which
 * the database will be accessed.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Tag used for logger.
     */
    private static final String TAG = "DatabaseHelper";

    /**
     * Stores the SQL database name.
     */
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    /**
     * Stores the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * String separator for storing/retrieving arrays from DB.
     */
    public static String strSeparator = "__,__";

    /**
     * Stores the context that's received from the activity.
     */
    private Context context;

    /**
     * DatabaseHelper constructor.
     * @param context The context from the calling activity.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        Log.d(TAG, "Inside DatabaseHelper constructor.");
    }

    /**
     * Called when a DatabaseHelper object is created and creates the DB tables.
     * @param sqLiteDatabase The reference to the SQLite database being created.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d(TAG, "DatabaseHelper onCreate method called.");

        //Store SQL queries in strings.
        String CREATE_INVIGILATOR_TABLE = "CREATE TABLE " + Config.INVIGILATOR_TABLE_NAME + " ("
                + Config.INVIGILATOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.INVIGILATOR_FIRST_NAME + " TEXT NOT NULL, "
                + Config.INVIGILATOR_LAST_NAME + " TEXT NOT NULL, "
                + Config.INVIGILATOR_USERNAME + " TEXT NOT NULL, "
                + Config.INVIGILATOR_PASSWORD + " TEXT NOT NULL"
                + ")";
        Log.d(TAG, "Table created with this query: " + CREATE_INVIGILATOR_TABLE);

        String CREATE_COURSE_TABLE = "CREATE TABLE " + Config.COURSE_TABLE_NAME + " ("
                + Config.COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COURSE_INVIGILATOR_ID + " INTEGER, "
                + Config.COURSE_TITLE + " TEXT NOT NULL, "
                + Config.COURSE_CODE + " TEXT NOT NULL, "
                + Config.COURSE_DESCRIPTION + " TEXT, "
                + "FOREIGN KEY" + " (" + Config.COURSE_INVIGILATOR_ID + ") "
                + "REFERENCES " + Config.INVIGILATOR_TABLE_NAME + " ("
                + Config.INVIGILATOR_ID + ")"
                + ")";
        Log.d(TAG, "Table created with this query: " + CREATE_COURSE_TABLE);

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + Config.STUDENTS_TABLE_NAME + " ("
                + Config.STUDENT_ID + " INTEGER PRIMARY KEY, "
                + Config.STUDENT_COURSE_ID + " TEXT, "
                + Config.STUDENT_FIRST_NAME + " TEXT NOT NULL, "
                + Config.STUDENT_LAST_NAME + " TEXT NOT NULL, "
//                + Config.STUDENT_USERNAME + "TEXT NOT NULL, " // TODO: to be implemented in Sprint 3 for Student Reports
//                + Config.STUDENT_PASSWORD + "TEXT NOT NULL, " // TODO: to be implemented in Sprint 3 for Student Reports
                + "FOREIGN KEY" + " (" + Config.STUDENT_COURSE_ID + ") "
                + "REFERENCES " + Config.COURSE_TABLE_NAME + " ("
                + Config.COURSE_ID + ")"
                + ")";
        Log.d(TAG, "Table created with this query: " + CREATE_STUDENT_TABLE);

        //Create the tables.
        sqLiteDatabase.execSQL(CREATE_INVIGILATOR_TABLE);
        sqLiteDatabase.execSQL(CREATE_COURSE_TABLE);
        sqLiteDatabase.execSQL(CREATE_STUDENT_TABLE);
    }

    /**
     * Called when the database version is upgraded.
     * @param sqLiteDatabase The reference to the SQLite database being created.
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //To be handled in the event that the database version ever needs to be updated.
    }

    public void testMethod() {
        SQLiteDatabase db = this.getWritableDatabase();
    }

    /**
     * Used to convert an array into a comma-separated string to be stored in the DB.
     * @param array Array of values to be converted to comma-separated strings
     * @return Comma-separated string equivalent of the array
     */
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str = str + array[i];

            // Do not append comma at the end of last element
            if(i < array.length-1){
                str = str + strSeparator;
            }
        }
        return str;
    }

    /**
     * Used to convert a comma-separated string to an array.
     * @param str The comma-separated string to be converted into an array.
     * @return The equivalent array
     */
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }
}


package com.bds.sqlliteproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bryanswartwood on 12/8/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String ID_COLUMN = "ID";
    public static final String NAME_COLUMN = "NAME";
    public static final String SURNAME_COLUMN = "SURNAME";
    public static final String MARKS_COLUMN = "MARKS";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME
            + " ( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT"
            + ", " + NAME_COLUMN + "TEXT"
            + ", " + SURNAME_COLUMN + "TEXT"
            + ", " + MARKS_COLUMN + "INTEGER)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}

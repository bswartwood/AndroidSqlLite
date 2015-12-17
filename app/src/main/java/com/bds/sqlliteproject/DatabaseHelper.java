package com.bds.sqlliteproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public static final String CREATE_TABLE = "create table " + TABLE_NAME
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT"
            + "," + NAME_COLUMN + " TEXT"
            + "," + SURNAME_COLUMN + " TEXT"
            + "," + MARKS_COLUMN + " INTEGER)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 3);
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

    public boolean insertData(String name, String surname, String marks) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN,name);
        contentValues.put(SURNAME_COLUMN,surname);
        contentValues.put(MARKS_COLUMN, marks);
        long result = this.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateData(String id, String name, String surname, String marks) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COLUMN,id);
        contentValues.put(NAME_COLUMN,name);
        contentValues.put(SURNAME_COLUMN,surname);
        contentValues.put(MARKS_COLUMN, marks);
        long result = db.update(TABLE_NAME, contentValues, ID_COLUMN + " = ?", new String[]{id});
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer result = db.delete(TABLE_NAME, ID_COLUMN + " = ?", new String[]{id});
        return result;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor results = db.rawQuery("select * from " + TABLE_NAME, null);
        return results;
    }
}

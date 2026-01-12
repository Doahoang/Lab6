package com.example.sqlite_note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NoteDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "TBL_NOTES";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_CONTENT = "Content";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CONTENT + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    // Nâng cấp database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // ================= CRUD =================

    // Create
    public void addNote(String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENT, content);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Read
    public ArrayList<String> getAllNotes() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1)); // Content
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // Delete
    public void deleteNote(String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_CONTENT + "=?", new String[]{content});
        db.close();
    }
}

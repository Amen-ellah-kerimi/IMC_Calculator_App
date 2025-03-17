package com.example.ims_score_app.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ims_score_app.Database.DatabaseSchema;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "HealthDatabase.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseSchema.HealthEntry.TABLE_NAME + " (" +
                    DatabaseSchema.HealthEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseSchema.HealthEntry.COLUMN_NAME_POIDS + " REAL," +
                    DatabaseSchema.HealthEntry.COLUMN_NAME_TAILLE + " REAL," +
                    DatabaseSchema.HealthEntry.COLUMN_NAME_AGE + " INTEGER," +
                    DatabaseSchema.HealthEntry.COLUMN_NAME_SEXE + " TEXT," +
                    DatabaseSchema.HealthEntry.COLUMN_NAME_IMC + " REAL," +
                    DatabaseSchema.HealthEntry.COLUMN_NAME_CLASSIFICATION + " TEXT," +
                    DatabaseSchema.HealthEntry.COLUMN_NAME_DATE + " INTEGER" +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseSchema.HealthEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

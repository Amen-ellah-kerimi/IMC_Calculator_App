package com.example.ims_score_app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.ims_score_app.Database.DatabaseHelper;
import com.example.ims_score_app.Database.DatabaseSchema;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.recyclerview.widget.RecyclerView;

public class DatabaseOperations {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;


    public DatabaseOperations(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public void open() {

        database = dbHelper.getWritableDatabase();
    }


    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
        dbHelper.close();
    }


    public long insertResult(double poids, double taille, int age, String sexe, double imc, String classification) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_POIDS, poids);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_TAILLE, taille);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_AGE, age);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_SEXE, sexe);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_IMC, imc);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_CLASSIFICATION, classification);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_DATE, System.currentTimeMillis()); // te5ou wa9t automatique


        return database.insert(DatabaseSchema.HealthEntry.TABLE_NAME, null, values);
    }

    public Cursor getTousEnregistrements() {
        return database.query(DatabaseSchema.HealthEntry.TABLE_NAME,
                null,
                null, null,
                null, null,
                DatabaseSchema.HealthEntry.COLUMN_NAME_DATE + " ASC");
    }
}

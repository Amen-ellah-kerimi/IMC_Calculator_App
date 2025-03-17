package com.example.ims_score_app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.ims_score_app.Database.DatabaseHelper;
public class DatabaseOperations {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    // Constructor: Initialize with context and create DatabaseHelper
    public DatabaseOperations(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Open the database connection
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    // Close the database connection
    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
        dbHelper.close();
    }

    // Insert a new health record into the database
    public long insertResult(double poids, double taille, int age, String sexe, double imc, String classification) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_POIDS, poids);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_TAILLE, taille);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_AGE, age);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_SEXE, sexe);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_IMC, imc);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_CLASSIFICATION, classification);
        values.put(DatabaseSchema.HealthEntry.COLUMN_NAME_DATE, System.currentTimeMillis()); // te5ou wa9t automatique

        // Insert and return the row ID (-1 if failed)
        return database.insert(DatabaseSchema.HealthEntry.TABLE_NAME, null, values);
    }

    public Cursor getTousEnregistrements() {
        return database.query(DatabaseSchema.HealthEntry.TABLE_NAME,
                null, // All columns
                null, null, // No filter
                null, null, // No grouping
                DatabaseSchema.HealthEntry.COLUMN_NAME_DATE + " ASC"); // Sort by date
    }
}

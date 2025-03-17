package com.example.ims_score_app.Database;

import android.provider.BaseColumns;

public final class DatabaseSchema {

    // Private constructor to prevent instantiation
    private DatabaseSchema() {}

    /* Inner class that defines the table contents */
    public static class HealthEntry implements BaseColumns {
        public static final String TABLE_NAME = "resultas";
        public static final String COLUMN_NAME_POIDS = "poids";
        public static final String COLUMN_NAME_TAILLE = "taille";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_SEXE = "sexe";
        public static final String COLUMN_NAME_IMC = "imc";
        public static final String COLUMN_NAME_CLASSIFICATION = "classification"; // Updated column name
        public static final String COLUMN_NAME_DATE = "date";
    }
}
package com.example.ims_score_app.Database;

import android.provider.BaseColumns;

public final class DatabaseSchema {


    private DatabaseSchema() {}


    public static class HealthEntry implements BaseColumns {
        public static final String TABLE_NAME = "resultas";
        public static final String COLUMN_NAME_POIDS = "poids";
        public static final String COLUMN_NAME_TAILLE = "taille";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_SEXE = "sexe";
        public static final String COLUMN_NAME_IMC = "imc";
        public static final String COLUMN_NAME_CLASSIFICATION = "classification";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
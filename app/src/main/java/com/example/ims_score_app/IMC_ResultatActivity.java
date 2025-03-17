package com.example.ims_score_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ims_score_app.Database.DatabaseOperations;

public class IMC_ResultatActivity extends AppCompatActivity {
    private static final String TAG = "IMC_ResultatActivity";
    TextView tvIMC, tvClassification, tvIMC_range;
    Button btnRetour, btnSauvegarder;
    DatabaseOperations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc_resultat);

        // norbtou les java bil xml
        tvIMC = findViewById(R.id.tvIMC);
        tvClassification = findViewById(R.id.tvClassification);
        tvIMC_range = findViewById(R.id.tvIMC_range);
        btnRetour = findViewById(R.id.btnRetour);
        btnSauvegarder = findViewById(R.id.btnSauvegarder);

        // na3mlou initialisation ta3 db
        dbOperations = new DatabaseOperations(this);

        // nlamdou données mil page précidente
        Intent intent = getIntent();
        double poids = intent.getDoubleExtra("poids", 0.0);
        double taille = intent.getDoubleExtra("taille", 0.0);
        int age = intent.getIntExtra("age", 0);
        String sexe = intent.getStringExtra("sexe");

        // ni7sbou imc
        double tailleEnMetres = taille / 100.0;
        double imc = poids / (tailleEnMetres * tailleEnMetres);

        // ntal3ou classification mil imc
        String classification = "";
        String imcRange = "";
        if (imc < 16.0) {
            classification = "Dénutrition ou famine";
            imcRange = "< 16.0";
        } else if (imc >= 16.0 && imc <= 16.9) {
            classification = "Dénutrition ou famine";
            imcRange = "16.0 - 16.9";
        } else if (imc >= 17.0 && imc <= 18.4) {
            classification = "Maigreur";
            imcRange = "17.0 - 18.4";
        } else if (imc >= 18.5 && imc <= 24.9) {
            classification = "Corpulence normale";
            imcRange = "18.5 - 24.9";
        } else if (imc >= 25.0 && imc <= 29.9) {
            classification = "Surpoids";
            imcRange = "25.0 - 29.9";
        } else if (imc >= 30.0 && imc <= 34.9) {
            classification = "Obésité modérée";
            imcRange = "30.0 - 34.9";
        } else if (imc >= 35.0 && imc <= 39.9) {
            classification = "Obésité sévère";
            imcRange = "35.0 - 39.9";
        } else {
            classification = "Obésité morbide";
            imcRange = "≥ 40.0";
        }

        // nwariiw resulat mil xml
        afficherResultat(imc, classification, imcRange);

        String finalClassification = classification;
        btnSauvegarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbOperations.open();
                try {
                    Log.d(TAG, "Attempting to insert: poids=" + poids + ", taille=" + taille + ", age=" + age + ", sexe=" + sexe + ", imc=" + imc + ", classification=" + finalClassification);
                    long resultId = dbOperations.insertResult(poids, taille, age, sexe, imc, finalClassification);
                    Log.d(TAG, "Insert result: " + resultId);
                    if (resultId != -1) {
                        Toast.makeText(IMC_ResultatActivity.this, getString(R.string.sauvegarde_success), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Navigating to GraphActivity");
                        Intent intent = new Intent(IMC_ResultatActivity.this, GraphActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(IMC_ResultatActivity.this, getString(R.string.sauvegarde_erreur), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Save failed");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error during save", e);
                } finally {
                    dbOperations.close();
                }
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour();
            }
        });
    }

    private void afficherResultat(double imc, String classification, String imcRange) {
        tvIMC.setText(String.format("%.2f", imc));
        tvClassification.setText(classification);
        tvIMC_range.setText(imcRange);

        // Highlight the current classification
        switch (classification) {
            case "Dénutrition ou famine":
                tvClassification.setTextColor(getResources().getColor(R.color.red));
                tvIMC_range.setTextColor(getResources().getColor(R.color.red));
                break;
            case "Maigreur":
                tvClassification.setTextColor(getResources().getColor(R.color.orange));
                tvIMC_range.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "Corpulence normale":
                tvClassification.setTextColor(getResources().getColor(R.color.green));
                tvIMC_range.setTextColor(getResources().getColor(R.color.green));
                break;
            case "Surpoids":
                tvClassification.setTextColor(getResources().getColor(R.color.yellow));
                tvIMC_range.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case "Obésité modérée":
                tvClassification.setTextColor(getResources().getColor(R.color.orange));
                tvIMC_range.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "Obésité sévère":
                tvClassification.setTextColor(getResources().getColor(R.color.red));
                tvIMC_range.setTextColor(getResources().getColor(R.color.red));
                break;
            case "Obésité morbide":
                tvClassification.setTextColor(getResources().getColor(R.color.dark_red));
                tvIMC_range.setTextColor(getResources().getColor(R.color.dark_red));
                break;
        }
    }

    private void retour() {
        finish();
    }
}
package com.example.ims_score_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etPoids, etTaille, etAge;
    RadioButton rbHomme, rbFemme;
    Button btnCalculer, btnAnnuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPoids = findViewById(R.id.etPoids);
        etTaille = findViewById(R.id.etTaille);
        etAge = findViewById(R.id.etAge);
        rbHomme = findViewById(R.id.rbHomme);
        rbFemme = findViewById(R.id.rbFemme);
        btnCalculer = findViewById(R.id.btnCalculer);
        btnAnnuler = findViewById(R.id.btnAnnuler);

        btnCalculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPoids.getText().toString().isEmpty() || etTaille.getText().toString().isEmpty() || etAge.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                double poids, taille;
                int age;
                String sexe = "";

                try {
                    poids = Double.parseDouble(etPoids.getText().toString());
                    taille = Double.parseDouble(etTaille.getText().toString());
                    age = Integer.parseInt(etAge.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Veuillez entrer des valeurs valides", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!rbHomme.isChecked() && !rbFemme.isChecked()) {
                    Toast.makeText(MainActivity.this, "Veuillez sélectionner un sexe", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rbHomme.isChecked()) {
                    sexe = "Homme";
                } else if (rbFemme.isChecked()) {
                    sexe = "Femme";
                }
                Intent intent = new Intent(MainActivity.this, IMC_ResultatActivity.class);
                intent.putExtra("poids", poids);
                intent.putExtra("taille", taille);
                intent.putExtra("age", age);

                intent.putExtra("sexe", sexe );
                startActivity(intent);
                Log.d("MainActivity", "Intent started"); // Debugging
            }
        });

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annuler();
            }
        });
    }

    private void annuler() {
        etPoids.setText("");
        etTaille.setText("");
        etAge.setText("");
        rbHomme.setChecked(false);
        rbFemme.setChecked(false);
    }
}


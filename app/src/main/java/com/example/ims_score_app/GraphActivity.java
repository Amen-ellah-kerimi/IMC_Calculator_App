package com.example.ims_score_app;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ims_score_app.Database.DatabaseOperations;
import com.example.ims_score_app.Database.DatabaseSchema;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
public class GraphActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        DatabaseOperations db = new DatabaseOperations(this);
        db.open();

        RecyclerView recyclerView = findViewById(R.id.rvBaseDonnee);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<String> historyItems = new ArrayList<>();
        Cursor cursor = db.getTousEnregistrements();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        while (cursor.moveToNext()) {
            double poids = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseSchema.HealthEntry.COLUMN_NAME_POIDS));
            double taille = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseSchema.HealthEntry.COLUMN_NAME_TAILLE));
            double imc = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseSchema.HealthEntry.COLUMN_NAME_IMC));
            String classification = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HealthEntry.COLUMN_NAME_CLASSIFICATION));
            long date = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseSchema.HealthEntry.COLUMN_NAME_DATE));

            historyItems.add(String.format(Locale.getDefault(),
                    "%s\nPoids: %.1f kg | Taille: %.2f m\nIMC: %.1f (%s)",
                    sdf.format(new Date(date)),
                    poids, taille, imc, classification
            ));
        }
        cursor.close();


        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_history, parent, false);
                return new RecyclerView.ViewHolder(view) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ((TextView)holder.itemView).setText(historyItems.get(position));
            }

            @Override
            public int getItemCount() {
                return historyItems.size();
            }
        });

        findViewById(R.id.btnRetourGraph).setOnClickListener(v -> finish());
    }
}
package com.example.ims_score_app;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ims_score_app.Database.DatabaseOperations;
import com.example.ims_score_app.Database.DatabaseSchema;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GraphActivity extends AppCompatActivity {

    private static final String TAG = "GraphActivity";
    private LineChart imcChart;
    private Button btnRetourGraph;
    private DatabaseOperations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        imcChart = findViewById(R.id.imcChart);
        btnRetourGraph = findViewById(R.id.btnRetourGraph);

        dbOperations = new DatabaseOperations(this);

        setupChart();

        try {
            dbOperations.open();
            loadChartData();
        } catch (Exception e) {
            Log.e(TAG, "Error opening database or loading chart data", e);
        } finally {
        }

        btnRetourGraph.setOnClickListener(view -> {
            finish();
        });
    }

    private void setupChart() {
        imcChart.getDescription().setEnabled(false);
        imcChart.setTouchEnabled(true);
        imcChart.setDragEnabled(true);
        imcChart.setScaleEnabled(true);
        imcChart.setPinchZoom(true);
        imcChart.setDrawGridBackground(false);

        XAxis xAxis = imcChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setValueFormatter(new DateAxisValueFormatter());

        YAxis leftAxis = imcChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);

        imcChart.getAxisRight().setEnabled(false);
        imcChart.getLegend().setEnabled(true);
        imcChart.animateX(1000);
    }

    private void loadChartData() {
        List<Entry> entries = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = dbOperations.getTousEnregistrements();

            if (cursor != null && cursor.moveToFirst()) {
                int dateIndex = cursor.getColumnIndexOrThrow(DatabaseSchema.HealthEntry.COLUMN_NAME_DATE);
                int imcIndex = cursor.getColumnIndexOrThrow(DatabaseSchema.HealthEntry.COLUMN_NAME_IMC);

                do {
                    long dateTimestamp = cursor.getLong(dateIndex);
                    float imc = (float) cursor.getDouble(imcIndex);
                    entries.add(new Entry((float) dateTimestamp, imc));
                    Log.d(TAG, "Adding entry: Time=" + dateTimestamp + ", IMC=" + imc);
                } while (cursor.moveToNext());
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Error getting column index", e);
        } catch (Exception e){
            Log.e(TAG, "Error reading data from cursor", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (entries.isEmpty()) {
            Log.d(TAG, "No entries found for the chart.");
            imcChart.setNoDataText(getString(R.string.chart_no_data));
            imcChart.setNoDataTextColor(Color.BLACK);
            imcChart.clear();
            imcChart.invalidate();
            return;
        }

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.imc_graph_label));
        dataSet.setColor(ContextCompat.getColor(this, R.color.design_default_color_primary));
        dataSet.setCircleColor(ContextCompat.getColor(this, R.color.design_default_color_primary_dark));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor(Color.DKGRAY);
        dataSet.setDrawValues(true);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(ContextCompat.getColor(this, R.color.design_default_color_primary_light));
        dataSet.setFillAlpha(100);

        LineData lineData = new LineData(dataSet);
        imcChart.setData(lineData);
        imcChart.invalidate();
    }

    public static class DateAxisValueFormatter extends ValueFormatter {
        private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            try {
                long timestamp = (long) value;
                return sdf.format(new Date(timestamp));
            } catch (Exception e) {
                Log.e(TAG, "Error formatting date for axis", e);
                return "";
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbOperations != null) {
            dbOperations.close();
        }
    }
}
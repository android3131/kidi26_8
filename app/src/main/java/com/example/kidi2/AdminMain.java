package com.example.kidi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;

public class AdminMain extends AppCompatActivity {
    //private static Tag="AdminMain";
    private int[] yData = {70, 30};
    private String[] xData = {"x", "y"};
    private BarChart barChart;
    private int[] yDataTotal = {210, 140, 70, 80};
    private String[] xDataTotal = {"sport", "space", "art", "math"};
   private PieChart pieChartHours, pieChartParents, pieChartKids, pieChartTotal;

    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);





        /////////////////////////////
       navigationView = findViewById(R.id.navibarAdminMain);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottomNavigationHomeMenuId:
                        startActivity(new Intent(AdminMain.this,FirstScreen.class));
                        return true;
                    case R.id.bottomNavigationUserMenuId:
                        startActivity(new Intent(AdminMain.this,Activity.class));
                        return true;
                    case R.id.bottomNavigationActivityMenuId:
                        startActivity(new Intent(AdminMain.this,AdminMain.class));
                        return true;
                    case R.id.bottomNavigationNotificatonsMenuId:
                        startActivity(new Intent(AdminMain.this,KidName.class));
                        return true;
                    case R.id.bottomNavigationMoreMenuId:
                        startActivity(new Intent(AdminMain.this,HomeLogin.class));
                        return true;
                }
                return false;
            }
        });
        //////////////////////////


        barChart = findViewById(R.id.barChartAdmin);


        initBarChart();
        showBarChart();


        //////////////////////////////
        pieChartHours = findViewById(R.id.idPieChartHours);
        pieChartParents = findViewById(R.id.idPieChartParent);
        pieChartKids = findViewById(R.id.idPieChartKids);
        pieChartTotal = findViewById(R.id.idPieChartTotal);
        // pieChart.setDescription("Sales by employee (In Thousands $) ");
        pieChartHours.setRotationEnabled(false);
        pieChartHours.setUsePercentValues(false);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChartHours.setHoleRadius(80f);
        pieChartHours.setTransparentCircleAlpha(0);
        pieChartHours.setCenterText("250");
        pieChartHours.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        pieChartHours.setCenterTextSize(20);
        pieChartHours.getDescription().setEnabled(false);
        pieChartHours.setHighlightPerTapEnabled(false);
//////////////////////////////////

        // pieChart.setDescription("Sales by employee (In Thousands $) ");
        pieChartParents.setRotationEnabled(false);
        pieChartParents.setUsePercentValues(false);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChartParents.setHoleRadius(80f);
        pieChartParents.setTransparentCircleAlpha(0);
        pieChartParents.setCenterText("30");
        pieChartParents.setCenterTextSize(20);
        pieChartParents.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        pieChartParents.getDescription().setEnabled(false);
        pieChartParents.setHighlightPerTapEnabled(false);
        /////////////////////////////////////////////////


        pieChartKids.setRotationEnabled(false);
        pieChartKids.setUsePercentValues(false);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChartKids.setHoleRadius(80f);
        pieChartKids.setTransparentCircleAlpha(0);
        pieChartKids.setCenterText("45");
        pieChartKids.setCenterTextSize(20);
        pieChartKids.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        pieChartKids.getDescription().setEnabled(false);
        pieChartKids.setHighlightPerTapEnabled(false);
        //////////////////////////////////////////////

        pieChartTotal.setRotationEnabled(false);
        pieChartTotal.setUsePercentValues(false);
        pieChartTotal.setHoleRadius(0f);
        pieChartTotal.setTransparentCircleAlpha(0);
        //pieChartTotal.setCenterText("250");
        //pieChartTotal.setCenterTextSize(10);
        pieChartTotal.getDescription().setEnabled(false);
        pieChartTotal.setHighlightPerTapEnabled(false);
        addDataSetHours();
        addDataSetTotal();
        addDataSetParents();
        addDataSetKids();
        /*
        pieChartHours.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {


                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 7);

                for(int i = 0; i < yData.length; i++){
                    if(yData[i] == Float.parseFloat(sales)){
                        pos1 = i;
                        break;
                    }
                }
                String employee = xData[pos1 + 1];
              //  Toast.makeText(AdminMain.this, "Employee " + employee + "\n" + "Sales: $" +
                    //    sales + "K", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
*/
    }

    private void addDataSetHours() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();


        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }


        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "        Monthly Activities In Hour");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#5377ee"));
        colors.add(Color.parseColor("#d0dbff"));

        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);

        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);
        pieDataSet.setDrawValues(false);
        pieDataSet.setSliceSpace(0f);
        pieChartHours.setDrawSliceText(false);
        //add legend to chart
        Legend legend = pieChartHours.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChartHours.setData(pieData);

        pieChartHours.invalidate();
        //pieChart.getLegend().setEnabled(false);


        ////////////////////////////////

    }

    private void addDataSetTotal() {
        ArrayList<PieEntry> yEntrysTotal = new ArrayList<>();
        ArrayList<String> xEntrysTotal = new ArrayList<>();
        for (int i = 0; i < yDataTotal.length; i++) {
            yEntrysTotal.add(new PieEntry(yDataTotal[i], i));
        }

        for (int i = 1; i < xDataTotal.length; i++) {
            xEntrysTotal.add(xDataTotal[i]);
        }
        PieDataSet pieDataSetTotal = new PieDataSet(yEntrysTotal, "     Total Per Category");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#5377ee"));
        colors.add(Color.parseColor("#edc655"));


        colors.add(Color.parseColor("#0091ff"));
        colors.add(Color.parseColor("#d0dbff"));
        colors.add(Color.GRAY);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSetTotal.setSliceSpace(2);
        pieDataSetTotal.setValueTextSize(12);
        pieDataSetTotal.setColors(colors);
        pieDataSetTotal.setDrawValues(true);
        pieDataSetTotal.setSliceSpace(0f);
        // pieChartTotal.setDrawSliceText(false);
        //add legend to chart

        pieDataSetTotal.setValueTextColor(Color.WHITE);
        pieDataSetTotal.setColors(colors);
        pieDataSetTotal.setDrawValues(true);
        pieDataSetTotal.setSliceSpace(0f);
        pieChartTotal.setDrawSliceText(true);
        ValueFormatter vf = new ValueFormatter() { //value format here, here is the overridden method
            @Override
            public String getFormattedValue(float value) {
                return "" + (int) value;
            }
        };
        pieDataSetTotal.setValueFormatter(vf);
        Legend legendTotal = pieChartTotal.getLegend();
        legendTotal.setForm(Legend.LegendForm.NONE);

        // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legendTotal.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legendTotal.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legendTotal.setDrawInside(false);
        //create pie data object
        PieData pieDataTotal = new PieData(pieDataSetTotal);
        pieChartTotal.setData(pieDataTotal);

        pieChartTotal.invalidate();
    }

    private void addDataSetParents() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();


        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }


        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "               New Parents");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#edc655"));
        colors.add(Color.parseColor("#d0dbff"));

        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);

        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);
        pieDataSet.setDrawValues(false);
        pieDataSet.setSliceSpace(0f);
        pieChartParents.setDrawSliceText(false);
        //add legend to chart
        Legend legend = pieChartParents.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChartParents.setData(pieData);

        pieChartParents.invalidate();
        //pieChart.getLegend().setEnabled(false);


        ////////////////////////////////

    }

    private void addDataSetKids() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();


        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }


        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "                  New Children");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#0091ff"));
        colors.add(Color.parseColor("#d0dbff"));


        colors.add(Color.GREEN);
        colors.add(Color.CYAN);

        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);
        pieDataSet.setDrawValues(false);
        pieDataSet.setSliceSpace(0f);
        pieChartKids.setDrawSliceText(false);
        //add legend to chart
        Legend legend = pieChartKids.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChartKids.setData(pieData);

        pieChartKids.invalidate();
        //pieChart.getLegend().setEnabled(false);


        ////////////////////////////////

    }

    private void showBarChart() {
        ArrayList<Double> valueList = new ArrayList<Double>();
        ArrayList<Double> valueList2 = new ArrayList<Double>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        String title = "Title";

        //input data

        valueList.add(100.0);
        valueList.add(80.0);
        valueList.add(70.0);
        valueList.add(90.0);

        valueList2.add(80.0);
        valueList2.add(40.0);
        valueList2.add(20.0);
        valueList2.add(50.0);


        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            BarEntry barEntry = new BarEntry(i, valueList.get(i).floatValue());
            entries.add(barEntry);

            BarEntry barEntry2 = new BarEntry(i, valueList2.get(i).floatValue());
            entries2.add(barEntry2);
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);
        BarDataSet barDataSet2 = new BarDataSet(entries2, title);

        BarData data = new BarData(barDataSet);
        data.addDataSet(barDataSet2);

        data.setBarWidth(0.2f);
        data.groupBars(-0.3f,0.6f,0);

        barChart.setData(data);
        barChart.invalidate();
        initBarDataSet(barDataSet);
        //initBarDataSet(barDataSet2);
    }

    private void initBarDataSet(BarDataSet barDataSet) {
        //Changing the color of the bar
        barDataSet.setColors(new int[]{Color.parseColor("#5377ee"),
                Color.parseColor("#edc655")
                , Color.parseColor("#1191ff"), Color.parseColor("#d0dbff")});
        //Setting the size of the form in the legend
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(12f);
    }

    private void initBarChart() {
        //hiding the grey background of the chart, default false if not set
        barChart.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        barChart.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        barChart.setDrawBorders(false);

        //remove the description label text located at the lower right corner
        Description description = new Description();

        description.setEnabled(false);

        barChart.setDescription(description);


        XAxis xAxis = barChart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = barChart.getAxisRight();
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);

        Legend legend = barChart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.LINE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);

        barChart.getAxisLeft().setDrawLabels(true);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setDrawLabels(false);
        barChart.setTouchEnabled(false);
        barChart.getLegend().setEnabled(false);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
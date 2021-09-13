package com.example.kidi2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kidi2.AdminAddCourse;
import com.example.kidi2.AdminSetCourse;
import com.example.kidi2.AdminSetLeader;
import com.example.kidi2.FirstScreen;
import com.example.kidi2.R;
import com.example.kidi2.RetrofitAPI;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdminMainActivity extends AppCompatActivity {



    //private int[] yDataTotal = {23, 30, 18, 10};
    //private String[]    xDataTotal = {"sport", "space", "art", "ff"};



    ////////////////PIE CHARTS///////////////
    PieChart pieChartHours, pieChartParents, pieChartKids, pieChartTotal;



    BarChart barchart;
    BarChart mChart;


    //////////PERCENTAGE VIEWS///////////////////
    TextView  percentage_hour,percentage_Parent,percentage_Children;

    /////////NAVIGATION BAR///////////
    BottomNavigationView navigationView;

    /////////////////BUTTONS FOR DATASET/////////////
    Button btn_week , btn_month , btn_year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        ////////////RETROFIT API///////////////////////////////
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        //////////////CHARTS VIEW////////////////
        pieChartHours = findViewById(R.id.idPieChartHours);
        pieChartParents = findViewById(R.id.idPieChartParent);
        pieChartKids = findViewById(R.id.idPieChartKids);
        pieChartTotal = findViewById(R.id.idPieChartTotal);
        barchart = findViewById(R.id.barchart);


        ////////////PERCENTAGE LABELS//////////////
        navigationView=findViewById(R.id.bottom_navigation);
        percentage_hour=findViewById(R.id.textView2);
        percentage_Parent=findViewById(R.id.textView3);
        percentage_Children=findViewById(R.id.textView4);


        ////////////On CLick Listener for bottoms (Weekly-Monthly-Year)///////////
        btn_week = findViewById(R.id.weekBtn);
        btn_month = findViewById(R.id.monthBtn);
        btn_year = findViewById(R.id.yearBtn);


        //////////DEFAULT START//////////

        Call<HashMap<String,Integer>> kids = retrofitAPI.createGetActiveKidsPerMonth();
        kids.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> kids, Response<HashMap<String, Integer>> response) {
                HashMap<String,Integer> theKids = response.body();
                Integer kidsCount = theKids.get("newKids");
                Integer totalKids = theKids.get("totalKids");
                // DataKids();

                ArrayList<PieEntry> yEntrys = new ArrayList<>();
                ArrayList<String> xEntrys = new ArrayList<>();

                int[] yData = {kidsCount, totalKids};
                String[] xData = {"newKids", "totalKids"};

                for (int i = 0; i < yData.length; i++) {
                    yEntrys.add(new PieEntry(i, yData[i]));
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
                colors.add(Color.parseColor("#4A92FC"));
                colors.add(Color.parseColor("#E8EAF6"));

                pieDataSet.setColors(colors);
                pieDataSet.setDrawValues(false);
                pieDataSet.setSliceSpace(0f);
                pieChartKids.setDrawSliceText((boolean) false);
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

                Integer percent = kidsCount *100/totalKids;
                percentage_Children.setText(String.valueOf(percent) +"%");
                pieChartKids.setCenterText(kidsCount.toString());
                pieChartKids.setRotationEnabled(false);
                pieChartKids.setUsePercentValues(false);
                pieChartKids.setHoleRadius(83f);
                pieChartKids.setTransparentCircleAlpha(0);
                pieChartKids.setCenterTextSize(20);
                pieChartKids.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                pieChartKids.getDescription().setEnabled(false);
                pieChartKids.setHighlightPerTapEnabled(false);


                //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });


        Call<HashMap<String,Integer>> parents = retrofitAPI.activeParentsMonth();
        parents.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> parents, Response<HashMap<String, Integer>> response) {
                HashMap<String,Integer> theParents = response.body();
                Integer parentsCount = theParents.get("newParents");
                Integer totalParents = theParents.get("totalParents");
                //DataParents();

                ArrayList<PieEntry> yEntrys = new ArrayList<>();
                ArrayList<String> xEntrys = new ArrayList<>();

                int[] yData = {parentsCount, totalParents};
                String[] xData = {"newParents", "totalParents"};

                for (int i = 0; i < yData.length; i++) {
                    yEntrys.add(new PieEntry(yData[i], i));
                }

                for (int i = 1; i < xData.length; i++) {
                    xEntrys.add(xData[i]);
                }

                //create the data set
                PieDataSet pieDataSet = new PieDataSet(yEntrys, "                   New Parents");
                pieDataSet.setSliceSpace(2);
                pieDataSet.setValueTextSize(12);

                //add colors to dataset
                ArrayList<Integer> colors = new ArrayList<>();
                colors.add(Color.parseColor("#ffbb33"));
                colors.add(Color.parseColor("#E8EAF6"));


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


                Integer percent = parentsCount *100/totalParents;
                percentage_Parent.setText(String.valueOf(percent) +"%");
                pieChartParents.setCenterText(parentsCount.toString());
                pieChartParents.setRotationEnabled(false);
                pieChartParents.setUsePercentValues(false);
                pieChartParents.setHoleRadius(83f);
                pieChartParents.setTransparentCircleAlpha(0);
                pieChartParents.setCenterTextSize(20);
                pieChartParents.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                pieChartParents.getDescription().setEnabled(false);
                pieChartParents.setHighlightPerTapEnabled(false);

                //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });


        Call<HashMap<String,Integer>> kidsByCategory = retrofitAPI.activeKidsCategMonth();
        kidsByCategory.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> kidsByCategory, Response<HashMap<String, Integer>> response) {
                HashMap<String,Integer> categoryCount = response.body();
                Integer artCount = categoryCount.get("art");
                Integer musicCount = categoryCount.get("music");
                Integer scienceCount = categoryCount.get("sience");
                Integer animalCount = categoryCount.get("animal");
                Integer spaceCount = categoryCount.get("space");
                Integer poetryCount = categoryCount.get("poetry");

                //DataTotal();
                ArrayList<PieEntry> yEntrysTotal = new ArrayList<>();
                ArrayList<String> xEntrysTotal = new ArrayList<>();

                int[] yDataTotal = {artCount, musicCount, scienceCount, animalCount,spaceCount,poetryCount};
                String[] xDataTotal = {"art", "music", "sience", "animal", "space" , "poetry"};

                for (int i = 0; i < yDataTotal.length; i++) {
                    yEntrysTotal.add(new PieEntry(yDataTotal[i], i));
                }

                for (int i = 1; i < xDataTotal.length; i++) {
                    xEntrysTotal.add(xDataTotal[i]);
                }
                PieDataSet pieDataSetTotal = new PieDataSet(yEntrysTotal, "     Total Per Category");
                ArrayList<Integer> colors = new ArrayList<>();
                colors.add(Color.parseColor("#E8EAF6"));
                colors.add(Color.parseColor("#4A92FC"));
                colors.add(Color.parseColor("#ffbb33"));
                colors.add(Color.parseColor("#4E7FE7"));


                pieDataSetTotal.setSliceSpace(2);
                pieDataSetTotal.setValueTextSize(12);
                pieDataSetTotal.setColors(colors);
                pieDataSetTotal.setDrawValues(true);
                pieDataSetTotal.setSliceSpace(0f);
                // pieChartTotal.setDrawSliceText(false);
                //add legend to chart


                pieDataSetTotal.setColors(colors);
                pieDataSetTotal.setDrawValues(true);
                pieDataSetTotal.setSliceSpace(0f);
                //  pieChartTotal.setDrawSliceText(true);
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

                pieChartTotal.setRotationEnabled(false);
                pieChartTotal.setUsePercentValues(false);
                pieChartTotal.setHoleRadius(0f);
                pieChartTotal.setTransparentCircleAlpha(0);
                pieChartTotal.getDescription().setEnabled(false);
                pieChartTotal.setHighlightPerTapEnabled(false);


                //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });



        Call<HashMap<String,Integer>> activityTime = retrofitAPI.createGetActivityPerMonth();
        activityTime.enqueue(new Callback<HashMap<String,Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> activityTime, Response<HashMap<String, Integer>> response) {
                HashMap<String,Integer> times = response.body();

                Integer allTime = times.get("totalTime");
                Integer activeTime = times.get("activityTime");
                Integer percent = activeTime *100/allTime;
                //DataHours();
                ArrayList<PieEntry> yEntrys = new ArrayList<>();
                ArrayList<String> xEntrys = new ArrayList<>();

                Integer [] yData = {activeTime, allTime};
                String[] xData = {"activityTime", "totalTime"};

                for (int i = 0; i < yData.length; i++) {
                    yEntrys.add(new PieEntry(yData[i].intValue(), i));
                }

                for (int i = 1; i < xData.length; i++) {
                    xEntrys.add(xData[i]);
                }


                //create the data set
                PieDataSet pieDataSet = new PieDataSet(yEntrys, "          Monthly Activities In Hour");

                pieDataSet.setSliceSpace(2);
                pieDataSet.setValueTextSize(12);

                //add colors to dataset
                ArrayList<Integer> colors = new ArrayList<>();
                colors.add(Color.parseColor("#4E7FE7"));
                colors.add(Color.parseColor("#E8EAF6"));


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
                pieChartHours.setCenterText(activeTime.toString());
                percentage_hour.setText(String.valueOf(percent) +"%");
                pieChartHours.setCenterText(activeTime.toString());
                pieChartHours.setRotationEnabled(false);
                pieChartHours.setUsePercentValues(false);
                pieChartHours.setHoleRadius(83f);
                pieChartHours.setTransparentCircleAlpha(0);
                pieChartHours.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                pieChartHours.setCenterTextSize(20);
                pieChartHours.getDescription().setEnabled(false);
                pieChartHours.setHighlightPerTapEnabled(false);
                //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.home_page:
                        //startActivity(new Intent(AdminMainActivity.this, AdminUpdateCourse.class));
                        // getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, homeFragment).commit();
                        return true;

                    case R.id.users_page:
                        startActivity(new Intent(AdminMainActivity.this, AdminSetUser.class));
                        // getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, leadersFragment).commit();
                        return true;

                    case R.id.leaders_page:
                        startActivity(new Intent(AdminMainActivity.this, AdminSetLeader.class));
                        // getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, userFragment).commit();
                        return true;

                    case R.id.course_page:
                        startActivity(new Intent(AdminMainActivity.this, AdminSetCourse.class));
                        //  getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, coursesFragment).commit();
                        return true;

                    case R.id.more_page:

                        PopupMenu popup = new PopupMenu(AdminMainActivity.this, findViewById(R.id.more_page));
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.mymenu, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId()==R.id.logoutmenu)
                                    startActivity(new Intent(AdminMainActivity.this, FirstScreen.class));

                                return true;
                            }
                        });
                        popup.show();
                        return true;
                }
                return false;
            }
        });



        // week button listener
        btn_week.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Call<HashMap<String,Integer>> kids = retrofitAPI.createGetActiveKidsPerWeek();
                kids.enqueue(new Callback<HashMap<String, Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> kids, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> theKids = response.body();
                        Integer kidsCount = theKids.get("newKids");
                        Integer totalKids = theKids.get("totalKids");
                        //DataKids();

                        ArrayList<PieEntry> yEntrys = new ArrayList<>();
                        ArrayList<String> xEntrys = new ArrayList<>();

                        int[] yData = {kidsCount, totalKids};
                        String[] xData = {"newKids", "totalKids"};

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
                        colors.add(Color.parseColor("#4A92FC"));
                        colors.add(Color.parseColor("#E8EAF6"));

                        pieDataSet.setColors(colors);
                        pieDataSet.setDrawValues(false);
                        pieDataSet.setSliceSpace(0f);
                        pieChartKids.setDrawSliceText((boolean) false);
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


                        Integer percent = kidsCount *100/totalKids;
                        percentage_Children.setText(String.valueOf(percent) +"%");
                        pieChartKids.setCenterText(kidsCount.toString());
                        pieChartKids.setRotationEnabled(false);
                        pieChartKids.setUsePercentValues(false);
                        pieChartKids.setHoleRadius(83f);
                        pieChartKids.setTransparentCircleAlpha(0);
                        pieChartKids.setCenterTextSize(20);
                        pieChartKids.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        pieChartKids.getDescription().setEnabled(false);
                        pieChartKids.setHighlightPerTapEnabled(false);


                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });


                Call<HashMap<String,Integer>> parents = retrofitAPI.createGetActiveParentsPerWeek();
                parents.enqueue(new Callback<HashMap<String, Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> parents, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> theParents = response.body();
                        Integer parentsCount = theParents.get("newParents");
                        Integer totalParents = theParents.get("totalParents");
                        //DataParents();
                        ArrayList<PieEntry> yEntrys = new ArrayList<>();
                        ArrayList<String> xEntrys = new ArrayList<>();

                        int[] yData = {parentsCount, totalParents};
                        String[] xData = {"New Parents", "totalParents"};

                        for (int i = 0; i < yData.length; i++) {
                            yEntrys.add(new PieEntry(yData[i], i));
                        }

                        for (int i = 1; i < xData.length; i++) {
                            xEntrys.add(xData[i]);
                        }

                        //create the data set
                        PieDataSet pieDataSet = new PieDataSet(yEntrys, "                   New Parents");
                        pieDataSet.setSliceSpace(2);
                        pieDataSet.setValueTextSize(12);

                        //add colors to dataset
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.parseColor("#ffbb33"));
                        colors.add(Color.parseColor("#E8EAF6"));


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

                        Integer percent = parentsCount *100/totalParents;
                        percentage_Parent.setText(String.valueOf(percent) +"%");
                        pieChartParents.setCenterText(parentsCount.toString());
                        pieChartParents.setRotationEnabled(false);
                        pieChartParents.setUsePercentValues(false);
                        pieChartParents.setHoleRadius(83f);
                        pieChartParents.setTransparentCircleAlpha(0);
                        pieChartParents.setCenterTextSize(20);
                        pieChartParents.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        pieChartParents.getDescription().setEnabled(false);
                        pieChartParents.setHighlightPerTapEnabled(false);

                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });


                Call<HashMap<String,Integer>> kidsByCategory = retrofitAPI.activeKidsCategWeek();
                kidsByCategory.enqueue(new Callback<HashMap<String, Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> kidsByCategory, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> categoryCount = response.body();
                        Integer artCount = categoryCount.get("art");
                        Integer musicCount = categoryCount.get("music");
                        Integer scienceCount = categoryCount.get("sience");
                        Integer animalCount = categoryCount.get("animal");
                        Integer spaceCount = categoryCount.get("space");
                        Integer poetryCount = categoryCount.get("poetry");


                        //DataTotal();

                        ArrayList<PieEntry> yEntrysTotal = new ArrayList<>();
                        ArrayList<String> xEntrysTotal = new ArrayList<>();


                        Integer[] yDataTotal = {artCount, musicCount, scienceCount, animalCount,spaceCount,poetryCount};
                        String[] xDataTotal = {"art", "music", "sience", "animal", "space", "poetry"};

                        for (int i = 0; i < yDataTotal.length; i++) {
                            yEntrysTotal.add(new PieEntry(yDataTotal[i], i));
                        }

                        for (int i = 1; i < xDataTotal.length; i++) {
                            xEntrysTotal.add(xDataTotal[i]);
                        }
                        PieDataSet pieDataSetTotal = new PieDataSet(yEntrysTotal, "     Total Per Category");
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.parseColor("#E8EAF6"));
                        colors.add(Color.parseColor("#4A92FC"));
                        colors.add(Color.parseColor("#ffbb33"));
                        colors.add(Color.parseColor("#4E7FE7"));


                        pieDataSetTotal.setSliceSpace(2);
                        pieDataSetTotal.setValueTextSize(12);
                        pieDataSetTotal.setColors(colors);
                        pieDataSetTotal.setDrawValues(true);
                        pieDataSetTotal.setSliceSpace(0f);
                        // pieChartTotal.setDrawSliceText(false);
                        //add legend to chart


                        pieDataSetTotal.setColors(colors);
                        pieDataSetTotal.setDrawValues(true);
                        pieDataSetTotal.setSliceSpace(0f);
                        //  pieChartTotal.setDrawSliceText(true);
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

                        pieChartTotal.setRotationEnabled(false);
                        pieChartTotal.setUsePercentValues(false);
                        pieChartTotal.setHoleRadius(0f);
                        pieChartTotal.setTransparentCircleAlpha(0);
                        pieChartTotal.getDescription().setEnabled(false);
                        pieChartTotal.setHighlightPerTapEnabled(false);


                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });


                Call<HashMap<String,Integer>> activityTime = retrofitAPI.createGetActivityPerWeek();
                activityTime.enqueue(new Callback<HashMap<String,Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> activityTime, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> times = response.body();

                        Integer allTime = times.get("totalTime");
                        Integer activeTime = times.get("activityTime");
                        Integer percent = activeTime *100/allTime;
                        //DataHours();
                        ArrayList<PieEntry> yEntrys = new ArrayList<>();
                        ArrayList<String> xEntrys = new ArrayList<>();

                        Integer [] yData = {activeTime, allTime};
                        String[] xData = {"activityTime", "totalTime"};

                        for (int i = 0; i < yData.length; i++) {
                            yEntrys.add(new PieEntry(yData[i].intValue(), i));
                        }

                        for (int i = 1; i < xData.length; i++) {
                            xEntrys.add(xData[i]);
                        }


                        //create the data set
                        PieDataSet pieDataSet = new PieDataSet(yEntrys, "          Weekly Activities In Hour");

                        pieDataSet.setSliceSpace(2);
                        pieDataSet.setValueTextSize(12);

                        //add colors to dataset
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.parseColor("#4E7FE7"));
                        colors.add(Color.parseColor("#E8EAF6"));


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
                        pieChartHours.setCenterText(activeTime.toString());
                        percentage_hour.setText(String.valueOf(percent) +"%");
                        pieChartHours.setCenterText(allTime.toString());
                        pieChartHours.setRotationEnabled(false);
                        pieChartHours.setUsePercentValues(false);
                        pieChartHours.setHoleRadius(83f);
                        pieChartHours.setTransparentCircleAlpha(0);
                        pieChartHours.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        pieChartHours.setCenterTextSize(20);
                        pieChartHours.getDescription().setEnabled(false);
                        pieChartHours.setHighlightPerTapEnabled(false);
                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        // month button listener

        btn_month.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                Call<HashMap<String,Integer>> kids = retrofitAPI.createGetActiveKidsPerMonth();
                kids.enqueue(new Callback<HashMap<String, Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> kids, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> theKids = response.body();
                        Integer kidsCount = theKids.get("newKids");
                        Integer totalKids = theKids.get("totalKids");
                        // DataKids();

                        ArrayList<PieEntry> yEntrys = new ArrayList<>();
                        ArrayList<String> xEntrys = new ArrayList<>();

                        int[] yData = {kidsCount, totalKids};
                        String[] xData = {"newKids", "totalKids"};

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
                        colors.add(Color.parseColor("#4A92FC"));
                        colors.add(Color.parseColor("#E8EAF6"));

                        pieDataSet.setColors(colors);
                        pieDataSet.setDrawValues(false);
                        pieDataSet.setSliceSpace(0f);
                        pieChartKids.setDrawSliceText((boolean) false);
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

                        Integer percent = kidsCount *100/totalKids;
                        percentage_Children.setText(String.valueOf(percent) +"%");
                        pieChartKids.setCenterText(kidsCount.toString());
                        pieChartKids.setRotationEnabled(false);
                        pieChartKids.setUsePercentValues(false);
                        pieChartKids.setHoleRadius(83f);
                        pieChartKids.setTransparentCircleAlpha(0);
                        pieChartKids.setCenterTextSize(20);
                        pieChartKids.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        pieChartKids.getDescription().setEnabled(false);
                        pieChartKids.setHighlightPerTapEnabled(false);


                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });


                Call<HashMap<String,Integer>> parents = retrofitAPI.activeParentsMonth();
                parents.enqueue(new Callback<HashMap<String, Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> parents, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> theParents = response.body();
                        Integer parentsCount = theParents.get("newParents");
                        Integer totalParents = theParents.get("totalParents");
                        //DataParents();

                        ArrayList<PieEntry> yEntrys = new ArrayList<>();
                        ArrayList<String> xEntrys = new ArrayList<>();

                        int[] yData = {parentsCount, totalParents};
                        String[] xData = {"New Parents", "totalParents"};

                        for (int i = 0; i < yData.length; i++) {
                            yEntrys.add(new PieEntry(yData[i], i));
                        }

                        for (int i = 1; i < xData.length; i++) {
                            xEntrys.add(xData[i]);
                        }

                        //create the data set
                        PieDataSet pieDataSet = new PieDataSet(yEntrys, "                   New Parents");
                        pieDataSet.setSliceSpace(2);
                        pieDataSet.setValueTextSize(12);

                        //add colors to dataset
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.parseColor("#ffbb33"));
                        colors.add(Color.parseColor("#E8EAF6"));


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


                        Integer percent = parentsCount *100/totalParents;
                        percentage_Parent.setText(String.valueOf(percent) +"%");
                        pieChartParents.setCenterText(parentsCount.toString());
                        pieChartParents.setRotationEnabled(false);
                        pieChartParents.setUsePercentValues(false);
                        pieChartParents.setHoleRadius(83f);
                        pieChartParents.setTransparentCircleAlpha(0);
                        pieChartParents.setCenterTextSize(20);
                        pieChartParents.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        pieChartParents.getDescription().setEnabled(false);
                        pieChartParents.setHighlightPerTapEnabled(false);

                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });


                Call<HashMap<String,Integer>> kidsByCategory = retrofitAPI.activeKidsCategMonth();
                kidsByCategory.enqueue(new Callback<HashMap<String, Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> kidsByCategory, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> categoryCount = response.body();
                        Integer artCount = categoryCount.get("art");
                        Integer musicCount = categoryCount.get("music");
                        Integer scienceCount = categoryCount.get("sience");
                        Integer animalCount = categoryCount.get("animal");
                        Integer spaceCount = categoryCount.get("space");
                        Integer poetryCount = categoryCount.get("poetry");


                        //DataTotal();

                        ArrayList<PieEntry> yEntrysTotal = new ArrayList<>();
                        ArrayList<String> xEntrysTotal = new ArrayList<>();


                        Integer[] yDataTotal = {artCount, musicCount, scienceCount, animalCount,spaceCount,poetryCount};
                        String[] xDataTotal = {"art", "music", "sience", "animal", "space", "poetry"};

                        for (int i = 0; i < yDataTotal.length; i++) {
                            yEntrysTotal.add(new PieEntry(yDataTotal[i], i));
                        }

                        for (int i = 1; i < xDataTotal.length; i++) {
                            xEntrysTotal.add(xDataTotal[i]);
                        }
                        PieDataSet pieDataSetTotal = new PieDataSet(yEntrysTotal, "     Total Per Category");
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.parseColor("#E8EAF6"));
                        colors.add(Color.parseColor("#4A92FC"));
                        colors.add(Color.parseColor("#ffbb33"));
                        colors.add(Color.parseColor("#4E7FE7"));


                        pieDataSetTotal.setSliceSpace(2);
                        pieDataSetTotal.setValueTextSize(12);
                        pieDataSetTotal.setColors(colors);
                        pieDataSetTotal.setDrawValues(true);
                        pieDataSetTotal.setSliceSpace(0f);
                        // pieChartTotal.setDrawSliceText(false);
                        //add legend to chart


                        pieDataSetTotal.setColors(colors);
                        pieDataSetTotal.setDrawValues(true);
                        pieDataSetTotal.setSliceSpace(0f);
                        //  pieChartTotal.setDrawSliceText(true);
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

                        pieChartTotal.setRotationEnabled(false);
                        pieChartTotal.setUsePercentValues(false);
                        pieChartTotal.setHoleRadius(0f);
                        pieChartTotal.setTransparentCircleAlpha(0);
                        pieChartTotal.getDescription().setEnabled(false);
                        pieChartTotal.setHighlightPerTapEnabled(false);


                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });



                Call<HashMap<String,Integer>> activityTime = retrofitAPI.createGetActivityPerMonth();
                activityTime.enqueue(new Callback<HashMap<String,Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> activityTime, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> times = response.body();

                        Integer allTime = times.get("totalTime");
                        Integer activeTime = times.get("activityTime");
                        Integer percent = activeTime *100/allTime;
                        //DataHours();
                        ArrayList<PieEntry> yEntrys = new ArrayList<>();
                        ArrayList<String> xEntrys = new ArrayList<>();

                        Integer [] yData = {activeTime,allTime};
                        String[] xData = {"activityTime", "totalTime"};

                        for (int i = 0; i < yData.length; i++) {
                            yEntrys.add(new PieEntry(yData[i].intValue(), i));
                        }

                        for (int i = 1; i < xData.length; i++) {
                            xEntrys.add(xData[i]);
                        }


                        //create the data set
                        PieDataSet pieDataSet = new PieDataSet(yEntrys, "          Monthly Activities In Hour");

                        pieDataSet.setSliceSpace(2);
                        pieDataSet.setValueTextSize(12);

                        //add colors to dataset
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.parseColor("#4E7FE7"));
                        colors.add(Color.parseColor("#E8EAF6"));


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
                        pieChartHours.setCenterText(activeTime.toString());
                        percentage_hour.setText(String.valueOf(percent) +"%");
                        pieChartHours.setCenterText(activeTime.toString());
                        pieChartHours.setRotationEnabled(false);
                        pieChartHours.setUsePercentValues(false);
                        pieChartHours.setHoleRadius(83f);
                        pieChartHours.setTransparentCircleAlpha(0);
                        pieChartHours.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        pieChartHours.setCenterTextSize(20);
                        pieChartHours.getDescription().setEnabled(false);
                        pieChartHours.setHighlightPerTapEnabled(false);
                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });

                // Toast.makeText(MainActivity.this," youClickMonthBtn",Toast.LENGTH_SHORT).show();
            }
        });


        // year button listener

        btn_year.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Call<HashMap<String,Integer>> kids = retrofitAPI.createGetActiveKidsPerYear();
                kids.enqueue(new Callback<HashMap<String, Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> kids, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> theKids = response.body();
                        Integer kidsCount = theKids.get("newKids");
                        Integer totalKids = theKids.get("totalKids");
                        //DataKids();

                        ArrayList<PieEntry> yEntrys = new ArrayList<>();
                        ArrayList<String> xEntrys = new ArrayList<>();

                        int[] yData = {kidsCount, totalKids};
                        String[] xData = {"newKids", "totalKids"};

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
                        colors.add(Color.parseColor("#4A92FC"));
                        colors.add(Color.parseColor("#E8EAF6"));

                        pieDataSet.setColors(colors);
                        pieDataSet.setDrawValues(false);
                        pieDataSet.setSliceSpace(0f);
                        pieChartKids.setDrawSliceText((boolean) false);
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

                        Integer percent = kidsCount *100/totalKids;
                        percentage_Children.setText(String.valueOf(percent) +"%");
                        pieChartKids.setCenterText(kidsCount.toString());
                        pieChartKids.setRotationEnabled(false);
                        pieChartKids.setUsePercentValues(false);
                        pieChartKids.setHoleRadius(83f);
                        pieChartKids.setTransparentCircleAlpha(0);
                        pieChartKids.setCenterTextSize(20);
                        pieChartKids.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        pieChartKids.getDescription().setEnabled(false);
                        pieChartKids.setHighlightPerTapEnabled(false);


                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });

                Call<HashMap<String,Integer>> parents = retrofitAPI.activeParentsYear();
                parents.enqueue(new Callback<HashMap<String, Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> parents, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> theParents = response.body();
                        Integer parentsCount = theParents.get("newParents");
                        Integer totalParents = theParents.get("totalParents");
                        //DataParents();

                        ArrayList<PieEntry> yEntrys = new ArrayList<>();
                        ArrayList<String> xEntrys = new ArrayList<>();

                        int[] yData = {parentsCount, totalParents};
                        String[] xData = {"New Parents", "totalParents"};

                        for (int i = 0; i < yData.length; i++) {
                            yEntrys.add(new PieEntry(yData[i], i));
                        }

                        for (int i = 1; i < xData.length; i++) {
                            xEntrys.add(xData[i]);
                        }

                        //create the data set
                        PieDataSet pieDataSet = new PieDataSet(yEntrys, "                   New Parents");
                        pieDataSet.setSliceSpace(2);
                        pieDataSet.setValueTextSize(12);

                        //add colors to dataset
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.parseColor("#ffbb33"));
                        colors.add(Color.parseColor("#E8EAF6"));


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

                        Integer percent = parentsCount *100/totalParents;
                        percentage_Parent.setText(String.valueOf(percent) +"%");
                        pieChartParents.setCenterText(parentsCount.toString());
                        pieChartParents.setRotationEnabled(false);
                        pieChartParents.setUsePercentValues(false);
                        pieChartParents.setHoleRadius(83f);
                        pieChartParents.setTransparentCircleAlpha(0);
                        pieChartParents.setCenterTextSize(20);
                        pieChartParents.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        pieChartParents.getDescription().setEnabled(false);
                        pieChartParents.setHighlightPerTapEnabled(false);

                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });


                Call<HashMap<String,Integer>> kidsByCategory = retrofitAPI.activeKidsCategYear();
                kidsByCategory.enqueue(new Callback<HashMap<String, Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> kidsByCategory, Response<HashMap<String, Integer>> response) {

                        HashMap<String,Integer> categoryCount = response.body();
                        Integer artCount = categoryCount.get("art");
                        Integer musicCount = categoryCount.get("music");
                        Integer scienceCount = categoryCount.get("sience");
                        Integer animalCount = categoryCount.get("animal");
                        Integer spaceCount = categoryCount.get("space");
                        Integer poetryCount = categoryCount.get("poetry");


                        //DataTotal();

                        ArrayList<PieEntry> yEntrysTotal = new ArrayList<>();
                        ArrayList<String> xEntrysTotal = new ArrayList<>();


                        Integer[] yDataTotal = {artCount, musicCount, scienceCount, animalCount,spaceCount,poetryCount};
                        String[] xDataTotal = {"art", "music", "sience", "animal", "space", "poetry"};

                        for (int i = 0; i < yDataTotal.length; i++) {
                            yEntrysTotal.add(new PieEntry(yDataTotal[i], i));
                        }

                        for (int i = 1; i < xDataTotal.length; i++) {
                            xEntrysTotal.add(xDataTotal[i]);
                        }
                        PieDataSet pieDataSetTotal = new PieDataSet(yEntrysTotal, "     Total Per Category");
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.parseColor("#E8EAF6"));
                        colors.add(Color.parseColor("#4A92FC"));
                        colors.add(Color.parseColor("#ffbb33"));
                        colors.add(Color.parseColor("#4E7FE7"));


                        pieDataSetTotal.setSliceSpace(2);
                        pieDataSetTotal.setValueTextSize(12);
                        pieDataSetTotal.setColors(colors);
                        pieDataSetTotal.setDrawValues(true);
                        pieDataSetTotal.setSliceSpace(0f);
                        // pieChartTotal.setDrawSliceText(false);
                        //add legend to chart


                        pieDataSetTotal.setColors(colors);
                        pieDataSetTotal.setDrawValues(true);
                        pieDataSetTotal.setSliceSpace(0f);
                        //  pieChartTotal.setDrawSliceText(true);
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
                        pieChartTotal.setRotationEnabled(false);
                        pieChartTotal.setUsePercentValues(false);
                        pieChartTotal.setHoleRadius(0f);
                        pieChartTotal.setTransparentCircleAlpha(0);
                        pieChartTotal.getDescription().setEnabled(false);
                        pieChartTotal.setHighlightPerTapEnabled(false);


                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });


                Call<HashMap<String,Integer>> activityTime = retrofitAPI.createGetActivityPerYear();
                activityTime.enqueue(new Callback<HashMap<String,Integer>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, Integer>> activityTime, Response<HashMap<String, Integer>> response) {
                        HashMap<String,Integer> times = response.body();

                        Integer allTime = times.get("totalTime");
                        Integer activeTime = times.get("activityTime");
                        Integer percent = activeTime *100/allTime;
                        //DataHours();
                        ArrayList<PieEntry> yEntrys = new ArrayList<>();
                        ArrayList<String> xEntrys = new ArrayList<>();

                        Integer [] yData = {activeTime, allTime};
                        String[] xData = {"activityTime", "totalTime"};

                        for (int i = 0; i < yData.length; i++) {
                            yEntrys.add(new PieEntry(yData[i].intValue(), i));
                        }

                        for (int i = 1; i < xData.length; i++) {
                            xEntrys.add(xData[i]);
                        }


                        //create the data set
                        PieDataSet pieDataSet = new PieDataSet(yEntrys, "          Yearly Activities In Hour");

                        pieDataSet.setSliceSpace(2);
                        pieDataSet.setValueTextSize(12);

                        //add colors to dataset
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.parseColor("#4E7FE7"));
                        colors.add(Color.parseColor("#E8EAF6"));


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

                        percentage_hour.setText(String.valueOf(percent) +"%");
                        pieChartHours.setCenterText(activeTime.toString());
                        pieChartHours.setCenterText(allTime.toString());
                        pieChartHours.setRotationEnabled(false);
                        pieChartHours.setUsePercentValues(false);
                        pieChartHours.setHoleRadius(83f);
                        pieChartHours.setTransparentCircleAlpha(0);
                        pieChartHours.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        pieChartHours.setCenterTextSize(20);
                        pieChartHours.getDescription().setEnabled(false);
                        pieChartHours.setHighlightPerTapEnabled(false);
                        //Toast.makeText(MainActivity.this," youClickWeekBtn",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                        Toast.makeText(AdminMainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                // Toast.makeText(MainActivity.this," youClickYearBtn",Toast.LENGTH_SHORT).show();
            }
        });




        //////////////////////////////////////////////

        //DataHours();
        // DataParents();
        //DataTotal();
        //DataKids();
        GroupBarChart();
    }

    public void GroupBarChart(){
        mChart = (BarChart) findViewById(R.id.barchart);
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        // empty labels so that the names are spread evenly
        String[] labels = {"", "1", "2", "3", "4", "5","6","7","8","9","10","11","12", ""};
        XAxis xAxis = mChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisMinimum(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisMinimum(0);

        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(2);
        leftAxis.setLabelCount(7, true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);



        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);

        float[] valOne = {10, 10, 10, 10, 10};
        float[] valTwo = {60, 50, 40, 30, 20};
        float[] valThree = {50, 60, 20, 10, 30};
        float[] valfour = {50, 80, 10, 10, 30};
        float[] valfive = {50, 70, 90, 10, 20};

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        ArrayList<BarEntry> barThree = new ArrayList<>();
        ArrayList<BarEntry> barFour = new ArrayList<>();
        ArrayList<BarEntry> barFive= new ArrayList<>();
        for (int i = 0; i < valOne.length; i++) {
            barOne.add(new BarEntry(i, valOne[i]));
            barTwo.add(new BarEntry(i, valTwo[i]));
            barThree.add(new BarEntry(i, valThree[i]));
            barFour.add(new BarEntry(i, valfour[i]));
            barFive.add(new BarEntry(i, valfive[i]));
        }

        BarDataSet set1 = new BarDataSet(barOne, "barOne");
        set1.setColor(Color.BLUE);
        BarDataSet set2 = new BarDataSet(barTwo, "barTwo");
        set2.setColor(Color.parseColor("#4E7FE7"));

        BarDataSet set3 = new BarDataSet(barThree, "barThree");
        set3.setColor(Color.GREEN);

        BarDataSet set4 = new BarDataSet(barFour, "barFour");
        set4.setColor(Color.parseColor("#ffbb33"));

        BarDataSet set5 = new BarDataSet(barFive, "barFive");
        set5.setColor(Color.parseColor("#4A92FC"));
        //  barDataSet.setColors(new int[]{Color.parseColor("#4A92FC"),
        //  Color.parseColor("#ffbb33"), Color.parseColor("#4E7FE7"), Color.GRAY, Color.BLUE});

        set1.setHighlightEnabled(false);
        set2.setHighlightEnabled(false);
        set3.setHighlightEnabled(false);
        set4.setHighlightEnabled(false);
        set5.setHighlightEnabled(false);
        set1.setDrawValues(false);
        set2.setDrawValues(false);
        set3.setDrawValues(false);
        set4.setDrawValues(false);
        set5.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
        dataSets.add(set5);
        BarData data = new BarData(dataSets);
        float groupSpace = 0.5f;
        float barSpace = 0.01f;
        float barWidth = 0.1f;
        // (barSpace + barWidth) * 2 + groupSpace = 1
        data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(labels.length - 1.1f);
        mChart.setData(data);
        mChart.setScaleEnabled(false);
        mChart.setVisibleXRangeMaximum(6f);
        mChart.groupBars(1f, groupSpace, barSpace);
        mChart.invalidate();
        mChart.setBackgroundColor(Color.WHITE);

    }

    private void showBarChart() {
        ArrayList<Double> valueList = new ArrayList<Double>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        //input data
        // for (int i = 0; i < 5; i++) {
        valueList.add((double) 50);
        valueList.add((double) 60);
        valueList.add((double) 90);
        valueList.add((double) 200);
        valueList.add((double) 150);
        //}

        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            BarEntry barEntry = new BarEntry(i, valueList.get(i).floatValue());
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, "");
        barDataSet.setColors(new int[]{Color.parseColor("#4A92FC"),
                Color.parseColor("#ffbb33"), Color.parseColor("#4E7FE7"), Color.GRAY, Color.BLUE});
        BarData data = new BarData(barDataSet);
        barchart.setData(data);
        barchart.invalidate();
        //Changing the color of the bar
        //barDataSet.setColor(Color.parseColor("#304567"));
        //Setting the size of the form in the legend
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);

        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(0f);
        barchart.setDoubleTapToZoomEnabled(false);

    }

    private void initBarChart() {
        //hiding the grey background of the chart, default false if not set
        barchart.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        barchart.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        barchart.setDrawBorders(false);

        //remove the description label text located at the lower right corner
        Description description = new Description();
        description.setEnabled(false);
        barchart.setDescription(description);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        barchart.animateY(1000);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        barchart.animateX(1000);
        barchart.getDescription().setEnabled(false);
        barchart.setHighlightPerTapEnabled(false);
        XAxis xAxis = barchart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barchart.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = barchart.getAxisRight();
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);

        Legend legend = barchart.getLegend();
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
        xAxis.setDrawLabels(false);
        barchart.setHighlightPerTapEnabled(false);
        barchart.getAxisLeft().setDrawLabels(true);
        barchart.getAxisRight().setDrawLabels(false);
        barchart.getXAxis().setDrawLabels(false);
        barchart.setTouchEnabled(false);
        barchart.getLegend().setEnabled(false);




    }

/*
    private void DataHours() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();


        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }


        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "          Monthly Activities In Hour");

        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#4E7FE7"));
        colors.add(Color.parseColor("#E8EAF6"));


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

    private void DataTotal() {
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
        colors.add(Color.parseColor("#E8EAF6"));
        colors.add(Color.parseColor("#4A92FC"));
        colors.add(Color.parseColor("#ffbb33"));
        colors.add(Color.parseColor("#4E7FE7"));


        pieDataSetTotal.setSliceSpace(2);
        pieDataSetTotal.setValueTextSize(12);
        pieDataSetTotal.setColors(colors);
        pieDataSetTotal.setDrawValues(true);
        pieDataSetTotal.setSliceSpace(0f);
        // pieChartTotal.setDrawSliceText(false);
        //add legend to chart


        pieDataSetTotal.setColors(colors);
        pieDataSetTotal.setDrawValues(true);
        pieDataSetTotal.setSliceSpace(0f);
        //  pieChartTotal.setDrawSliceText(true);
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

    private void DataParents() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();


        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "                   New Parents");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#ffbb33"));
        colors.add(Color.parseColor("#E8EAF6"));


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

    private void DataKids() {

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
        colors.add(Color.parseColor("#4A92FC"));
        colors.add(Color.parseColor("#E8EAF6"));

        pieDataSet.setColors(colors);
        pieDataSet.setDrawValues(false);
        pieDataSet.setSliceSpace(0f);
        pieChartKids.setDrawSliceText((boolean) false);
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

 */
}

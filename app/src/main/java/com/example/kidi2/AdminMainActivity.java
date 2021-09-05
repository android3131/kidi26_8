package com.example.kidi2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminMainActivity extends AppCompatActivity {
    PieChart activitiesChart;
    PieChart parentsChart;
    BarChart barChart;
    PieChart childrenChart;
    PieChart totalCatChart;
    // variable for our bar data set.
    BarDataSet barCategory1, barCategory2, barCategory3, barCategory4, barCategory5;
    HomeFragment homeFragment = new HomeFragment();
    LeadersFragment leadersFragment = new LeadersFragment();
    UserFragment userFragment = new UserFragment();
    CoursesFragment coursesFragment = new CoursesFragment();
    MoreFragment moreFragment = new MoreFragment();

    // buttons
    Button button ;
    Button week ;
    Button month ;
    Button year ;


    // array list for storing entries.
    ArrayList barEntries;

    //initializing percentage texts
    TextView activitiesText ;
    TextView parentsText ;
    TextView childrenText ;

    // creating a string array for displaying days.
    String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Thursday", "Friday", "Saturday"};
    String[] weeks = new String[]{"Week 1","Week 2","Week 3","Week 4","Week 5"};
    String[] months = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
    int[] colorClassArray = new int[]{Color.parseColor("#5377EE"),Color.parseColor("#EDC655"),
            Color.parseColor("#0091FF"),Color.parseColor("#D0DBFF"),Color.parseColor("#FFB0B1") };


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8090/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
    // create an instance for our retrofit api class.
    RetrofitAPIAdminMain retrofitAPI = retrofit.create(RetrofitAPIAdminMain.class);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        barChart = findViewById(R.id.bar_chart);
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);

        //find texts inputs
         activitiesText = (TextView) findViewById(R.id.activities_perc);
         parentsText = (TextView) findViewById(R.id.parents_perc);
         childrenText = (TextView) findViewById(R.id.children_perc);

        // retrieve data by month as default
        Call<HashMap<String,Integer>> newKids = retrofitAPI.createGetActiveKidsPerMonth();
        Call<HashMap<String,Integer>> newParents = retrofitAPI.createGetActiveParentsPerMonth();
        Call<HashMap<String,Integer>> activityTime = retrofitAPI.createGetActivityPerMonth();
        Call<HashMap<String,Integer>> kidsByCategory = retrofitAPI.createGetActiveKidsPerCategoryPerMonth();


        //find charts in layout
        Log.d("Mohammad","here");
        PieChart newKidsChart = (PieChart)findViewById(R.id.children_chart);
        PieChart newParentsChart = (PieChart)findViewById(R.id.parents_chart);
        PieChart activityTimeChart = (PieChart)findViewById(R.id.activities_chart);
        PieChart kidsByCategoryChart = (PieChart)findViewById(R.id.totelPerCat_chart);

        //get the data
        Log.d("Mohammad","here here");

        newKids.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.d("Mohammad","success");
                    initialzieChildrenChart(response.body());

                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","failed");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();
            }
        });
        newParents.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.d("Mohammad", ""+response.body().get("totalParents"));
                    initialzieParentsChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","failed");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();
            }
        });

        activityTime.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    initialzieActivitiesChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","6th");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();

            }
        });

        kidsByCategory.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    initialzieTotalCatChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","6th");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();

            }
        });

        showBarChart(weeks, "weeks");

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.home_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, homeFragment).commit();
                        return true;

                    case R.id.leaders_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, leadersFragment).commit();
                        return true;

                    case R.id.users_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, userFragment).commit();
                        return true;

                    case R.id.course_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, coursesFragment).commit();
                        return true;

                    case R.id.more_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, moreFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }

    private void showBarChart(String[] dataArray, String s) {
        // creating a new bar data set.
        barCategory1 = new BarDataSet(getBarEntriesOne(s), "art");
        barCategory1.setColor(getApplicationContext().getResources().getColor(R.color.purple_200));
        barCategory2 = new BarDataSet(getBarEntriesTwo(s), "music");
        barCategory2.setColor(Color.BLUE);
        barCategory3 = new BarDataSet(getBarEntriesThree(s), "science");
        barCategory3.setColor(getApplicationContext().getResources().getColor(R.color.black));
        barCategory4 = new BarDataSet(getBarEntriesFour(s), "space");
        barCategory4.setColor(getApplicationContext().getResources().getColor(R.color.parents_colour));
        barCategory5 = new BarDataSet(getBarEntriesFive(s), "poetry");
        barCategory5.setColor(getApplicationContext().getResources().getColor(R.color.children_colour));

        // below line is to add bar data set to our bar data.
        BarData data = new BarData(barCategory1, barCategory2, barCategory3, barCategory4, barCategory5);

        // after adding data to our bar data we
        // are setting that data to our bar chart.
        barChart.setData(data);

        // below line is to remove description
        // label of our bar chart.
        barChart.getDescription().setEnabled(false);

        // below line is to get x axis
        // of our bar chart.
        XAxis xAxis = barChart.getXAxis();

        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dataArray));

        // below line is to set center axis
        // labels to our bar chart.
        xAxis.setCenterAxisLabels(true);

        // below line is to set position
        // to our x-axis to bottom.
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // below line is to set granularity
        // to our x axis labels.
        xAxis.setGranularity(1);

        // below line is to enable
        // granularity to our x axis.
        xAxis.setGranularityEnabled(true);

        // below line is to make our
        // bar chart as draggable.
        barChart.setDragEnabled(true);

        // below line is to make visible
        // range for our bar chart.
        barChart.setVisibleXRangeMaximum(3);

        // below line is to add bar
        // space to our chart.
        float barSpace = 0.1f;

        // below line is use to add group
        // spacing to our bar chart.
        float groupSpace = 0.5f;

        // we are setting width of
        // bar in below line.
        data.setBarWidth(0.15f);

        // below line is to set minimum
        // axis to our chart.
        barChart.getXAxis().setAxisMinimum(0);

        // below line is to
        // animate our chart.
        barChart.animate();

        // below line is to group bars
        // and add spacing to it.
        barChart.groupBars(0, groupSpace, barSpace);

        // below line is to invalidate
        // our bar chart.
        barChart.invalidate();
    }

    private List<BarEntry> getBarEntriesFive(String s) {
        // creating a new array list
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        if(s.equals("days")) {
                barEntries.add(new BarEntry(1f, 3));
                barEntries.add(new BarEntry(2f, 11));
                barEntries.add(new BarEntry(3f, 5));
                barEntries.add(new BarEntry(4f, 2));
                barEntries.add(new BarEntry(5f, 6));
                barEntries.add(new BarEntry(6f, 3));
                barEntries.add(new BarEntry(7f, 1));

            } else if (s.equals("weeks")) {
                barEntries.add(new BarEntry(1f, 2));
                barEntries.add(new BarEntry(2f, 2));
                barEntries.add(new BarEntry(3f, 3));
                barEntries.add(new BarEntry(4f, 2));
                barEntries.add(new BarEntry(5f, 0));
            } else if (s.equals("months")){
                barEntries.add(new BarEntry(1f, 2));
            barEntries.add(new BarEntry(2f, 1));
            barEntries.add(new BarEntry(3f, 1));
            barEntries.add(new BarEntry(4f, 2));
            barEntries.add(new BarEntry(5f, 0));
            barEntries.add(new BarEntry(6f, 2));
            barEntries.add(new BarEntry(7f, 2));
            barEntries.add(new BarEntry(8f, 0));
            barEntries.add(new BarEntry(9f, 1));
            barEntries.add(new BarEntry(10f, 1));
            barEntries.add(new BarEntry(11f, 1));
            barEntries.add(new BarEntry(12f, 0));
        }
        return barEntries;
    }

    private List<BarEntry> getBarEntriesFour(String s) {
        // creating a new array list
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, 8));
        barEntries.add(new BarEntry(2f, 2));
        barEntries.add(new BarEntry(3f, 9));
        barEntries.add(new BarEntry(4f, 10));
        barEntries.add(new BarEntry(5f, 17));
        barEntries.add(new BarEntry(6f, 5));
        return barEntries;
    }

    private List<BarEntry> getBarEntriesThree(String s) {
        // creating a new array list
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, 2));
        barEntries.add(new BarEntry(2f, 12));
        barEntries.add(new BarEntry(3f, 4));
        barEntries.add(new BarEntry(4f, 6));
        barEntries.add(new BarEntry(5f, 22));
        barEntries.add(new BarEntry(6f, 13));
        return barEntries;
    }


    private List<BarEntry> getBarEntriesTwo(String s) {
        // creating a new array list
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, 8));
        barEntries.add(new BarEntry(2f, 12));
        barEntries.add(new BarEntry(3f, 4));
        barEntries.add(new BarEntry(4f, 1));
        barEntries.add(new BarEntry(5f, 7));
        barEntries.add(new BarEntry(6f, 3));
        return barEntries;
    }

    private List<BarEntry> getBarEntriesOne(String s) {
        // creating a new array list
        ArrayList<BarEntry> barEntries = new ArrayList<>();


        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, 4));
        barEntries.add(new BarEntry(2f, 6));
        barEntries.add(new BarEntry(3f, 8));
        barEntries.add(new BarEntry(4f, 2));
        barEntries.add(new BarEntry(5f, 4));
        barEntries.add(new BarEntry(6f, 1));
        return barEntries;
    }

    private void initialzieTotalCatChart(HashMap<String, Integer> map) {
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        Map<String, Integer> typeAmountMap = map;
        pieEntries.add(new PieEntry(typeAmountMap.get("art")));
        pieEntries.add(new PieEntry(typeAmountMap.get("music")));
        pieEntries.add(new PieEntry(typeAmountMap.get("science")));
        pieEntries.add(new PieEntry(typeAmountMap.get("space")));
        pieEntries.add(new PieEntry(typeAmountMap.get("poetry")));


//        for(String type: typeAmountMap.keySet()){
//            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue()));
//        }

        totalCatChart = findViewById(R.id.totelPerCat_chart);
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"        Total per Category");
        pieDataSet.setColors(colorClassArray);
        ValueFormatter formatter = new ValueFormatter() {
            public String getFormatterValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler){
                return ""+((int)Math.round(value));
            }
        };
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(formatter);
        pieDataSet.setValueTextSize(12);

        totalCatChart.setRotationEnabled(false);
        totalCatChart.setUsePercentValues(false);
        totalCatChart.setHoleRadius(0f);
        totalCatChart.setTransparentCircleAlpha(0);
        totalCatChart.getDescription().setEnabled(false);
        totalCatChart.setHighlightPerTapEnabled(false);
        totalCatChart.setHoleRadius(0);
        totalCatChart.setTransparentCircleRadius(0);
        totalCatChart.getDescription().setEnabled(false);
        Legend legendTotal = totalCatChart.getLegend();
        legendTotal.setForm(Legend.LegendForm.NONE);

        // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legendTotal.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legendTotal.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legendTotal.setDrawInside(false);
        //create pie data object
        totalCatChart.setData(pieData);
        totalCatChart.animateY(1400,Easing.EaseInOutQuad);
        totalCatChart.invalidate();
    }

    private void initialzieParentsChart(HashMap<String, Integer> map) {
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        Map<String, Integer> typeAmountMap = map;
        pieEntries.add(new PieEntry(typeAmountMap.get("New Parents")));
        pieEntries.add(new PieEntry(typeAmountMap.get("totalParents")-typeAmountMap.get("New Parents")));
//        for(String type: typeAmountMap.keySet()){
//            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue()));
//        }
        parentsChart = findViewById(R.id.parents_chart);
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"        New Parents");
        pieDataSet.setColors(Color.parseColor("#EDC655"),Color.parseColor("#D0DBFF"));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //update percentage
        double d = ((Double.valueOf(typeAmountMap.get("New Parents")))/(Double.valueOf(typeAmountMap.get("totalParents")))*100);
        parentsText.setText(""+new DecimalFormat("##.##").format(d)+"%");

        parentsChart.setRotationEnabled(false);
        parentsChart.setUsePercentValues(false);
        parentsChart.setHoleRadius(83f);
        parentsChart.setTransparentCircleAlpha(0);
        parentsChart.setCenterText(Integer.toString(typeAmountMap.get("New Parents")));
        parentsChart.setCenterTextSize(20);
        parentsChart.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        parentsChart.getDescription().setEnabled(false);
        parentsChart.setHighlightPerTapEnabled(false);

        pieDataSet.setDrawValues(false);
        pieDataSet.setSliceSpace(0f);
        parentsChart.setDrawSliceText(false);
        //add legend to chart
        Legend legend = parentsChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);

        parentsChart.setData(pieData);
        parentsChart.animateY(1400, Easing.EaseInOutQuad);
        parentsChart.invalidate();

    }

    private void initialzieChildrenChart(HashMap<String, Integer> map) {
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        Map<String, Integer> typeAmountMap = map;
        pieEntries.add(new PieEntry(typeAmountMap.get("newKids")));
        pieEntries.add(new PieEntry(typeAmountMap.get("totalKids")-typeAmountMap.get("newKids")));
//        for(String type: typeAmountMap.keySet()){
//            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue()));
//        }
        childrenChart = findViewById(R.id.children_chart);
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"    New Children");
        pieDataSet.setColors(Color.parseColor("#0091FF"),Color.parseColor("#D0DBFF"));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        //update percentage
        double d = ((Double.valueOf(typeAmountMap.get("newKids")))/(Double.valueOf(typeAmountMap.get("totalKids")))*100);
        childrenText.setText(""+new DecimalFormat("##.##").format(d)+"%");


        ////
        childrenChart.setRotationEnabled(false);
        childrenChart.setUsePercentValues(false);
        childrenChart.setHoleRadius(83f);
        childrenChart.setTransparentCircleAlpha(0);
        childrenChart.setCenterText(Integer.toString(typeAmountMap.get("newKids")));
        childrenChart.setCenterTextSize(20);
        childrenChart.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        childrenChart.getDescription().setEnabled(false);
        childrenChart.setHighlightPerTapEnabled(false);

        pieDataSet.setDrawValues(false);
        pieDataSet.setSliceSpace(0f);
        childrenChart.setDrawSliceText(false);
        //add legend to chart
        Legend legend = childrenChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        childrenChart.setData(pieData);
        childrenChart.animateY(1400, Easing.EaseInOutQuad);
        childrenChart.invalidate();
    }

    private void initialzieActivitiesChart(HashMap<String, Integer> map) {
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        Map<String, Integer> typeAmountMap = map;
        pieEntries.add(new PieEntry(typeAmountMap.get("activityTime")));
        pieEntries.add(new PieEntry(typeAmountMap.get("totalTime")-typeAmountMap.get("activityTime")));
//        for(String type: typeAmountMap.keySet()){
//            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue()));
//        }
        activitiesChart = findViewById(R.id.activities_chart);
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Monthly Activities in Hour");
        pieDataSet.setColors(Color.parseColor("#5377EE"),Color.parseColor("#D0DBFF"));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        //update percentage
        double d = ((Double.valueOf(typeAmountMap.get("activityTime")))/(Double.valueOf(typeAmountMap.get("totalTime")))*100);
        activitiesText.setText(""+new DecimalFormat("##.##").format(d)+"%");

        activitiesChart.setRotationEnabled(false);
        activitiesChart.setUsePercentValues(false);
        activitiesChart.setHoleRadius(83f);
        activitiesChart.setTransparentCircleAlpha(0);
        activitiesChart.setCenterText(Integer.toString(typeAmountMap.get("activityTime")));
        activitiesChart.setCenterTextSize(20);
        activitiesChart.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        activitiesChart.getDescription().setEnabled(false);
        activitiesChart.setHighlightPerTapEnabled(false);

        pieDataSet.setDrawValues(false);
        pieDataSet.setSliceSpace(0f);
        activitiesChart.setDrawSliceText(false);
        //add legend to chart
        Legend legend = activitiesChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        activitiesChart.setData(pieData);
        activitiesChart.animateY(1400,Easing.EaseInOutQuad);
        activitiesChart.invalidate();
    }





    public void buttonPressed(View view) {
         button = findViewById(view.getId());
         week = findViewById(R.id.week_button);
         month = findViewById(R.id.month_button);
         year = findViewById(R.id.year_button);

        if (button.isSelected()) {
            button.setSelected(false);
            button.setTextColor(Color.parseColor("#000000"));
        } else if (!button.isSelected()){
            button.setSelected(true);
            button.setTextColor(Color.parseColor("#ffffff"));
        }
        if(week.isSelected() && month.isSelected()){
            if(view.getId()==week.getId()){
                month.setSelected(false);
                month.setTextColor(Color.parseColor("#000000"));

            }
            if(view.getId()==month.getId()){
                week.setSelected(false);
                week.setTextColor(Color.parseColor("#000000"));

            }
        }
        if(month.isSelected() && year.isSelected()){
            if(view.getId()==month.getId()){
                year.setSelected(false);
                year.setTextColor(Color.parseColor("#000000"));

            }
            if(view.getId()== year.getId()){
                month.setSelected(false);
                month.setTextColor(Color.parseColor("#000000"));

            }
        }
        if(week.isSelected() && year.isSelected()){
            if(view.getId()==week.getId()){
                year.setSelected(false);
                year.setTextColor(Color.parseColor("#000000"));

            }
            if(view.getId()== year.getId()){
                week.setSelected(false);
                week.setTextColor(Color.parseColor("#000000"));

            }
        }
        if(week.isSelected()){
            refreshByWeek();
        }
        else if(month.isSelected()){
            refreshByMonth();
        }
        else if(year.isSelected()){
            refreshByYear();
        }
    }

    private void refreshByYear() {
        // update bar chart
        showBarChart(months,"months");

        // update the rest of the charts
        // retrieve data by month as default
        Call<HashMap<String,Integer>> newKids = retrofitAPI.createGetActiveKidsPerYear();
        Call<HashMap<String,Integer>> newParents = retrofitAPI.createGetActiveParentsPerYear();
        Call<HashMap<String,Integer>> activityTime = retrofitAPI.createGetActivityPerYear();
        Call<HashMap<String,Integer>> kidsByCategory = retrofitAPI.createGetActiveKidsPerCategoryPerYear();

        newKids.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.d("Mohammad","success");
                    initialzieChildrenChart(response.body());

                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","failed");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();
            }
        });
        newParents.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.d("Mohammad", ""+response.body().get("totalParents"));
                    initialzieParentsChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","failed");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();
            }
        });

        activityTime.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    initialzieActivitiesChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","6th");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();

            }
        });

        kidsByCategory.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    initialzieTotalCatChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","6th");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();

            }
        });




    }

    private void refreshByMonth() {
        //update bar chart
        showBarChart(weeks,"weeks");

        // update the rest of the charts
        // retrieve data by month as default
        Call<HashMap<String,Integer>> newKids = retrofitAPI.createGetActiveKidsPerMonth();
        Call<HashMap<String,Integer>> newParents = retrofitAPI.createGetActiveParentsPerMonth();
        Call<HashMap<String,Integer>> activityTime = retrofitAPI.createGetActivityPerMonth();
        Call<HashMap<String,Integer>> kidsByCategory = retrofitAPI.createGetActiveKidsPerCategoryPerMonth();

        newKids.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.d("Mohammad","success");
                    initialzieChildrenChart(response.body());

                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","failed");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();
            }
        });
        newParents.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.d("Mohammad", ""+response.body().get("totalParents"));
                    initialzieParentsChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","failed");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();
            }
        });

        activityTime.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    initialzieActivitiesChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","6th");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();

            }
        });

        kidsByCategory.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    initialzieTotalCatChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","6th");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void refreshByWeek() {
        //update bar chart
        showBarChart(days,"days");

        // update the rest of the charts
        // retrieve data by month as default
        Call<HashMap<String,Integer>> newKids = retrofitAPI.createGetActiveKidsPerWeek();
        Call<HashMap<String,Integer>> newParents = retrofitAPI.createGetActiveParentsPerWeek();
        Call<HashMap<String,Integer>> activityTime = retrofitAPI.createGetActivityPerWeek();
        Call<HashMap<String,Integer>> kidsByCategory = retrofitAPI.createGetActiveKidsPerCategoryPerWeek();

        newKids.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.d("Mohammad","success");
                    initialzieChildrenChart(response.body());

                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","failed");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();
            }
        });
        newParents.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Log.d("Mohammad", ""+response.body().get("totalParents"));
                    initialzieParentsChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","failed");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();
            }
        });

        activityTime.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    initialzieActivitiesChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","6th");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();

            }
        });

        kidsByCategory.enqueue(new Callback<HashMap<String, Integer>>() {
            @Override
            public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    initialzieTotalCatChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
                Log.d("Mohammad","6th");
                Toast.makeText(AdminMainActivity.this, "----Failure, Please Do something.---", Toast.LENGTH_SHORT).show();

            }
        });


    }

}
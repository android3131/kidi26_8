package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminAddCourse extends AppCompatActivity {
    private Button mDatePickerBtn;
    private TextView mSelectedDateText;
    RecyclerView recyclerView1, recyclerView2;
    public ArrayList<RVitem3> lis1;
    public ArrayList<RVitem3> lis2;
    MyAdapter3 myAdapter31, myAdapter32;
    Button bt1;
    EditText courseName;
    int pos;
    RVitem3 selectedHourStart;
    RVitem3 selectedHourEnd;
    Object categorySelection;
    String selectedDatesTxt;
    String startDateExtracted;
    String endDateExtracted;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:7017/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();

    RetroFitAPI3 retrofitAPI3 = retrofit.create(RetroFitAPI3.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_course);

        //select range of dates for the course
        mSelectedDateText = findViewById(R.id.selected_date);
        mDatePickerBtn = findViewById(R.id.date_picker_btn);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        CalendarConstraints.Builder constrainBuilder = new CalendarConstraints.Builder();
        constrainBuilder.setValidator(DateValidatorPointForward.now());


        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT A DATE");
        builder.setCalendarConstraints(constrainBuilder.build());
        final MaterialDatePicker materialDatePicker = builder.build();


        mDatePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                mSelectedDateText.setText("Selected Date :" + materialDatePicker.getHeaderText());
                selectedDatesTxt = materialDatePicker.getHeaderText().toString();
            }
        });

        addSpinner(); //function that fill the categories in the spinner
        addItemToRVHourStart();//function that fill the start hour in the RV
        addItemToRVHourEnd(); //function that fill the end hour in the RV

        //recyclerView for the start hour
        recyclerView1 = findViewById(R.id.recycler_view1);
        recyclerView1.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView1, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                            for (int i = 0; i < lis1.size(); i++) {
                                if (i != position)
                                    lis1.get(i).setColor(0);
                            lis1.get(position).setColor(R.color.black);
                            selectedHourStart = lis1.get(position);
                            myAdapter31.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        myAdapter31 = new MyAdapter3(this, lis1);
        recyclerView1.setAdapter(myAdapter31);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));


        //recyclerView for the end hour
        recyclerView2 = findViewById(R.id.recycler_view2);
        recyclerView2.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView2, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                            for (int i = 0; i < lis2.size(); i++) {
                                if (i != position)
                                    lis2.get(i).setColor(0);
                            lis2.get(position).setColor(R.color.black);
                            selectedHourEnd = lis2.get(position);
                            myAdapter32.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        myAdapter32 = new MyAdapter3(this, lis2);
        recyclerView2.setAdapter(myAdapter32);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        //all the elements we need to post new course:
        bt1 = findViewById(R.id.update_button);
        courseName = findViewById(R.id.editTextTextPersonName);

        //post new course

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course1 course1 = new Course1(courseName.getText().toString(), 0, selectedDatesTxt ,"30/02/11", "defaultDay", selectedHourStart.getItemName() , selectedHourEnd.getItemName(), 0, new Category(categorySelection.toString(), "BBB"));

                // calling a method to create a post and passing our model class.
                Call<Course1> call = retrofitAPI3.postNewCourse(course1);
                // add the http request to queue
                call.enqueue(new Callback<Course1>() {
                    @Override
                    public void onResponse(Call<Course1> call, Response<Course1> response) {
                        // this method is called when we get response from our api.
                        //Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        Course1 responseFromAPI = response.body();
                        Toast.makeText(AdminAddCourse.this, "Course added", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Course1> call, Throwable t) {
                    }
                });
            }
        });

    }

    private void extractSelectedDates() {
        String[] arrOfStr = selectedDatesTxt.split("-", 8);
       for (String a : arrOfStr)
            //System.out.println(a);
           startDateExtracted = a ;

    }


    private void addItemToRVHourStart(){
        lis1 = new ArrayList<RVitem3>();
        lis1.add(new RVitem3("00:00", 0));
        lis1.add(new RVitem3("01:00", 0));
        lis1.add(new RVitem3("02:00", 0));
        lis1.add(new RVitem3("03:00", 0));
        lis1.add(new RVitem3("04:00", 0));
        lis1.add(new RVitem3("05:00", 0));
        lis1.add(new RVitem3("06:00", 0));
        lis1.add(new RVitem3("07:00", 0));
        lis1.add(new RVitem3("08:00", 0));
        lis1.add(new RVitem3("09:00", 0));
        lis1.add(new RVitem3("10:00", 0));
        lis1.add(new RVitem3("11:00", 0));
        lis1.add(new RVitem3("12:00", 0));
        lis1.add(new RVitem3("13:00", 0));
        lis1.add(new RVitem3("14:00", 0));
        lis1.add(new RVitem3("15:00", 0));
        lis1.add(new RVitem3("16:00", 0));
        lis1.add(new RVitem3("17:00", 0));
        lis1.add(new RVitem3("18:00", 0));
        lis1.add(new RVitem3("19:00", 0));
        lis1.add(new RVitem3("20:00", 0));
        lis1.add(new RVitem3("21:00", 0));
        lis1.add(new RVitem3("22:00", 0));
        lis1.add(new RVitem3("23:00", 0));
        lis1.add(new RVitem3("24:00", 0));
    }


    private void addItemToRVHourEnd() {
        lis2 = new ArrayList<RVitem3>();
        lis2.add(new RVitem3("00:00", 0));
        lis2.add(new RVitem3("01:00", 0));
        lis2.add(new RVitem3("02:00", 0));
        lis2.add(new RVitem3("03:00", 0));
        lis2.add(new RVitem3("04:00", 0));
        lis2.add(new RVitem3("05:00", 0));
        lis2.add(new RVitem3("06:00", 0));
        lis2.add(new RVitem3("07:00", 0));
        lis2.add(new RVitem3("08:00", 0));
        lis2.add(new RVitem3("09:00", 0));
        lis2.add(new RVitem3("10:00", 0));
        lis2.add(new RVitem3("11:00", 0));
        lis2.add(new RVitem3("12:00", 0));
        lis2.add(new RVitem3("13:00", 0));
        lis2.add(new RVitem3("14:00", 0));
        lis2.add(new RVitem3("15:00", 0));
        lis2.add(new RVitem3("16:00", 0));
        lis2.add(new RVitem3("17:00", 0));
        lis2.add(new RVitem3("18:00", 0));
        lis2.add(new RVitem3("19:00", 0));
        lis2.add(new RVitem3("20:00", 0));
        lis2.add(new RVitem3("21:00", 0));
        lis2.add(new RVitem3("22:00", 0));
        lis2.add(new RVitem3("23:00", 0));
        lis2.add(new RVitem3("24:00", 0));
    }

        //implementation of the function that add the categories to the spinner
    private void addSpinner() {
        List<String> categoryList = new ArrayList<>();
        Spinner categorySpinner = findViewById(R.id.spinner_category);
        categoryList.add("Choose Category");
        categoryList.add("Animal");
        categoryList.add("Arts");
        categoryList.add("Space");
        categoryList.add("Science");



        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ofra", "on Button click: " + categorySpinner.getSelectedItem());
                pos=i;
                categorySelection = categorySpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

}

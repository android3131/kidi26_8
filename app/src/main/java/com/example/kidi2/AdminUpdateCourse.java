package com.example.kidi2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidi2.MyAdapter3;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//change from Course1 to Course
public class AdminUpdateCourse extends AppCompatActivity {
    //buttons:
    private Button mDatePickerBtn;
    Button bt_update;

    private TextView mSelectedDateText; //textView that show the selected dates

    //hours recyclerView - start and end hours:
    RecyclerView recyclerView1, recyclerView2;
    public ArrayList<RVitem3> lis1;
    public ArrayList<RVitem3> lis2;
    MyAdapter3 myAdapter31, myAdapter32;
    int pos;

    //spinners :
    List<String> categoryList = new ArrayList<>();
    List<String> dayList = new ArrayList<>();
    Spinner daySpinner;
    List<String> categoryIds;
    Spinner categorySpinner;

    //for shared prefrences
    EditText courseName;
    Object categorySelection;
    String selectedDatesTxt;
    String daySelection;
    RVitem3 selectedHourStart;
    RVitem3 selectedHourEnd;
    TextView hours;
    EditText ZoomLink;
    EditText urlLink;

    Retrofit retrofit,retrofit2;
    RetroFitAPI3 retrofitAPI3;
    RetroFitAPI2 retrofitAPI2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_update_course);
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();


        retrofitAPI3 = retrofit.create(RetroFitAPI3.class);
        retrofitAPI2 = retrofit.create(RetroFitAPI2.class);
        //findViewById
        hours = findViewById(R.id.hours);
        daySpinner=findViewById(R.id.day_spinner1);
        mSelectedDateText = findViewById(R.id.selected_date);
        mDatePickerBtn = findViewById(R.id.date_picker_btn);
        categorySpinner =(Spinner) findViewById(R.id.spinner_category1);
        recyclerView1 = findViewById(R.id.recycler_view1);
        recyclerView2 = findViewById(R.id.recycler_view2);
        bt_update = findViewById(R.id.update_button1);
        courseName = findViewById(R.id.editTextTextPersonName1);
        ZoomLink = findViewById(R.id.zoomLinkEditText);
        urlLink = findViewById(R.id.urlLinkEditText1);



        //choose range of dates for course
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
                selectedDatesTxt = materialDatePicker.getHeaderText();

            }
        });


        addSpinner(); //function that fill the categories in the spinner
        addDaySpinner();
        addItemToRVHourStart();//function that fill the start hour in the RV
        addItemToRVHourEnd(); //function that fill the end hour in the RV


        //recyclerView for the start hour
        recyclerView1.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView1, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        hours.setText("");
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
        recyclerView2.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView2, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        hours.setText("");
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

        //all the relevant fields relevant to shared preferences initialize to null
        SharedPreferences mPrefrences = PreferenceManager.getDefaultSharedPreferences(AdminUpdateCourse.this);
        SharedPreferences.Editor editor= mPrefrences.edit();
        String s=mPrefrences.getString("courseName", "");
        String s1=mPrefrences.getString("category", "");
        String s3=mPrefrences.getString("startDate", "");
        String s4=mPrefrences.getString("endDate", "");
        String s5=mPrefrences.getString("startHour", "");
        String s6=mPrefrences.getString("endHour", "");
        String s7=mPrefrences.getString("day", "");
        String s8=mPrefrences.getString("zoomLink", "");
        String s9=mPrefrences.getString("urlLink", "");

        mSelectedDateText.setText("Selectesd Dates: "+s3+"-"+s4);//set course Dates that exist in the system
        hours.setText("Selected Hours: "+ s5 + "-" + s6); //set course hours that exist in the system

        //all the elements we need to update course:
        courseName.setText(s); //set course name that exist in the system

        //put the category from sharedRefrences(setAdminCourse) in the spinner on the update activity
        int posCat=0;
        for (int i=0;i<categoryList.size();i++) {
            if (categoryList.get(i).equals( s1)) {
                posCat = i;
                break;
            }
        }
        categorySpinner.setSelection(posCat);

        //put the day from sharedRefrences(setAdminCourse) in the spinner on the update activity
        int posDay = 0;
        for (int i=0;i<dayList.size();i++) {
            if (dayList.get(i).equals(s7)) {
                posDay = i;
                break;
            }
        }
        daySpinner.setSelection(posDay);


        ZoomLink.setText(s8); //set course zoom link that exist in the system
        urlLink.setText(s9);

        //post new course
        // extractSelectedDates();
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM,dd,yyyy hh:mm:ss");
                String []j=selectedDatesTxt.split(" ");
                String left="";
                String right="";
                if(  j.length ==  5) {
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    if(j[1].length()==1)
                        j[1]="0"+j[1];
                    left = j[0] +","+ j[1]+","+year;
                    if(j[4].length()==1)
                        j[4]="0"+j[4];
                    right = j[3] +","+ j[4]+","+year;
                }
                else if(j.length==7)
                {
                    if(j[1].length()==2)
                        j[1]="0"+j[1];
                    left = j[0]+"," + j[1]+j[2];
                    if(j[5].length()==2)
                        j[5]="0"+j[5];
                    right = j[4]+"," + j[5]+j[6];
                }
                else // at another year
                {
                    if(j[1].length()==1)
                        j[1]="0"+j[1];
                    left = j[0]+"," + j[1]+","+j[5];
                    if(j[4].length()==2)
                        j[4]="0"+j[4];
                    right= j[3]+","+j[4]+j[5];
                }

                Date strtdate_update=new Date();
                try {
                    strtdate_update=sdf.parse(left+" 00:00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date endDt_update=new Date();
                try {
                    endDt_update=sdf.parse(right+" 00:00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Course course1 = new Course(courseName.getText().toString(), 0, left ,right, daySelection.toString(), selectedHourStart.getItemName() , selectedHourEnd.getItemName(), 0, new Category(categorySelection, "BBB"),ZoomLink.getText().toString());
                //String name, Date startDateTime, Date finishDateTime, String categoryId, String zoomMeetingLink,
                //                  String day, String startHour,String urlLink, String endHour
                Course course1 = new Course(courseName.getText().toString(), strtdate_update, endDt_update, "String categoryId", ZoomLink.getText().toString(),
                        daySelection.toString(), selectedHourStart.getItemName(),urlLink.getText().toString(), selectedHourEnd.getItemName());


                // calling a method to create a post and passing our model class.
                Call<Course> call = retrofitAPI3.updateCourse(course1);
                // add the http request to queue
                call.enqueue(new Callback<Course>() {
                    @Override
                    public void onResponse(Call<Course> call, Response<Course> response) {
                        // this method is called when we get response from our api.
                        //Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        Course responseFromAPI = response.body();
                        Toast.makeText(AdminUpdateCourse.this, "Course added", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Course> call, Throwable t) {
                    }
                });
            }
        });

    }

    private void addDaySpinner() {
        dayList.add("Choose Day");
        dayList.add("Sunday");
        dayList.add("Monday");
        dayList.add("Tuesday");
        dayList.add("Wednesday");
        dayList.add("Thursday");
        dayList.add("Friday");
        dayList.add("Saturday");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dayList);
        daySpinner.setAdapter(categoryAdapter);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ofra", "on Button click: " + daySpinner.getSelectedItem());
                pos=i;
                daySelection = dayList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
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

        categorySpinner = findViewById(R.id.spinner_category1);
        categoryList = new ArrayList<>();
        categoryIds = new ArrayList<>();
        categoryList.add("Choose Category");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(AdminUpdateCourse.this, android.R.layout.simple_spinner_item, categoryList);
        categorySpinner.setAdapter(categoryAdapter);
        Call<List<Category>> call;
        call = retrofitAPI2.getallCat();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                List<Category> responseFromAPI = response.body();
                int len = responseFromAPI.size();
                for (int i = 0; i < len; i++) {
                    categoryList.add(responseFromAPI.get(i).getName());
                    categoryIds.add(responseFromAPI.get(i).getId());
                }

                // ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(AdminSetCourse.this, android.R.layout.simple_spinner_item, categoryList);

//                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        //Log.d("ofra", "on Button click: " + categorySpinner.getSelectedItem());
//                       // myAdapter.notifyDataSetChanged();
//                        Toast.makeText(AdminUpdateCourse.this, "item selected" + categoryList.get(i), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//                    }
//                });
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
            }
        });

    }

}

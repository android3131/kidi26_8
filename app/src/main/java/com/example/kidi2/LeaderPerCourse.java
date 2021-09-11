package com.example.kidi2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeaderPerCourse extends AppCompatActivity {



    List<String> categoryIds;
    Spinner categorySpinner;
    List<String> categoryList = new ArrayList<>();
    int pos1;
    Object categorySelection1;
    Button bt_delete_leader, bt_add_leader;
    RecyclerView recyclerView1;
    MyAdapterPerLeader myAdapterPerLeader;
    public ArrayList<Leader> lisLeader;
    TextView courseName, categoryName;
    TextView startHour ,endHour , startDate, EndDate;



    List<String> LeadersIds;

    Retrofit retrofit ;

    RetroFitAPI3 retrofitAPI3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_per_course);

        bt_delete_leader = findViewById(R.id.delete_button2);
        bt_add_leader = findViewById(R.id.addLeader_button2);
        recyclerView1=findViewById(R.id.recyclerView);
        courseName = findViewById(R.id.courseName1);
        categoryName = findViewById(R.id.category1);
        startHour = findViewById(R.id.HourStart);
        endHour = findViewById(R.id.HourEnd);
        startDate = findViewById(R.id.DateStart);
        EndDate = findViewById(R.id.DateEnd);

retrofit = new Retrofit.Builder()
            .baseUrl(getString(R.string.BASE_URL))
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
        retrofitAPI3 = retrofit.create(RetroFitAPI3.class);
        init("", 1);
        myAdapterPerLeader=new MyAdapterPerLeader(this,lisLeader);
        myAdapterPerLeader.notifyDataSetChanged();

        recyclerView1.setAdapter(myAdapterPerLeader);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        addSpinner(); //function that fill the categories in the spinner
        recyclerView1.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView1 ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if (position != 0) {
                            //Toast.makeText(AdminSetCourse.this, "Course " + lis.get(position).getName()+" selected", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < lisLeader.size(); i++) {
                                if (i != position) //dont include the first row of the heads
                                    lisLeader.get(i).setClr(0);
                            }
                            lisLeader.get(position).setClr(R.color.black); //mark the selected course in RV
                            myAdapterPerLeader.notifyDataSetChanged(); //update the recyclerView


                            bt_delete_leader.setEnabled(true);

                            //delete selected item from RV
                            bt_delete_leader.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Call<Boolean> call = retrofitAPI3.deleteCourse2(LeadersIds.get(position));

                                    call.enqueue(new Callback<Boolean>() {
                                        @Override
                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                            //Toast.makeText(AdminSetCourse.this,"deleted  "+position,Toast.LENGTH_SHORT).show();
                                            lisLeader.remove(position); //delete the selected course from RV
                                            myAdapterPerLeader.notifyDataSetChanged();//update the recyclerView
                                            Toast.makeText(getApplicationContext(), "course deleted successfully", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<Boolean> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "A problem occured", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                            //update selected item in RV


                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );



//        SharedPreferences mPrefrences = PreferenceManager.getDefaultSharedPreferences(LeaderPerCourse.this);
//        SharedPreferences.Editor editor= mPrefrences.edit();
//        String s=mPrefrences.getString("courseName", "");
//        String s1=mPrefrences.getString("category", "");
//        String s3=mPrefrences.getString("startDate", "");
//        String s4=mPrefrences.getString("endDate", "");
//        String s5=mPrefrences.getString("startHour", "");
//        String s6=mPrefrences.getString("endHour", "");
//
//        courseName.setText(s); //set course name that exist in the system
//       categoryName.setText(s1);
//        startHour.setText(s5);
//        endHour.setText(s6);
//        startDate.setText(s3);
//        EndDate.setText(s4);


    }
    private void addSpinner() {
//
//        categorySpinner = findViewById(R.id.categorySpinner);
//        categoryList = new ArrayList<>();
//        categoryIds = new ArrayList<>();
//        categoryList.add("Choose Category");
//        Call<List<Category>> call;
//        call=retrofitAPI3.getallCat1();
//        call.enqueue(new Callback<List<Category>>() {
//            @Override
//            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
//
//                int len=0;
//                List<Category> responseFromAPI = response.body();
//                if(responseFromAPI!=null)
//                    len=responseFromAPI.size(); //the number of categories in the spinner
//                for (int i=0;i<len;i++) {
//                    categoryList.add(responseFromAPI.get(i).getName());
//                    categoryIds.add(responseFromAPI.get(i).getId());
//                }
//
//                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(LeaderPerCourse.this, android.R.layout.simple_spinner_item, categoryList);
//                categorySpinner.setAdapter(categoryAdapter);
//                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        //Log.d("ofra", "on Button click: " + categorySpinner.getSelectedItem());
//                        pos1=i; ///keep the selected category for the put request
//                        categorySelection1 = categorySpinner.getSelectedItem();
//
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//                    }
//                });
//            }
//            @Override
//            public void onFailure(Call<List<Category>> call, Throwable t) {
//            }
//        });

    }

    public  void  init(String catg,int k)
    {
        lisLeader=new ArrayList<>();
        lisLeader.clear();


//        LeadersIds.clear();
        //courses added to check the adminUpdateCourse activity
        Leader l1= new Leader("faisal","1111eee@gmail.com");
        Leader l2= new Leader("yael","1111eee@gmail.com");
        l1.setPhoneNumber("123456798");
        l2.setPhoneNumber("13465498123");
        lisLeader.add(l1);
        lisLeader.add(l2);

//
//        //this should be deleted !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        lisLeader.add(new Course2("Course21",50,"14/5/2020","14/8/2020",
//                new Category("Space","img"),
//                "zoom.com"
//                ,"Friday","14:00","17:00",0, "urlLink.com"));

//        myAdapterPerLeader.notifyDataSetChanged();
//
//        Call<List<Leader>> call;
//        if(k==0)
//            call=retrofitAPI3.getAllCourses();
//        else
//            call=retrofitAPI3.getcoursesofcat(catg);
//        call.enqueue(new Callback<List<Leader>>() {
//            @Override
//            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
//
//                List<Course> responseFromAPI = response.body();
//                int len=responseFromAPI.size();
//                for (int i=0;i<len;i++) {
//                    lisLeader.add(new Course2(responseFromAPI.get(i).getName()
//                            , responseFromAPI.get(i).getPrice(), responseFromAPI.get(i).getStartDateTime().getDay() + "/" + responseFromAPI.get(i).getStartDateTime().getMonth() + "/" + responseFromAPI.get(i).getStartDateTime().getYear()
//                            , responseFromAPI.get(i).getFinishDateTime().getDay() + "/" + responseFromAPI.get(i).getFinishDateTime().getMonth() + "/" + responseFromAPI.get(i).getFinishDateTime().getYear()
//                            , new Category(responseFromAPI.get(i).getCategoryName(), "responseFromAPI.get(i).getCategoryImage()"), responseFromAPI.get(i).getZoomMeetingLink(),
//                            responseFromAPI.get(i).getDay(), responseFromAPI.get(i).getStartHour(), responseFromAPI.get(i).getEndHour(), 0, responseFromAPI.get(i).getUrlLink()));
//
//                    LeadersIds.add(responseFromAPI.get(i).getID());
        //               }
        //  myAdapterPerLeader.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<List<Leader>> call, Throwable t) {
//            }
//        });

    }
}
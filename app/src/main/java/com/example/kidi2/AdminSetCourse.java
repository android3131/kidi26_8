package com.example.kidi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminSetCourse extends AppCompatActivity {
    RecyclerView recyclerView;
    //Context context;
    public ArrayList<Course2> lis;
    MyAdapter myAdapter;
    Button bt_delete, bt_update, bt_add, bt_add_leader;
    BottomNavigationView bottomnav;

    List<String> categoryIds;
    List<String> categoryList;
    List<String> coursesIds;

    Retrofit retrofit;
    RetroFitAPI2 retrofitAPI2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_set_course);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();
        // create an instance for our retrofit api class.
        retrofitAPI2 = retrofit.create(RetroFitAPI2.class);
        //lists
        lis = new ArrayList<Course2>();
        addSpinner();

        //findViewById- all
        bt_delete = findViewById(R.id.delete_button);
        bt_update = findViewById(R.id.update_button);
        bt_add = findViewById(R.id.add_button);
        bt_add_leader = findViewById(R.id.add_leader_button);
        recyclerView = findViewById(R.id.recyclerView);
        bt_delete.setEnabled(false);
        bt_update.setEnabled(false);
        bt_add_leader.setEnabled(false);

        bottomnav = findViewById(R.id.nav_viewsetcourse);
        bottomnav.setSelectedItemId(R.id.course_page);

        bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.home_page:
                        startActivity(new Intent(AdminSetCourse.this, AdminMainActivity.class));
                        // getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, homeFragment).commit();
                        return true;

                    case R.id.users_page:
                        startActivity(new Intent(AdminSetCourse.this, AdminSetUser.class));
                        // getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, leadersFragment).commit();
                        return true;

                    case R.id.leaders_page:
                        startActivity(new Intent(AdminSetCourse.this, AdminSetLeader.class));
                        // getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, userFragment).commit();
                        return true;

                    case R.id.course_page:
                        startActivity(new Intent(AdminSetCourse.this, AdminSetCourse.class));
                        //  getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, coursesFragment).commit();
                        return true;

                    case R.id.more_page:

                        PopupMenu popup = new PopupMenu(AdminSetCourse.this, findViewById(R.id.more_page));
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.mymenu, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId()==R.id.logoutmenu)
                                    startActivity(new Intent(AdminSetCourse.this, FirstScreen.class));

                                return true;
                            }
                        });
                        popup.show();
                        return true;
                }
                return false;
            }
        });


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position != 0) {
                            //Toast.makeText(AdminSetCourse.this, "Course " + lis.get(position).getName()+" selected", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < lis.size(); i++) {
                                if (i != position) //dont include the first row of the heads
                                    lis.get(i).setClr(0);
                            }
                            lis.get(position).setClr(R.color.black); //mark the selected course in RV
                            myAdapter.notifyDataSetChanged(); //update the recyclerView


                            bt_delete.setEnabled(true);
                            bt_update.setEnabled(true);
                            bt_add_leader.setEnabled(true);

                            //delete selected item from RV
                            bt_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Call<Boolean> call = retrofitAPI2.deleteCourse1(coursesIds.get(position));

                                    call.enqueue(new Callback<Boolean>() {
                                        @Override
                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                            //Toast.makeText(AdminSetCourse.this,"deleted  "+position,Toast.LENGTH_SHORT).show();
                                            lis.remove(position); //delete the selected course from RV
                                            myAdapter.notifyDataSetChanged();//update the recyclerView
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
                            bt_update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences mPrefrences = PreferenceManager.getDefaultSharedPreferences(AdminSetCourse.this);
                                    SharedPreferences.Editor editor = mPrefrences.edit();
                                    editor.putString("category", lis.get(position).getCategory().getName());
                                    editor.putString("courseName", lis.get(position).getName());
                                    editor.putString("startDate", lis.get(position).getStartDateTime());
                                    editor.putString("endDate", lis.get(position).getFinishDateTime());
                                    editor.putString("day", lis.get(position).getDay());
                                    editor.putString("startHour", lis.get(position).getStartOclock());
                                    editor.putString("endHour", lis.get(position).getEndOclock());
                                    editor.putString("zoomLink", lis.get(position).getZoomMeetingLink());
                                    editor.putString("urlLink", lis.get(position).getUrlLink());


                                    editor.commit();

                                    //after put all the relevant fields in shared Preferences go to update activity
                                    Intent intent = new Intent(AdminSetCourse.this, AdminUpdateCourse.class);
                                    startActivity(intent);

                                }
                            });


                            bt_add_leader.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences mPrefrences = PreferenceManager.getDefaultSharedPreferences(AdminSetCourse.this);
                                    SharedPreferences.Editor editor = mPrefrences.edit();
                                    editor.putString("courseId", coursesIds.get(position));
                                    editor.putString("category", lis.get(position).getCategory().getName());
                                    editor.putString("courseName", lis.get(position).getName());
                                    editor.putString("startDate", lis.get(position).getStartDateTime());
                                    editor.putString("endDate", lis.get(position).getFinishDateTime());
                                    editor.putString("day", lis.get(position).getDay());
                                    editor.putString("startHour", lis.get(position).getStartOclock());
                                    editor.putString("endHour", lis.get(position).getEndOclock());
                                    editor.putString("zoomLink", lis.get(position).getZoomMeetingLink());
                                    editor.putString("urlLink", lis.get(position).getUrlLink());
                                    editor.commit();

                                    //after put all the relevant fields in shared Preferences go to update activity
                                    Intent intent = new Intent(AdminSetCourse.this, LeaderPerCourse.class);
                                    startActivity(intent);

                                }
                            });

                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        myAdapter = new MyAdapter(this, lis);
        init("", 0);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add course button selected - go to add course activity
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSetCourse.this, AdminAddCourse.class);
                startActivity(intent);

            }
        });


    }

    //spinner for filter by category the courses
    private void addSpinner() {

        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        categoryList = new ArrayList<>();
        categoryIds = new ArrayList<>();
        coursesIds = new ArrayList<>();
        categoryList.add("Choose Category");
        categoryIds.add("");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(AdminSetCourse.this, android.R.layout.simple_spinner_item, categoryList);
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
                categorySpinner.setAdapter(categoryAdapter);
                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //Log.d("ofra", "on Button click: " + categorySpinner.getSelectedItem());
                        init(categoryIds.get(i), i);
                        myAdapter.notifyDataSetChanged();
                        Toast.makeText(AdminSetCourse.this, "item selected" + categoryList.get(i), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
            }
        });
        /*
        categoryList.add("Choose Category");
        categoryList.add("space");
        categoryList.add("animals");
        categoryList.add("arts");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.d("ofra", "on Button click: " + categorySpinner.getSelectedItem());
                init(categoryList.get(i));
                myAdapter.notifyDataSetChanged();
                Toast.makeText(AdminSetCourse.this, "item selected" + categoryList.get(i), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
         */
    }


    public void init(String catg, int k) {
        lis.clear();


        coursesIds.clear();
        //courses added to check the adminUpdateCourse activity
        lis.add(new Course2("Course", 50, "Start", "End",
                new Category("Category", "img"),
                "zoom.com"
                , "Day", "From", "To", 0, "urlLink.com"));
        coursesIds.add("");

        //this should be deleted !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


        myAdapter.notifyDataSetChanged();


        Call<List<Course>> call;
        if (k == 0)
            call = retrofitAPI2.getAllCourses();
        else
            call = retrofitAPI2.getcoursesofcat(catg);
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {

                List<Course> responseFromAPI = response.body();
                int len = 0;
                if (responseFromAPI != null)
                    len = responseFromAPI.size();
                String catego=categoryList.get(k);
                if(k==0)
                    catego="";
                for (int i = 0; i < len; i++) {
                    lis.add(new Course2(responseFromAPI.get(i).getName()
                            , responseFromAPI.get(i).getPrice(), responseFromAPI.get(i).getStartDateTime().getDay() + "/" + responseFromAPI.get(i).getStartDateTime().getMonth() + "/" + responseFromAPI.get(i).getStartDateTime().getYear()
                            , responseFromAPI.get(i).getFinishDateTime().getDay() + "/" + responseFromAPI.get(i).getFinishDateTime().getMonth() + "/" + responseFromAPI.get(i).getFinishDateTime().getYear()
                            , new Category( responseFromAPI.get(i).getCategoryName(), ""), responseFromAPI.get(i).getZoomMeetingLink(),
                            responseFromAPI.get(i).getDay(), responseFromAPI.get(i).getStartHour(), responseFromAPI.get(i).getEndHour(), 0, responseFromAPI.get(i).getUrlLink()));

                    coursesIds.add(responseFromAPI.get(i).getID());
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
            }
        });
    }
}
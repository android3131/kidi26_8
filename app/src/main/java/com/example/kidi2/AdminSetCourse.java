package com.example.myapplication19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminSetCourse extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    public ArrayList<Course> lis;
    MyAdapter myAdapter;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8099/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
    // create an instance for our retrofit api class.
    Button bt_delete,bt_update,bt_add;
    RetroFitAPI2 retrofitAPI2 = retrofit.create(RetroFitAPI2.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_set_course);
        addSpinner();
        lis=new ArrayList<Course>();
        bt_delete=findViewById(R.id.delete_button);
        bt_update=findViewById(R.id.update_button);
        bt_add=findViewById(R.id.add_button);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                       if(position!=0) {
                           Toast.makeText(AdminSetCourse.this, "Course " + lis.get(position).getName()+" selected", Toast.LENGTH_SHORT).show();
                            for (int i=0;i<lis.size();i++) {
                                if(i!=position)
                                    lis.get(i).setClr(0);
                            }
                           lis.get(position).setClr(R.color.black);
                            myAdapter.notifyDataSetChanged();
                            bt_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Call<List<Course>> call= retrofitAPI2.deleteCourse(lis.get(position).getName());

                                    call.enqueue(new Callback<List<Course>>() {
                                        @Override
                                        public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                                            Toast.makeText(AdminSetCourse.this,"deleted  "+position,Toast.LENGTH_SHORT).show();
                                            lis.remove(position);
                                            myAdapter.notifyDataSetChanged();
                                        }
                                        @Override
                                        public void onFailure(Call<List<Course>> call, Throwable t) {
                                        }
                                    });
                                }
                            });
                       }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        myAdapter = new MyAdapter(this,lis);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminSetCourse.this,thirdScreen.class);
                startActivity(intent);

            }
        });

    }
    private void addSpinner() {
        List<String> categoryList = new ArrayList<>();
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
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
    }
    public  void  init(String catg)
    {
        lis.clear();
        lis.add(new Course("Course",50,"Start","End",
                new Category("Category","img"),
                "zoom.com"
                ,"Day","From","To",0));
        Call<List<Course>> call;
        if(catg=="space")
             call = retrofitAPI2.getSpacesCourses();
        else if(catg=="animals")
             call = retrofitAPI2.getAnimalsCourses();
             else if(catg=="arts")
                 call = retrofitAPI2.getArtsCourses();
                    else
                        call = retrofitAPI2.getAllCourses();
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {

                List<Course> responseFromAPI = response.body();
                int len=responseFromAPI.size();
                for (int i=0;i<len;i++)
                    lis.add(new Course(responseFromAPI.get(i).getName(),responseFromAPI.get(i).getPrice(),responseFromAPI.get(i).getStartDateTime(),responseFromAPI.get(i).getFinishDateTime()
                            ,new Category(responseFromAPI.get(i).getCategory().getName(),responseFromAPI.get(i).getCategory().getImage()),responseFromAPI.get(i).getZoomMeetingLink(),
                            responseFromAPI.get(i).getDay(),responseFromAPI.get(i).getStartOclock(),responseFromAPI.get(i).getEndOclock(),responseFromAPI.get(i).getClr()));
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
            }
        });
    }
}
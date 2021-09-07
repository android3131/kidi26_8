package com.example.kidi2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Addactivity extends AppCompatActivity {

    private Button addActivityBtn;
    private Spinner spinner;
    private ArrayList<Course_Addactivity> childsCourses = new ArrayList<Course_Addactivity>();

    //public Spinner timesSpinner;

    //private ArrayList<String> stringList = new ArrayList<String>();
    private HashMap<Course_Addactivity, String> courseList = new HashMap<Course_Addactivity, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maingroup6);

        //spinner = (Spinner) findViewById(R.id.spinner);
        addActivityBtn = findViewById(R.id.addBtn);
        spinner = (Spinner) findViewById(R.id.spinner);
        List <String> strings = new ArrayList<String>();
        getData(1, 1);
        addActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Addactivity.this, HomeLogin.class));
            }
        });



    }


    private void addSpinner() {
        int i = 0;
        ArrayList<StateVO> listVOs = new ArrayList<>();
        List<String> list = new ArrayList<String>(courseList.values());
        for (Course_Addactivity c: courseList.keySet()) {
            System.out.println(childsCourses);
            System.out.println(c.getCourseName());
            StateVO stateVO = new StateVO();
            stateVO.setTitle(courseList.get(c));
            //MySingletone.getInstance().setDataAtPosition(i, c);
            if (childsCourses.contains(c)) {
                System.out.println("YEs");
                stateVO.setSelected(true);
            }
            else {
                System.out.println("NO");
                stateVO.setSelected(false);
            }
            listVOs.add(stateVO);
            i++;
        }
        //----------- setting the data adapter --------

        MyAdapter4 myAdapter = new MyAdapter4(Addactivity.this, 0,
                listVOs);
        spinner.setAdapter(myAdapter);

        // add Spinner item selection Listener
        spinner.setOnItemSelectedListener(new KidiOnItemSelectedListener());
    }

    private void getData(int categoryId, int childId) {
        // create retrofit builder and pass our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();
        // create an instance for our retrofit api class.
        RetrofitAPI6 retrofitAPI = retrofit.create(RetrofitAPI6.class);

        Call<List<Course_Addactivity>> callCoursesByCat = retrofitAPI.getCourseOfCategory(categoryId);
        Call<List<Course_Addactivity>> callCoursesByChild = retrofitAPI.getCoursesOfChild(childId);

        // add the http request to queue
        callCoursesByChild.enqueue(new Callback<List<Course_Addactivity>>() {
            @Override
            public void onResponse(Call<List<Course_Addactivity>> call, Response<List<Course_Addactivity>> response) {
                // getting response from our body
                // and passing it to our model class
                List<Course_Addactivity> lstResponseFromAPI = response.body();
                System.out.println(lstResponseFromAPI.get(0).getCourseName());
                childsCourses.addAll(lstResponseFromAPI);

            }

            @Override
            public void onFailure(Call<List<Course_Addactivity>> call, Throwable t) {
            }
        });

        // add the http request to queue
        callCoursesByCat.enqueue(new Callback<List<Course_Addactivity>>() {
            @Override
            public void onResponse(Call<List<Course_Addactivity>> call, Response<List<Course_Addactivity>> response) {

                // getting response from our body
                // and passing it to our model class
                List<Course_Addactivity> lstResponseFromAPI = response.body();

                for (Course_Addactivity c : lstResponseFromAPI) {
                    //courseList.add(c,c.getCourseName() + System.getProperty("line.separator") + c.getDay() + ", " + c.getStartTime() + "+" + c.getDuration());
                    courseList.put(c,c.getCourseName() + System.getProperty("line.separator") + c.getDay() + ", " + c.getStartTime() + "+" + c.getDuration());
                }
                addSpinner();
            }

            @Override
            public void onFailure(Call<List<Course_Addactivity>> call, Throwable t) {
                int x = 5;
                System.out.println("da");
            }
        });


}

    public void PressedBox(View view) {
        CheckedTextView boy_box = findViewById(R.id.checkBox3);
        CheckedTextView girl_box = findViewById(R.id.checkBox);
        CheckedTextView box = findViewById(view.getId());
            if (box.isChecked()) {
                box.setChecked(false);
            } else if (!box.isChecked()){
                box.setChecked(true);
            }
            if(boy_box.isChecked() && girl_box.isChecked()){
                if(view.getId()==boy_box.getId()){
                    girl_box.setChecked(false);
                }
                if(view.getId()==girl_box.getId()){
                    boy_box.setChecked(false);
                }
             }

//        else if(box.getId()== girl_box.getId()){
//            if(box.isChecked() && !girl_box.isChecked()){
//                box.setChecked(false);
//            }
//            else{
//                girl_box.setChecked(true);
//                box.setChecked(false);
//            }
//        }

    }
}

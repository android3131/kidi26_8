package com.example.kidi2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Addactivity extends AppCompatActivity {

    //    private Button addActivityBtn;
//    private Spinner spinner;
//    private ArrayList<Course_Addactivity> childsCourses = new ArrayList<Course_Addactivity>();
//
//
//    //private ArrayList<String> stringList = new ArrayList<String>();
//    private HashMap<Course_Addactivity, String> courseList = new HashMap<Course_Addactivity, String>();
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
    private Button addBtn;
    private ImageButton backBtn;
    public ArrayList<AlgorithmItem_grp6> algorithmItems;
    public HashMap<AlgorithmItem_grp6, Course> algoMap = new HashMap<AlgorithmItem_grp6, Course>();
    private AlgorithmAdapter_grp6 adapter;
    RvAdapter_grp6 adpt;
    SharedPreferences s;
    Course chosenCourse = new Course();


    ViewPager2 imageContainer;
    SliderAdapter adapter2;
    int list[];
    TextView[] dots;
    LinearLayout layout;
    RecyclerView rv;
    private RecyclerView my_recycler;
    private MyAdapter2 m_adapter;
    //private RecyclerView.LayoutManager m_layout;
    static int index = -1;
    static ArrayList<KidCheckBox> Kid_list = new ArrayList<KidCheckBox>();
    Kid curr_kid;
    Retrofit retrofit ;

    RetrofitAPI6 retrofitAPI ;

    String space_id="612a326989674a4e38a688a0";
    String art_id="612a326a89674a4e38a688a1";
    String animal_id="612a326a89674a4e38a688a2";
    String siecnce_id="612a326a89674a4e38a688a3";
    String music_id="61373a2bc1866b7771fe78da";
    String parentId;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maingroup6);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();
        //String.valueOf(R.string.BASE_URL)
        // create an instance for our retrofit api class.
        retrofitAPI = retrofit.create(RetrofitAPI6.class);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        parentId = pref.getString("parentID", null);

        getKidsData();

        addBtn = findViewById(R.id.addActivityBtn);
        backBtn = findViewById(R.id.backBtn);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Boolean> call = retrofitAPI.updateKidsCourses(parentId, curr_kid.getId(), chosenCourse.getID());
                //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Boolean responseKid = response.body();
                        if (responseKid == null) {
                            Toast.makeText(com.example.kidi2.Addactivity.this,   "Add failed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(com.example.kidi2.Addactivity.this,   "Course was added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(com.example.kidi2.Addactivity.this,   "Add failed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });



            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //ViewPager v = findViewById(R.id.vie);
        //v.setAdapter(new CustomPagerAdapter(this));//,algorithmItems));

        // we pass our item list and context to our Adapter.
        rv=findViewById(R.id.rv_1);
        imageContainer = findViewById(R.id.image_container);
        layout = findViewById(R.id.dots_container);
        algorithmItems = new ArrayList<AlgorithmItem_grp6>();
        //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
        dots = new TextView[5];
        //spinner = findViewById(R.id.spinner);
        list = new int[5];
        list[0] = R.drawable.drftspace;
        list[1] = R.drawable.anml1;  //getResources().getColor(R.color.red);
        list[2] = R.drawable.art;
        list[3] = R.drawable.scs;
        list[4] = R.drawable.musics;

        adapter2 = new SliderAdapter(list);
        imageContainer.setAdapter(adapter2);

        setIndicators();

        sliderChange();

        adpt=new RvAdapter_grp6(this,algorithmItems);
        rv.setAdapter(adpt);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //Toast.makeText(com.example.kidi2.Addactivity.this,   " selected", Toast.LENGTH_SHORT).show();
                        for (int i=0;i<algorithmItems.size();i++) {
                            if(position!=i) {
                                algorithmItems.get(i).setCheck(false);
                            }
                        }
                        algorithmItems.get(position).setCheck(true);
                        chosenCourse = algoMap.get(algorithmItems.get(position));
                        adpt.setItems(algorithmItems);
                        adpt.notifyDataSetChanged();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    void ClickedKid()   {
        curr_kid = Kid_list.get(index).getKid();

        adapter2.notifyDataSetChanged();
        imageContainer.setCurrentItem(1, true);
        imageContainer.setCurrentItem(0, true);
    }

    void sliderChange(){
        imageContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectedDots(position);
                super.onPageSelected(position);
                switch (position)
                {
                    case 0:initList(space_id);
                        break;
                    case 1:initList(animal_id);
                        break;
                    case 2:initList(art_id);
                        break;
                    case 3:initList(siecnce_id);
                        break;
                    case 4:initList(music_id);
                        break;
                    default:
                        // code block
                }
            }
        });
    }

    // It is used to set the algorithm names to our array list.

    public void initList(String catId) {
        adpt.notifyDataSetChanged();

        algorithmItems.clear();
        if (curr_kid != null) {

            Call<List<Course>> call = retrofitAPI.getCourseByCatNewAct(parentId, curr_kid.getId(), catId);
            //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
            call.enqueue(new Callback<List<Course>>() {
                @Override
                public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                    List<Course> responseFromAPI = response.body();

                    algorithmItems.clear();

                    int len = responseFromAPI.size();

                    for (int i = 0; i < len; i++) {
                        String pattern = "HH:mm:ss";
                        DateFormat df = new SimpleDateFormat(pattern);
                        String todayAsString = df.format(responseFromAPI.get(i).getStartDateTime().getTime());
                        AlgorithmItem_grp6 item = new AlgorithmItem_grp6(responseFromAPI.get(i).getName(), responseFromAPI.get(i).getDay() + " " + todayAsString + " + " + responseFromAPI.get(i).getMeetingDuration(), false);
                        algorithmItems.add(item);

                        algoMap.put(item, responseFromAPI.get(i));
                        adpt.setItems(algorithmItems);
                        adpt.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Course>> call, Throwable t) {
                }
            });
        }
    }

    private void selectedDots ( int position){
        for (int i = 0; i < dots.length; i++) {
            if (i == position) {
                dots[i].setTextColor(list[position]);
            } else {
                dots[i].setTextColor(getResources().getColor(R.color.black));
                //changed from gray
            }
        }
    }

    private void setIndicators () {
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(18);
            layout.addView(dots[i]);
        }

    }

    private void getKidsData() {
        Kid_list.clear();
        Call<List<Kid>> getAllParentsChildren = retrofitAPI.getParentsChildren(parentId);
        getAllParentsChildren.enqueue(new Callback<List<Kid>>() {
            @Override
            public void onResponse(Call<List<Kid>> getAllParentsChildren, Response<List<Kid>> response) {
                List<Kid>  kids = response.body();
                for(int i=0;i<kids.size();i++){
                    if (kids.get(i) != null) {
                        Kid_list.add(new KidCheckBox(kids.get(i), false));
                    }
                }
                my_recycler = findViewById(R.id.RecyclerView);
                my_recycler.setHasFixedSize(true);
                m_adapter = new MyAdapter2(Addactivity.this,Kid_list);
                my_recycler.setAdapter(m_adapter);
            }

            @Override
            public void onFailure(Call<List<Kid>> getAllParentsChildren, Throwable t) {
            }
        });

    }

    static public void getPosition(int pos){
        index=pos;
    }
}




package com.example.kidi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Streaming;


public class HomeLogin extends AppCompatActivity {
    ViewPager2 viewPager, viewPager2;
    private static final int limit_of_activities = 5;
    private BottomNavigationView navigationView;
    private ImageButton activityBtn, addBtn;
    private TextView viewActive, viewCompleted;
    private ArrayList<ViewPagerItem> viewPagerItemArrayList, viewPagerItemArrayListCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_login);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String parentId;

        parentId = pref.getString("ParentID", null); // getting Integer

        navigationView = findViewById(R.id.navibarhomelogin);
        activityBtn = findViewById(R.id.activityButtonHomeID);
        addBtn = findViewById(R.id.addButtonHomeID);
        viewActive = findViewById(R.id.viewAllActiveHomeID);
        viewCompleted = findViewById(R.id.viewAllCompletedHomeID);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        activityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("meetingState", "all");
                editor.commit();
                startActivity(new Intent(HomeLogin.this, Activity.class));

            }
        });

        viewActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("meetingState", "active");
                editor.commit();
                startActivity(new Intent(HomeLogin.this, Activity.class));
            }
        });
        viewCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("meetingState", "completed");
                editor.commit();
                startActivity(new Intent(HomeLogin.this, Activity.class));
            }
        });


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationHomeMenuId:

                        return true;
                    case R.id.bottomNavigationUserMenuId:
                        startActivity(new Intent(HomeLogin.this, ParentProfileActivity.class));
                        return true;
                    case R.id.bottomNavigationActivityMenuId:
                        startActivity(new Intent(HomeLogin.this, ForthParentReg.class));
                        return true;
                    case R.id.bottomNavigationNotificatonsMenuId:
                        startActivity(new Intent(HomeLogin.this, KidName.class));
                        return true;
                    case R.id.bottomNavigationMoreMenuId:
                        PopupMenu popup = new PopupMenu(HomeLogin.this, findViewById(R.id.bottomNavigationMoreMenuId));
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.mymenu, popup.getMenu());
                        popup.show();
                        return true;
                }
                return false;
            }
        });


        int[] images = {R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl};
        int[] imagesCompleted = {R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl};


        String[] names = {"elie", "wafik", "jana", "fofo", "ahmed"};
        String[] namesCompleted = {"elie2", "wafik2", "jana2", "fofo2", "ahmed2"};

        Date[] dates = {
                new Date(), new Date(), new Date(), new Date(), new Date()

        };
        Date[] datesCompleted = {
                new Date(), new Date(), new Date(), new Date(), new Date()

        };


        String[] describtion = {"math", "art", "sport", "space", "physics"};
        String[] describtionCompleted = {"math2", "art2", "sport2", "space2", "physics2"};
        String[] kidsID=new String[images.length];
        String[] kidsIDCompleted=new String[images.length];
        //call backend
        viewPager = findViewById(R.id.viewpager);
        viewPager2 = findViewById(R.id.viewpager2);
        viewPagerItemArrayList = new ArrayList<>();
        viewPagerItemArrayListCompleted = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {

            System.out.println("onfailure1" + i);
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(date);
            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i], "date", names[i], "see profile", describtion[i]);
            viewPagerItemArrayList.add(viewPagerItem);

            ViewPagerItem viewPagerItemCompleted = new ViewPagerItem(imagesCompleted[i], "date2", namesCompleted[i], "see profile", describtionCompleted[i]);
            viewPagerItemArrayListCompleted.add(viewPagerItemCompleted);


        }

        VPAdapter vpAdapter = new VPAdapter(viewPagerItemArrayList);
        VPAdapter2 vpAdapter2 = new VPAdapter2(viewPagerItemArrayListCompleted);
        vpAdapter.setCtx(this);
        vpAdapter2.setCtx(this);
        vpAdapter.setFm(getSupportFragmentManager());
        viewPager.setAdapter(vpAdapter);
        viewPager2.setAdapter(vpAdapter2);

        viewPager.setClipToPadding(false);

        viewPager.setClipChildren(false);

        //   viewPager.setOffscreenPageLimit(2);

        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);


        viewPager2.setClipToPadding(false);

        viewPager2.setClipChildren(false);


        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setOffscreenPageLimit(3);
        SliderTransformer sliderTransformer = new SliderTransformer(viewPager, viewPager.getCurrentItem());

        viewPager.setPageTransformer(sliderTransformer);

        viewPager2.setOffscreenPageLimit(3);
        SliderTransformer sliderTransformer2 = new SliderTransformer(viewPager2, viewPager2.getCurrentItem());

        viewPager2.setPageTransformer(sliderTransformer2);


        //////////////////////////////////////
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("" + (position + 1))
        ).attach();

        TabLayout tabLayout2 = findViewById(R.id.tab_layout2);
        new TabLayoutMediator(tabLayout2, viewPager2,
                (tab, position) -> tab.setText("" + (position + 1))
        ).attach();


///////////////////////////////////////////////////



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL)
                )
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();

        // create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        //DataModel user = new DataModel();

        Call<HashMap<List<Kid>,List<Meeting>>> call = retrofitAPI.getAllKidsNextCoursesSorted(parentId);

        try {
            Response<HashMap<List<Kid>,List<Meeting>>> response = call.execute();
            HashMap<List<Kid>,List<Meeting>> meetingsHashMap = response.body();

            List<Kid> kid=new ArrayList<Kid>();
            List<Meeting> meeting=new ArrayList<Meeting>();
            for (List<Kid> i : meetingsHashMap.keySet()) {
                kid.addAll(i);
            }
            for (List<Meeting> j : meetingsHashMap.values()) {
                meeting.addAll(j);
            }

            for (int i = 0; i < images.length; i++) {
                kidsID[i]=kid.get(i).getId();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                dates[i] = meeting.get(i).getMeetingDateTime();
                String strDate = dateFormat.format(dates[i]);
                describtion[i] = meeting.get(i).getId();
                names[i] = kid.get(i).getFullName();
                Resources r = getResources();
                int drawableId = r.getIdentifier(kid.get(i).getImage(), "drawable",
                        "com.mypackage.myapp");

                try {
                    Class res = R.drawable.class;
                    Field field = res.getField("drawableName");
                    drawableId = field.getInt(null);
                } catch (Exception e) {
                    Log.e("MyTag", "Failure to get drawable id.", e);
                }
                images[i] = drawableId;

                viewPagerItemArrayList.get(i).setName(names[i]);
                viewPagerItemArrayList.get(i).setDescription(describtion[i]);
                viewPagerItemArrayList.get(i).setImageID(images[i]);
                viewPagerItemArrayList.get(i).setDate(strDate);


            }
            viewPager.getAdapter().notifyDataSetChanged();
        } catch (Exception ex) {

        }
        vpAdapter.setKidsID(kidsID,viewPager.getCurrentItem());


        //end call

        Call<HashMap<List<Kid>,List<Meeting>>> callCompleted = retrofitAPI.getAllKidsFinishedCoursesSorted(parentId);

        try {
            Response<HashMap<List<Kid>,List<Meeting>>> response = callCompleted.execute();
            HashMap<List<Kid>,List<Meeting>> meetingsHashMap = response.body();

            List<Kid> kid=new ArrayList<Kid>();
            List<Meeting> meeting=new ArrayList<Meeting>();
            for (List<Kid> i : meetingsHashMap.keySet()) {
                kid.addAll(i);
            }
            for (List<Meeting> j : meetingsHashMap.values()) {
                meeting.addAll(j);
            }


            for (int i = 0; i < imagesCompleted.length; i++) {
                kidsIDCompleted[i]=kid.get(i).getId();

                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                datesCompleted[i] = meeting.get(i).getMeetingDateTime();
                String strDate = dateFormat.format(datesCompleted[i]);
                describtionCompleted[i] = meeting.get(i).getId();
                namesCompleted[i] = kid.get(i).getFullName();
                Resources r = getResources();
                int drawableId = r.getIdentifier(kid.get(i).getImage(), "drawable",
                        "com.mypackage.myapp");

                try {
                    Class res = R.drawable.class;
                    Field field = res.getField("drawableName");
                    drawableId = field.getInt(null);
                } catch (Exception e) {
                    Log.e("MyTag", "Failure to get drawable id.", e);
                }
                imagesCompleted[i] = drawableId;

                viewPagerItemArrayListCompleted.get(i).setName(namesCompleted[i]);
                viewPagerItemArrayListCompleted.get(i).setDescription(describtionCompleted[i]);
                viewPagerItemArrayListCompleted.get(i).setImageID(imagesCompleted[i]);
                viewPagerItemArrayListCompleted.get(i).setDate(strDate);


            }
            viewPager2.getAdapter().notifyDataSetChanged();
        } catch (Exception ex) {

        }


        vpAdapter2.setKidsID(kidsID,viewPager2.getCurrentItem());


    }}

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    private ImageButton activityBtn, addBtn,parentImage;
    private TextView viewActive, viewCompleted, screenTitle, funwehad, funweplan;
    private ArrayList<ViewPagerItem> viewPagerItemArrayList, viewPagerItemArrayListCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_login);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String parentId;
        String userType;
        userType = pref.getString("userType", "parent");
        //userType="leader";
        parentId = pref.getString("parentID", null);
        screenTitle = findViewById(R.id.kidiT);
        funweplan = findViewById(R.id.funT);
        funwehad = findViewById(R.id.funwehadText);
        navigationView = findViewById(R.id.navibarhomelogin);
        activityBtn = findViewById(R.id.activityButtonHomeID);
        addBtn = findViewById(R.id.addButtonHomeID);
        viewActive = findViewById(R.id.viewAllActiveHomeID);
        viewCompleted = findViewById(R.id.viewAllCompletedHomeID);
        parentImage=findViewById(R.id.userImage);
        parentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeLogin.this, ParentProfileActivity.class));

            }
        });
        if (userType.equals("leader")) {
            screenTitle.setText("Leader");
            activityBtn.setVisibility(View.INVISIBLE);
            addBtn.setVisibility(View.INVISIBLE);
            funwehad.setText("Completed Courses");
            funweplan.setText("Active Courses");
            Menu nav_Menu = navigationView.getMenu();
            MenuItem target = nav_Menu.findItem(R.id.bottomNavigationUserMenuId);
            target.setVisible(false);
        }else {
            screenTitle.setText("KIDI");
            funwehad.setText("Fun we had");
            funweplan.setText("Fun we plan");
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeLogin.this, Addactivity.class));


            }
        });
        activityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeLogin.this, Addactivity.class));


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

        navigationView.setSelectedItemId(R.id.bottomNavigationHomeMenuId);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationHomeMenuId:
                        startActivity(new Intent(HomeLogin.this, HomeLogin.class));
                        return true;
                    case R.id.bottomNavigationUserMenuId:
                        startActivity(new Intent(HomeLogin.this, ParentProfileActivity.class));
                        return true;
                    case R.id.bottomNavigationActivityMenuId:
                        startActivity(new Intent(HomeLogin.this, ChatLeader.class));
                        return true;
                    case R.id.bottomNavigationNotificatonsMenuId:
                        startActivity(new Intent(HomeLogin.this, Notifications.class));
                        return true;
                    case R.id.bottomNavigationMoreMenuId:

                        PopupMenu popup = new PopupMenu(HomeLogin.this, findViewById(R.id.bottomNavigationMoreMenuId));
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.mymenu, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId()==R.id.logoutmenu)
                                    startActivity(new Intent(HomeLogin.this, FirstScreen.class));

                                return true;
                            }
                        });
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
        String[] kidsID = new String[images.length];
        String[] kidsIDCompleted = new String[images.length];
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


        parentId = pref.getString("parentID",null); //getString("parentID",null);
        // create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        //DataModel user = new DataModel();
//        LogInInfo logInInfo=new LogInInfo(pref.getString("username",null),
//                pref.getString("password",null));

        Call<HashMap<List<Kid>, List<Meeting>>> call2 = retrofitAPI.getAllKidsNextCoursesSorted
                (parentId);
        // try {
        //  Response<HashMap<List<Kid>, List<Meeting>>> response2 = call2.execute();
        call2.enqueue(new Callback<HashMap<List<Kid>, List<Meeting>>>() {
            @Override
            public void onResponse(Call<HashMap<List<Kid>, List<Meeting>>> call2, Response<HashMap<List<Kid>, List<Meeting>>> response2) {
                //  try {

                //Response<HashMap<List<Kid>, List<Meeting>>> response2 = call2.execute();


                HashMap<List<Kid>, List<Meeting>> meetingsHashMap = response2.body();
                //meetingsHashMap=new HashMap<>(response2.body());

                if (response2.body() != null) {
                    System.out.println("inside if");
                    List<Kid> kid = new ArrayList<Kid>();
                    List<Meeting> meeting = new ArrayList<Meeting>();
                    for (List<Kid> i : meetingsHashMap.keySet()) {
                        kid.addAll(i);
                    }
                    for (List<Meeting> j : meetingsHashMap.values()) {
                        meeting.addAll(j);
                    }

                    for (int i = 0; i < images.length && i < kid.size(); i++) {
                        kidsID[i] = kid.get(i).getId();
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
                }
//                                     } catch (Exception e) {
//                                         Log.e("MYAPP", "exception", e);
            }

            public void onFailure(Call<HashMap<List<Kid>, List<Meeting>>> call2, Throwable t) {
                Log.e("tag", "msg", t);
            }
        });
//        } catch (Exception e) {
//
//        }


        vpAdapter.setKidsID(kidsID, viewPager.getCurrentItem());


//        Call<HashMap<String, List<?>>> callCompleted = retrofitAPI.getAllKidsFinishedCoursesSorted
//                ("61373a2ac1866b7771fe78d7");
//
//        //try {
//        callCompleted.enqueue(new Callback<HashMap<String, List<?>>>() {
//            @Override
//            public void onResponse(Call<HashMap<String, List<?>>> callCompleted, Response<HashMap<String, List<?>>> response2) {
//                //  try {
//                // Response<HashMap<String, List>> response2 = callCompleted.execute();
//                HashMap<String, List<?>> meetingsHashMap = response2.body();
//
//                List<Kid> kid = new ArrayList<Kid>();
//                List<Meeting> meeting = new ArrayList<Meeting>();
//
//                for (Map.Entry<String, List<?>> entry : meetingsHashMap.entrySet()) {
//                    String elie = entry.getKey();
//                    if (elie.equals("elie1")) {
//                        List<?> value = entry.getValue();
//                        for (Object j : value) {
//                            kid.add((Kid)j);
//                        }
//                    }
//
//                    if (elie.equals("elie2")) {
//                        List<?> value = entry.getValue();
//                        for(Object j: value){
//                            meeting.add((Meeting) j);
//                        }
//                    }
//
//                }
//
//                for (int i = 0; i < imagesCompleted.length && i < kid.size(); i++) {
//                    kidsIDCompleted[i] = kid.get(i).getId();
//
//                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//                    datesCompleted[i] = meeting.get(i).getMeetingDateTime();
//                    String strDate = dateFormat.format(datesCompleted[i]);
//                    describtionCompleted[i] = meeting.get(i).getId();
//                    namesCompleted[i] = kid.get(i).getFullName();
//                    Resources r = getResources();
//                    int drawableId = r.getIdentifier(kid.get(i).getImage(), "drawable",
//                            "com.mypackage.myapp");
//
//                    try {
//                        Class res = R.drawable.class;
//                        Field field = res.getField("drawableName");
//                        drawableId = field.getInt(null);
//                    } catch (Exception e) {
//                        Log.e("MyTag", "Failure to get drawable id.", e);
//                    }
//                    imagesCompleted[i] = drawableId;
//
//                    viewPagerItemArrayListCompleted.get(i).setName(namesCompleted[i]);
//                    viewPagerItemArrayListCompleted.get(i).setDescription(describtionCompleted[i]);
//                    viewPagerItemArrayListCompleted.get(i).setImageID(imagesCompleted[i]);
//                    viewPagerItemArrayListCompleted.get(i).setDate(strDate);
//
//
//                }
//                viewPager2.getAdapter().notifyDataSetChanged();
//            }
//
//            public void onFailure(Call<HashMap<String, List<?>>> callCompleted, Throwable t) {
//                Log.e("funwehad", "msg", t);
//            }
//        });

        vpAdapter2.setKidsID(kidsID, viewPager2.getCurrentItem());


    }
}

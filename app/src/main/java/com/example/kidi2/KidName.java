package com.example.kidi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

public class KidName extends AppCompatActivity {
    private ViewPager2 viewPager, viewPager2;
    private ImageButton returnB, kidProfilePic;
    private ArrayList<ViewPagerItem> viewPagerItemArrayList, viewPagerItemArrayListCompleted;
    private BottomNavigationView navigationView;
    private TextView viewActive, viewCompleted, numberofactive, numberofcompleted, kidName, kidName2;
    private String kidid;
    private Button addActivityBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kid_name);
        /////////////////////////

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        navigationView = findViewById(R.id.navibarKidName);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationHomeMenuId:
                        startActivity(new Intent(KidName.this, HomeLogin.class));
                        return true;
                    case R.id.bottomNavigationUserMenuId:
                        startActivity(new Intent(KidName.this, ParentProfileActivity.class));
                        return true;
                    case R.id.bottomNavigationActivityMenuId:
                        startActivity(new Intent(KidName.this, ChatLeader.class));
                        return true;
                    case R.id.bottomNavigationNotificatonsMenuId:
                        startActivity(new Intent(KidName.this, Notifications.class));
                        return true;
                    case R.id.bottomNavigationMoreMenuId:

                        PopupMenu popup = new PopupMenu(KidName.this, findViewById(R.id.bottomNavigationMoreMenuId));
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.mymenu, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId()==R.id.logoutmenu)
                                    startActivity(new Intent(KidName.this, FirstScreen.class));

                                return true;
                            }
                        });
                        popup.show();
                        return true;
                }
                return false;
            }
        });
        numberofactive = findViewById(R.id.numberofactive);
        numberofcompleted = findViewById(R.id.numberofcompleted);
        kidProfilePic = findViewById(R.id.kidImageID);
        viewActive = findViewById(R.id.viewAllActiveKidID);
        viewCompleted = findViewById(R.id.viewAllCompletedKidID);
        addActivityBtn = findViewById(R.id.addActivityBtn);
        kidName = findViewById(R.id.kidNameID);
        kidName2 = findViewById(R.id.kidnameIDText);
        addActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KidName.this, Addactivity.class));
            }
        });
        kidid = pref.getString("kidSeeProfile", null);
        viewActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("meetingState", "active");
                editor.commit();
                startActivity(new Intent(KidName.this, Activity.class));
            }
        });
        viewCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("meetingState", "completed");
                editor.commit();
                startActivity(new Intent(KidName.this, Activity.class));
            }
        });


        /////////////////////////////
        viewPager = findViewById(R.id.viewpagerKid1);
        viewPager2 = findViewById(R.id.viewpagerKid2);
        returnB = findViewById(R.id.returnBID);
        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KidName.this, HomeLogin.class));
            }
        });
        int[] images = {R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl};
        String[] names = {"elie", "wafik", "jana", "fofo", "ahmed"};
        Date[] dates = {
                new Date(), new Date(), new Date(), new Date(), new Date()

        };


        String[] describtion = {"math", "art", "sport", "space", "physics"};

        int[] imagesCompleted = {R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl};
        String[] namesCompleted = {"elie", "wafik", "jana", "fofo", "ahmed"};
        Date[] datesCompleted = {
                new Date(), new Date(), new Date(), new Date(), new Date()

        };


        String[] describtionCompleted = {"math", "art", "sport", "space", "physics"};

        viewPagerItemArrayList = new ArrayList<>();
        viewPagerItemArrayListCompleted = new ArrayList<>();


        //////////////////////////


        for (int i = 0; i < images.length; i++) {

            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i], "dates", names[i], "see profile", describtion[i]);
            viewPagerItemArrayList.add(viewPagerItem);
            ViewPagerItem viewPagerItemCompleted = new ViewPagerItem(images[i], "dates", names[i], "see profile", describtion[i]);
            viewPagerItemArrayListCompleted.add(viewPagerItemCompleted);


        }

        VPAdapter vpAdapter = new VPAdapter(viewPagerItemArrayList);
        VPAdapter2 vpAdapter2 = new VPAdapter2(viewPagerItemArrayListCompleted);
        vpAdapter.setSharedp(true);
        vpAdapter.setCtx(this);
        vpAdapter2.setSharedp(true);
        vpAdapter2.setCtx(this);
        viewPager.setAdapter(vpAdapter);

        viewPager.setClipToPadding(false);

        viewPager.setClipChildren(false);

        viewPager.setOffscreenPageLimit(2);

        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        viewPager2.setAdapter(vpAdapter2);

        viewPager2.setClipToPadding(false);

        viewPager2.setClipChildren(false);


        viewPager2.setOffscreenPageLimit(2);

        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        viewPager.setOffscreenPageLimit(3);

        SliderTransformer sliderTransformer = new SliderTransformer(viewPager, viewPager.getCurrentItem());

        viewPager.setPageTransformer(sliderTransformer);

        viewPager2.setOffscreenPageLimit(3);
        SliderTransformer sliderTransformer2 = new SliderTransformer(viewPager2, viewPager2.getCurrentItem());

        viewPager2.setPageTransformer(sliderTransformer2);

/////////////////////////////////////////


        TabLayout tabLayout = findViewById(R.id.tab_layoutKid1);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("" )
        ).attach();
        TabLayout tabLayout2 = findViewById(R.id.tab_layoutKid2);
        new TabLayoutMediator(tabLayout2, viewPager2,
                (tab, position) -> tab.setText("")
        ).attach();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();

        // create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        kidid = "61373a2bc1866b7771fe78d8";

////////////////////////////////////////////////////////////////////getkid
        Call<Kid> call = retrofitAPI.getKid(kidid);
        call.enqueue(new Callback<Kid>() {


            @Override

            public void onResponse(Call<Kid> call, Response<Kid> response) {
                // try{
                //  Response<Kid> response=call.execute();
                System.out.println("onresponse");
                Kid kid = response.body();
                if (response.body() == null)
                    System.out.println("response is null");
                Resources r = getResources();
                //image is null
                //    int drawableId = r.getIdentifier(kid.getImage(), "drawable",
                //      "com.mypackage.myapp");

//                             try {
//                                 Class res = R.drawable.class;
//                                 Field field = res.getField("drawableName");
//                                 drawableId = field.getInt(null);
//                             } catch (Exception e) {
//                                 Log.e("MyTag", "Failure to get drawable id.", e);
//                             }
//                             kidProfilePic.setImageResource(drawableId);
                kidName.setText(kid.getFullName());
                kidName2.setText(kid.getFullName());
                for (int i = 0; i < viewPagerItemArrayList.size(); i++) {

                    viewPagerItemArrayList.get(i).setName(kid.getFullName());

                    // viewPagerItemArrayList.get(i).setImageID(drawableId);


                    // viewPagerItemArrayListCompleted.get(i).setImageID(drawableId);
                }
                for (int i = 0; i < viewPagerItemArrayListCompleted.size(); i++) {



                    // viewPagerItemArrayList.get(i).setImageID(drawableId);
                    viewPagerItemArrayListCompleted.get(i).setName(kid.getFullName());

                    // viewPagerItemArrayListCompleted.get(i).setImageID(drawableId);
                }
                viewPager.getAdapter().notifyDataSetChanged();
                viewPager2.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Kid> call, Throwable t) {
                // catch(Exception e){
                System.out.println("onfailure");
                kidName.setText("name");
                kidName2.setText("name");
                for (int i = 0; i < images.length; i++) {

                    viewPagerItemArrayList.get(i).setName("name test");
                    viewPagerItemArrayListCompleted.get(i).setName("name test2");


                }
                Log.e("tag", "msg", t);
                viewPager.getAdapter().notifyDataSetChanged();
                viewPager2.getAdapter().notifyDataSetChanged();
            }
        });

        //get number of active courses

//        Call<Integer> callnumberofactive = retrofitAPI.getNumberActiveCourses(kidid);
//
//        callnumberofactive.enqueue(new Callback<Integer>() {
//
//
//            @Override
//
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                // Response<Integer> response = callnumberofactive.execute();
//                int numofactive = response.body();
//
//                numberofactive.setText(String.valueOf(numofactive));
//
//
//            }
//
//            public void onFailure(Call<Integer> call, Throwable t) {
//                int numofactive = 5;
//                numberofactive.setText(String.valueOf(numofactive));
//            }
//        });
//        /////get number of completed courses
//        Call<Integer> callnumberofcompleted = retrofitAPI.getNumberCompletedCourses(kidid);
//        callnumberofcompleted.enqueue(new Callback<Integer>() {
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                //Response<Integer> response = callnumberofcompleted.execute();
//                int numofcompleted = response.body();
//
//                numberofcompleted.setText(String.valueOf(numofcompleted));
//
//
//            }
//
//            public void onFailure(Call<Integer> call, Throwable t) {
//                int numofcompleted = 3;
//                numberofcompleted.setText(String.valueOf(numofcompleted));
//            }
//        });
//        LogInInfo logInInfo4 = new LogInInfo(pref.getString("username", null),
//                pref.getString("password", null));
//
//////////////////get list of active courses
//        Call<List<Meeting>> callActiveCourses = retrofitAPI.getAllKidsActiveCoursesSortedKidProfile(kidid);
//
//        callActiveCourses.enqueue(new Callback<List<Meeting>>() {
//            public void onResponse(Call<List<Meeting>> call, Response<List<Meeting>> response) {
//                //Response<List<Meeting>> response = callActiveCourses.execute();
//                List<Meeting> meetings = response.body();
//                if(response.body()!=null) {
//                    for (int j = 4; j >= meetings.size(); j--) {
//                        viewPagerItemArrayList.remove(j);
//                    }
//                    for (int i = 0; i < images.length && i < meetings.size(); i++) {
//
//                        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//                        dates[i] = meetings.get(i).getMeetingDateTime();
//                        String strDate = dateFormat.format(dates[i]);
//                        describtion[i] = meetings.get(i).getId();
//
//                        Resources r = getResources();
//
//                        viewPagerItemArrayList.get(i).setDescription(describtion[i]);
//
//                        viewPagerItemArrayList.get(i).setDate(strDate);
//
//
//                    }
//                    viewPager.getAdapter().notifyDataSetChanged();
//                }
//
//            }
//
//            public void onFailure(Call<List<Meeting>> call, Throwable t) {
//                for (int i = 0; i < images.length; i++) {
//
//                    viewPagerItemArrayList.get(i).setDescription("describtion test");
//
//                    viewPagerItemArrayList.get(i).setDate("date test");
//                }
//                viewPager.getAdapter().notifyDataSetChanged();
//
//
//            }
//        });
//
//        ///////////////////////////////////////////
//        Call<List<Meeting>> callCompletedCourses = retrofitAPI.getAllKidsCompletedCoursesSortedKidProfile(kidid);
//
//        callCompletedCourses.enqueue(new Callback<List<Meeting>>() {
//            public void onResponse(Call<List<Meeting>> call, Response<List<Meeting>> response) {
//                //Response<List<Meeting>> response = callActiveCourses.execute();
//                List<Meeting> meetings = response.body();
//                if (response.body() != null) {
//                    if (meetings != null) {
//                        for (int j = 4; j >= meetings.size(); j--) {
//                            viewPagerItemArrayListCompleted.remove(j);
//                        }
//                        for (int i = 0; i < images.length && i < meetings.size(); i++) {
//
//                            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//                            datesCompleted[i] = meetings.get(i).getMeetingDateTime();
//                            String strDate = dateFormat.format(dates[i]);
//                            describtionCompleted[i] = meetings.get(i).getId();
//
//                            Resources r = getResources();
//
//                            viewPagerItemArrayListCompleted.get(i).setDescription(describtionCompleted[i]);
//
//                            viewPagerItemArrayListCompleted.get(i).setDate(strDate);
//
//
//                        }
//                        viewPager2.getAdapter().notifyDataSetChanged();
//                    } else {
//                        viewPagerItemArrayListCompleted.clear();
//                        viewPager2.getAdapter().notifyDataSetChanged();
//                    }
//                }
//            }
//
//            public void onFailure(Call<List<Meeting>> call, Throwable t) {
//                for (int i = 0; i < images.length; i++) {
//
//                    viewPagerItemArrayListCompleted.get(i).setDescription("describtion test2");
//
//                    viewPagerItemArrayListCompleted.get(i).setDate("date test2");
//                }
//                viewPager2.getAdapter().notifyDataSetChanged();
//
//
//            }
//        });

    }

}

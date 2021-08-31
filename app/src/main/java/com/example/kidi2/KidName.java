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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

public class KidName extends AppCompatActivity {
    private ViewPager2 viewPager, viewPager2;
    private ImageButton returnB;
    private ArrayList<ViewPagerItem> viewPagerItemArrayList;
    private BottomNavigationView navigationView;
    private TextView viewActive, viewCompleted,numberofactive,numberofcompleted;
    private String kidid;
    private ImageButton kidProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kid_name);
        /////////////////////////

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        navigationView = findViewById(R.id.navibarKidName);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationHomeMenuId:
                        startActivity(new Intent(KidName.this, HomeLogin.class));
                        return true;
                    case R.id.bottomNavigationUserMenuId:
                        startActivity(new Intent(KidName.this, Activity.class));
                        return true;
                    case R.id.bottomNavigationActivityMenuId:
                        startActivity(new Intent(KidName.this, FirstScreen.class));
                        return true;
                    case R.id.bottomNavigationNotificatonsMenuId:
                        startActivity(new Intent(KidName.this, KidName.class));
                        return true;
                    case R.id.bottomNavigationMoreMenuId:
                        PopupMenu popup = new PopupMenu(KidName.this, findViewById(R.id.bottomNavigationMoreMenuId));
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.mymenu, popup.getMenu());
                        popup.show();
                        return true;
                }
                return false;
            }
        });
        numberofactive=findViewById(R.id.numberofactive);
        numberofcompleted=findViewById(R.id.numberofcompleted);
        kidProfilePic = findViewById(R.id.kidImageID);
        viewActive = findViewById(R.id.viewAllActiveKidID);
        viewCompleted = findViewById(R.id.viewAllCompletedKidID);
        kidid = "kidid";
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9010/")
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();

        // create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Kid> call = retrofitAPI.getKid(kidid);
        call.enqueue(new Callback<Kid>() {


            @Override

            public void onResponse(Call<Kid> call, Response<Kid> response) {
                // this method is called when we get response from our api.
                Kid kid = response.body();
                Resources r = getResources();
                int drawableId = r.getIdentifier(kid.getImage(), "drawable",
                        "com.mypackage.myapp");

                try {
                    Class res = R.drawable.class;
                    Field field = res.getField("drawableName");
                    drawableId = field.getInt(null);
                } catch (Exception e) {
                    Log.e("MyTag", "Failure to get drawable id.", e);
                }
                kidProfilePic.setImageResource(drawableId);


            }

            @Override
            public void onFailure(Call<Kid> call, Throwable t) {

            }
        });
        Call<Integer> callnumberofactive = retrofitAPI.getNumberActiveCourses(kidid);
        callnumberofactive.enqueue(new Callback<Integer>() {


            @Override

            public void onResponse(Call<Integer> call, Response<Integer> response) {
              int numofactive=response.body();

                numberofactive.setText(String.valueOf(numofactive));


            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                int numofactive=5;
                numberofactive.setText(String.valueOf(numofactive));
            }
        });
        Call<Integer> callnumberofcompleted = retrofitAPI.getNumberCompletedCourses(kidid);
        callnumberofcompleted.enqueue(new Callback<Integer>() {


            @Override

            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int numofcompleted=response.body();
                numberofcompleted.setText(String.valueOf(numofcompleted));



            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                int numofcompleted=3;
                numberofcompleted.setText(String.valueOf(numofcompleted));
            }
        });

        //////////////////////////
        viewPager = findViewById(R.id.viewpagerKid1);
        viewPager2 = findViewById(R.id.viewpagerKid2);
        returnB = findViewById(R.id.returnBID);
        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KidName.this, FirstScreen.class));
            }
        });
        int[] images = {R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl};
        String[] names = {"elie", "wafik", "jana", "fofo", "ahmed"};
        String[] dates = {"21 jul", "31 aug", "16 dec", "21 jan", "23 aug"};
        final Calendar myCalendar = Calendar.getInstance();

        String[] describtion = {"math", "art", "sport", "space", "physics"};

        viewPagerItemArrayList = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {

            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i], dates[i], names[i], "see profile", describtion[i]);
            viewPagerItemArrayList.add(viewPagerItem);


        }

        VPAdapter vpAdapter = new VPAdapter(viewPagerItemArrayList);
        VPAdapter2 vpAdapter2 = new VPAdapter2(viewPagerItemArrayList);
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
                (tab, position) -> tab.setText("" + (position + 1))
        ).attach();
        TabLayout tabLayout2 = findViewById(R.id.tab_layoutKid2);
        new TabLayoutMediator(tabLayout2, viewPager2,
                (tab, position) -> tab.setText("" + (position + 1))
        ).attach();
    }

}

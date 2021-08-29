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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeLogin extends AppCompatActivity {
    ViewPager2 viewPager, viewPager2;
    private static final int limit_of_activities = 5;
    private BottomNavigationView navigationView;

    ArrayList<ViewPagerItem> viewPagerItemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_login);

        String parentId;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        parentId = pref.getString("ParentID", null); // getting Integer

        navigationView = findViewById(R.id.navibarhomelogin);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottomNavigationHomeMenuId:
                        startActivity(new Intent(HomeLogin.this,FirstScreen.class));
                        return true;
                    case R.id.bottomNavigationUserMenuId:
                        startActivity(new Intent(HomeLogin.this,Activity.class));
                        return true;
                    case R.id.bottomNavigationActivityMenuId:
                        startActivity(new Intent(HomeLogin.this,HomeLogin.class));
                        return true;
                    case R.id.bottomNavigationNotificatonsMenuId:
                        startActivity(new Intent(HomeLogin.this,KidName.class));
                        return true;
                    case R.id.bottomNavigationMoreMenuId:
                        startActivity(new Intent(HomeLogin.this,HomeLogin.class));
                        return true;
                }
                return false;
            }
        });


        int[] images = {R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl, R.drawable.girl};
        // String[] names = {"elie","name2","name3","name4","name5"};
        //  String[] dates = {"date1","date2","date3","date4","date5"};

        //   String[] describtion = {"describtion1","describtion2","describtion3","describtion4","describtion5"};


        String[] names = {"elie", "wafik", "jana", "fofo", "ahmed"};
        String[] dates = {"21 jul", "31 aug", "16 dec", "21 jan", "23 aug"};


        String[] describtion = {"math", "art", "sport", "space", "physics"};
        //call backend
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9010/")
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();

        // create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        //DataModel user = new DataModel();

        Call<List<DataModelKid>> call = retrofitAPI.GetAllKidsOfParent(parentId);

        call.enqueue(new Callback<List<DataModelKid>>() {


            @Override

            public void onResponse(Call<List<DataModelKid>> call, Response<List<DataModelKid>> response) {
                // this method is called when we get response from our api.
                List<DataModelKid> kidLst = response.body();
                for (int i = 0; i < limit_of_activities; i++) {
                    if (kidLst.get(i) == null)
                        break;
                    names[i] = kidLst.get(i).getFullName();
                    Resources r = getResources();
                    int drawableId = r.getIdentifier(kidLst.get(i).getImage(), "drawable", "com.mypackage.myapp");

                    try {
                        Class res = R.drawable.class;
                        Field field = res.getField("drawableName");
                        drawableId = field.getInt(null);
                    } catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                    images[i] = drawableId;
                }


                //Call<DataModelParent> call2 = retrofitAPI.sendMailToUser(email);


                // getting response from our body
                // and passing it to our model class.
                List<DataModelKid> responseFromAPI = response.body();

                // getting our data from model class and adding it to our string.
                //String responseString = "Response Code : " + response.code() +
                //     "\nManufacturer : " + responseFromAPI.getEmail();

                System.out.println("error onfailure");


            }

            @Override
            public void onFailure(Call<List<DataModelKid>> call, Throwable t) {

            }
        });
        //end call


        viewPager = findViewById(R.id.viewpager);
        viewPager2 = findViewById(R.id.viewpager2);


        viewPagerItemArrayList = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {

            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i], dates[i], names[i], "see profile", describtion[i]);
            viewPagerItemArrayList.add(viewPagerItem);


        }

        VPAdapter vpAdapter = new VPAdapter(viewPagerItemArrayList);
        VPAdapter2 vpAdapter2 = new VPAdapter2(viewPagerItemArrayList);
        viewPager.setAdapter(vpAdapter);

        viewPager.setClipToPadding(false);

        viewPager.setClipChildren(false);

        viewPager.setOffscreenPageLimit(2);

        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        viewPager2.setAdapter(vpAdapter2);

        viewPager2.setClipToPadding(false);

        viewPager2.setClipChildren(false);


        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);


///////////////////////////////////////////////////


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


    }

}

package com.example.kidi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class KidName extends AppCompatActivity {
    private ViewPager2 viewPager, viewPager2;
    private ImageButton returnB, calenderImage;
    private ArrayList<ViewPagerItem> viewPagerItemArrayList;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kid_name);
        /////////////////////////


        navigationView = findViewById(R.id.navibarKidName);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationHomeMenuId:
                        startActivity(new Intent(KidName.this, FirstScreen.class));
                        return true;
                    case R.id.bottomNavigationUserMenuId:
                        startActivity(new Intent(KidName.this, Activity.class));
                        return true;
                    case R.id.bottomNavigationActivityMenuId:
                        startActivity(new Intent(KidName.this, HomeLogin.class));
                        return true;
                    case R.id.bottomNavigationNotificatonsMenuId:
                        startActivity(new Intent(KidName.this, KidName.class));
                        return true;
                    case R.id.bottomNavigationMoreMenuId:
                        startActivity(new Intent(KidName.this, HomeLogin.class));
                        return true;
                }
                return false;
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
        EditText calenderText = findViewById(R.id.dateTextID);
        calenderImage = findViewById(R.id.calenderImageBID);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("select text");
        final MaterialDatePicker materialDatePicker = builder.build();

        calenderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "date_picker");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                calenderText.setText(materialDatePicker.getHeaderText());
            }
        });
        calenderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "date_picker");
            }
        });


        // MaterialDatePicker.Builder builder=MaterialDatePicker.Builder.datePicker();
        //  builder.setTitleText("select date");
        // MaterialDatePicker materialDatePicker=builder.build();

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

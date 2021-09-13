package com.example.kidi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity extends AppCompatActivity {
    private EditText fromDateText;
    private BottomNavigationView navigationView;
    private int statefilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
/////////////////////////////////////////
        reloadavtivities();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();        navigationView = findViewById(R.id.navibarActivity);
        String parentId;
        String userType;
        userType = pref.getString("userType", "parent");

        parentId = pref.getString("parentID", null);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationHomeMenuId:
                        startActivity(new Intent(Activity.this, HomeLogin.class));
                        return true;
                    case R.id.bottomNavigationUserMenuId:
                        startActivity(new Intent(Activity.this, Activity.class));
                        return true;
                    case R.id.bottomNavigationActivityMenuId:
                        startActivity(new Intent(Activity.this, FirstScreen.class));
                        return true;
                    case R.id.bottomNavigationNotificatonsMenuId:
                        startActivity(new Intent(Activity.this, KidName.class));
                        return true;
                    case R.id.bottomNavigationMoreMenuId:
                        PopupMenu popup = new PopupMenu(Activity.this, findViewById(R.id.bottomNavigationMoreMenuId));
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.mymenu, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId()==R.id.logoutmenu)
                                    startActivity(new Intent(Activity.this, FirstScreen.class));

                                return true;
                            }
                        });
                        popup.show();
                        return true;
                }
                return false;
            }
        });

        Spinner spinnerKid = (Spinner) findViewById(R.id.chooseKid);
        Spinner spinnerCategory = (Spinner) findViewById(R.id.chooseCategory);
        Spinner spinnerState = (Spinner) findViewById(R.id.chooseState);

        //Sample String ArrayList
        ArrayList<String> arrayListKid = new ArrayList<String>();
        ArrayList<String> arrayListCategory = new ArrayList<String>();
        ArrayList<String> arrayListState = new ArrayList<String>();
        arrayListKid.add("kid");
        arrayListKid.add("elie");
        arrayListKid.add("wafik");
        arrayListKid.add("jana");

        arrayListCategory.add("category");
        arrayListCategory.add("Space");
        arrayListCategory.add("Math");
        arrayListCategory.add("Art");

        arrayListState.add("All");
        arrayListState.add("Active");
        arrayListState.add("Completed");

        ArrayAdapter<String> adpKid = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayListKid);
        spinnerKid.setAdapter(adpKid);
        ArrayAdapter<String> adpState = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayListState);
        spinnerState.setAdapter(adpState);
        ArrayAdapter<String> adpCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayListCategory);
        spinnerCategory.setAdapter(adpCategory);

        String state=pref.getString("meetingState",null);

        editor.commit();
        switch(state){
            case "all":

                spinnerState.setSelection(0);
                break;
            case "active":

                spinnerState.setSelection(1);
                break;
            case "completed":

                spinnerState.setSelection(2);
                break;
        }

        spinnerKid.setVisibility(View.VISIBLE);
        //Set listener Called when the item is selected in spinner
        spinnerKid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long arg3) {
                reloadavtivities();

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        spinnerState.setVisibility(View.VISIBLE);
        //Set listener Called when the item is selected in spinner
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long arg3) {
                statefilter=spinnerState.getSelectedItemPosition();
                reloadavtivities();

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        spinnerCategory.setVisibility(View.VISIBLE);
        //Set listener Called when the item is selected in spinner
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long arg3) {
                reloadavtivities();

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        ////////////////////////
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();

        // create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

///////////////////////////////
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("select date");
        final MaterialDatePicker materialDatePicker = builder.build();


        fromDateText = findViewById(R.id.dateActivityTextID);
        fromDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "date_picker");
            }
        });




        materialDatePicker.addOnPositiveButtonClickListener
                (new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        fromDateText.setText(materialDatePicker.getHeaderText());
                    }
                });

        ImageView activityBtn = findViewById(R.id.imageButton6);
        ImageView addBtn = findViewById(R.id.imageButton7);
        //////////////////////////
        if (userType.equals("leader")) {
            activityBtn.setVisibility(View.INVISIBLE);
            addBtn.setVisibility(View.INVISIBLE);
            Menu nav_Menu = navigationView.getMenu();
            MenuItem target = nav_Menu.findItem(R.id.bottomNavigationUserMenuId);
            target.setVisible(false);
        }

        //////////
    }
    public void reloadavtivities() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService
                (this.LAYOUT_INFLATER_SERVICE);
        LinearLayout scrollViewLinearlayout = (LinearLayout) findViewById(R.id.scrollViewLayoutID);
        scrollViewLinearlayout.removeAllViews();
        if(statefilter!=2) {
            // The layout inside scroll view
            for (int i = 0; i < 5; i++) {
                LinearLayout layout2 = new LinearLayout(this);
                layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                View item = inflater.inflate(R.layout.viewpager_item, null, false);
                layout2.setId(i);
                layout2.addView(item);
                scrollViewLinearlayout.addView(layout2);
            }
        }
        if(statefilter!=1) {
            for (int i = 5; i < 10; i++) {
                LinearLayout layout2 = new LinearLayout(this);
                layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                View item = inflater.inflate(R.layout.viewpager_item2, null, false);
                layout2.setId(i);
                layout2.addView(item);
                scrollViewLinearlayout.addView(layout2);
            }
        }
    }
}
package com.example.kidi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SearchView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminSetUser extends AppCompatActivity   {
    RecyclerView recyclerview;
    LeadersAdapter adapterleader;
    ArrayList<Leaders> leaders;
    ArrayList<String> usernamesList=new ArrayList<>();
    List<Leaders> leadersManagement=new ArrayList<>();

    AdminAdapter adapteradmin;
    ArrayList<Admin> admins;
    List<Admin> adminsManagement=new ArrayList<>();
    ArrayList<String> adminsList=new ArrayList<>();


    ParentAdapter adapterparent;
    ArrayList<Parent> parents;
    List<Parent> parentsManagement=new ArrayList<>();
    ArrayList<String> parentsList=new ArrayList<>();


    Retrofit retrofit ;
    RetrofitAPIAdminSetUser retrofitAPI;

    Button button ;
    Button parentsBtn;
    Button leadersBtn ;
    Button adminsBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_user_admin);
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);

        //Retrofit retrofit = new Retrofit.Builder().baseUrl(String.valueOf(R.string.BASE_URL)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofit = new Retrofit.Builder().baseUrl(getString(R.string.BASE_URL)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitAPI = retrofit.create(RetrofitAPIAdminSetUser.class);
        leadersBtn = findViewById(R.id.leaders_button);
        leadersBtn.setSelected(true);
        leadersBtn.setTextColor(Color.parseColor("#000000"));
        Call<List<Leaders>> myLeaders = retrofitAPI.retrieveAllLeaders();
        myLeaders.enqueue(new Callback<List<Leaders>>() {
            @Override
            public void onResponse(Call<List<Leaders>> call, Response<List<Leaders>> response) {
                List<Leaders> allLeaders=response.body();
                setRecyclerViewLeader(allLeaders);
            }

            @Override
            public void onFailure(Call<List<Leaders>> call, Throwable t) {

            }
        });

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        for(Leaders m : leadersManagement){
            usernamesList.add( m.getFullName());
        }
        System.out.println(usernamesList.toString());

        recyclerview.addItemDecoration(new SimpleDividerItemDecoration(getResources()));

        //xgetdata();

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.home_page:
                        //startActivity(new Intent(AdminMainActivity.this, AdminUpdateCourse.class));
                        // getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, homeFragment).commit();
                        return true;

                    case R.id.users_page:
                        startActivity(new Intent(AdminSetUser.this, AdminSetUser.class));
                        // getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, leadersFragment).commit();
                        return true;

                    case R.id.leaders_page:
                        startActivity(new Intent(AdminSetUser.this, AdminSetLeader.class));
                        // getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, userFragment).commit();
                        return true;

                    case R.id.course_page:
                        startActivity(new Intent(AdminSetUser.this, AdminSetCourse.class));
                        //  getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_fragments, coursesFragment).commit();
                        return true;

                    case R.id.more_page:

                        PopupMenu popup = new PopupMenu(AdminSetUser.this, findViewById(R.id.more_page));
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.mymenu, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId()==R.id.logoutmenu)
                                    startActivity(new Intent(AdminSetUser.this, FirstScreen.class));

                                return true;
                            }
                        });
                        popup.show();
                        return true;
                }
                return false;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.search_filter, menu);
        SearchView searchView = (SearchView) findViewById(R.id.sv);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterleader.getFilter().filter(newText);
                if(leadersBtn.isSelected()) {
                    adapterleader.getFilter().filter(newText);
                    return false;
                }
                if(parentsBtn.isSelected()) {
                    adapterparent.getFilter().filter(newText);
                    return false;
                }
                if(adminsBtn.isSelected()) {
                    adapteradmin.getFilter().filter(newText);
                    return false;
                }
                return false;
            }
        });
        return true;
    }


    //private ArrayList<String> getQuantityData(){
    //  ArrayList<String> arrayList=new ArrayList<>();
    //arrayList.add("10");
    // arrayList.add("20");
    // arrayList.add("30");
    //arrayList.add("40");
    // return arrayList;
    // }
    private void setRecyclerViewLeader( List<Leaders> all_leaders) {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //adapter=new QuantityAapter(this,getQuantityData(),this );
        adapterleader=new LeadersAdapter(all_leaders);

        recyclerview.setAdapter(adapterleader);

    }

    private void setRecyclerViewParent( List<Parent> all_parents) {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //adapter=new QuantityAapter(this,getQuantityData(),this );
        adapterparent=new ParentAdapter(all_parents);

        recyclerview.setAdapter(adapterparent);

    }

    private void setRecyclerViewAdmin( List<Admin> all_admins) {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //adapter=new QuantityAapter(this,getQuantityData(),this );
        adapteradmin=new AdminAdapter(all_admins);

        recyclerview.setAdapter(adapteradmin);

    }

    public void buttonPressed(View view) {
        button = findViewById(view.getId());
        parentsBtn = findViewById(R.id.parents_button);
        leadersBtn = findViewById(R.id.leaders_button);
        adminsBtn = findViewById(R.id.admins_button);

        if (button.isSelected()) {
            button.setSelected(false);
            button.setTextColor(Color.parseColor("#000000"));
        } else if (!button.isSelected()){
            button.setSelected(true);
            button.setTextColor(Color.parseColor("#ffffff"));
        }
        if(parentsBtn.isSelected() && leadersBtn.isSelected()){
            if(view.getId()==parentsBtn.getId()){
                leadersBtn.setSelected(false);
                leadersBtn.setTextColor(Color.parseColor("#000000"));

            }
            if(view.getId()==leadersBtn.getId()){
                parentsBtn.setSelected(false);
                parentsBtn.setTextColor(Color.parseColor("#000000"));

            }
        }
        if(leadersBtn.isSelected() && adminsBtn.isSelected()){
            if(view.getId()==leadersBtn.getId()){
                adminsBtn.setSelected(false);
                adminsBtn.setTextColor(Color.parseColor("#000000"));

            }
            if(view.getId()== adminsBtn.getId()){
                leadersBtn.setSelected(false);
                leadersBtn.setTextColor(Color.parseColor("#000000"));

            }
        }
        if(parentsBtn.isSelected() && adminsBtn.isSelected()){
            if(view.getId()==parentsBtn.getId()){
                adminsBtn.setSelected(false);
                adminsBtn.setTextColor(Color.parseColor("#000000"));

            }
            if(view.getId()== adminsBtn.getId()){
                parentsBtn.setSelected(false);
                parentsBtn.setTextColor(Color.parseColor("#000000"));

            }
        }
        if(parentsBtn.isSelected()){
            refreshByparents();
        }
        else if(leadersBtn.isSelected()){
            refreshByleaders();
        }
        else if(adminsBtn.isSelected()){
            refreshByadmins();
        }
    }

    private void refreshByadmins() {
        Call<List<Admin>> myAdmins = retrofitAPI.retrieveAllAdmins();
        myAdmins.enqueue(new Callback<List<Admin>>() {
            @Override
            public void onResponse(Call<List<Admin>> call, Response<List<Admin>> response) {
                List<Admin> allAdmins=response.body();
                setRecyclerViewAdmin(allAdmins);
            }

            @Override
            public void onFailure(Call<List<Admin>> call, Throwable t) {

            }
        });

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        for(Admin m : adminsManagement){
            adminsList.add( m.getFullName());
        }
        System.out.println(adminsList.toString());

        recyclerview.addItemDecoration(new SimpleDividerItemDecoration(getResources()));

        //xgetdata();
    }

    private void refreshByleaders() {
        Call<List<Leaders>> myLeaders = retrofitAPI.retrieveAllLeaders();
        myLeaders.enqueue(new Callback<List<Leaders>>() {
            @Override
            public void onResponse(Call<List<Leaders>> call, Response<List<Leaders>> response) {
                List<Leaders> allLeaders=response.body();
                setRecyclerViewLeader(allLeaders);
            }

            @Override
            public void onFailure(Call<List<Leaders>> call, Throwable t) {

            }
        });

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        for(Leaders m : leadersManagement){
            usernamesList.add( m.getFullName());
        }
        System.out.println(usernamesList.toString());

        recyclerview.addItemDecoration(new SimpleDividerItemDecoration(getResources()));

        //xgetdata();
    }

    private void refreshByparents() {
        Call<List<Parent>> myParents = retrofitAPI.retrieveAllParents();
        myParents.enqueue(new Callback<List<Parent>>() {
            @Override
            public void onResponse(Call<List<Parent>> call, Response<List<Parent>> response) {
                List<Parent> allParent=response.body();
                setRecyclerViewParent(allParent);
            }

            @Override
            public void onFailure(Call<List<Parent>> call, Throwable t) {

            }
        });

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        for(Parent m : parentsManagement){
            parentsList.add( m.getFullName());
        }
        System.out.println(parentsList.toString());

        recyclerview.addItemDecoration(new SimpleDividerItemDecoration(getResources()));

        //xgetdata();
    }
    //private ArrayList<LeaderManagement> getList(){
    //leadersManagement=retrofitAP
    //Call<List<Leaders>> leaders = re
    // leadersManagement.add(new LeaderManagement("rili amdder","leader","21","Active","1/21/2020"));
    //leadersManagement.add(new LeaderManagement("rola marei","leader","22","Active","1/21/2020"));
    // leadersManagement.add(new LeaderManagement("elei ha ","leader","23","Active","1/21/2020"));
    // leadersManagement.add(new LeaderManagement("w","leader","24","Active","1/21/2020"));
    // leadersManagement.add(new LeaderManagement("m","leader","25","Active","1/21/2020"));
    // leadersManagement.add(new LeaderManagement("r","leader","26","Active","1/21/2020"));
    // leadersManagement.add(new LeaderManagement("o","leader","27","Active","1/21/2020"));
    //leadersManagement.add(new LeaderManagement("r","leader","28","Active","1/21/2020"));
    // leadersManagement.add(new LeaderManagement("s","leader","29","Active","1/21/2020"));

    // return leadersManagement;
    //}


}
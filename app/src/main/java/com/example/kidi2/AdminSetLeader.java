package com.example.kidi2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminSetLeader extends AppCompatActivity   {
    RecyclerView recyclerview;
    LeaderAdapter adapter;
    ArrayList<Leader> leaders;
    ArrayList<String> usernamesList=new ArrayList<>();
    List<Leader> leadersManagement=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_leader_admin);
        //Retrofit retrofit = new Retrofit.Builder().baseUrl(String.valueOf(R.string.BASE_URL)).addConverterFactory(GsonConverterFactory.create()).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.BASE_URL)).
                addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPIAdminSetLeader retrofitAPI = retrofit.create(RetrofitAPIAdminSetLeader.class);

        Call<List<Leader>> myLeaders = retrofitAPI.retrieveAllLeaders();
        myLeaders.enqueue(new Callback<List<Leader>>() {
            @Override
            public void onResponse(Call<List<Leader>> call, Response<List<Leader>> response) {
                   List<Leader> allLeaders=response.body();
                setRecyclerView(allLeaders);
            }

            @Override
            public void onFailure(Call<List<Leader>> call, Throwable t) {

            }
        });

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        for(Leader m : leadersManagement){
            usernamesList.add( m.getFullName());
        }
        System.out.println(usernamesList.toString());

        recyclerview.addItemDecoration(new SimpleDividerItemDecoration(getResources()));

        //xgetdata();
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
                adapter.getFilter().filter(newText);
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
    private void setRecyclerView( List<Leader> all_leaders) {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //adapter=new QuantityAapter(this,getQuantityData(),this );
        adapter=new LeaderAdapter(all_leaders);

        recyclerview.setAdapter(adapter);

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
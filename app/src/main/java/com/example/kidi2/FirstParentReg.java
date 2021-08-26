package com.example.myapplication11;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstParentReg extends AppCompatActivity  {
    private Button bt1;
    public ArrayList<AlgorithmItem> algorithmItems;
    private AlgorithmAdapter adapter;
    boolean first_one=true;
    static int counter=0;
    RvAdapter adpt;
    SharedPreferences s;

    Context context;

    ViewPager2 imageContainer;
    SliderAdapter adapter2;
    int list[];
    TextView[] dots;
    LinearLayout layout;
    RecyclerView rv;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8092/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
    // create an instance for our retrofit api class.
    RetroFitAPI retrofitAPI = retrofit.create(RetroFitAPI.class);
    Spinner spinner;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_parent_reg);

        //ViewPager v = findViewById(R.id.vie);
        //v.setAdapter(new CustomPagerAdapter(this));//,algorithmItems));

        // we pass our item list and context to our Adapter.
        rv=findViewById(R.id.rv_1);
        imageContainer = findViewById(R.id.image_container);
        layout = findViewById(R.id.dots_container);
        algorithmItems = new ArrayList<AlgorithmItem>();
        algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
        dots = new TextView[3];
        //spinner = findViewById(R.id.spinner);
        list = new int[3];
        list[0] = R.drawable.drftspace;
        list[1] = R.drawable.anml1;  //getResources().getColor(R.color.red);
        list[2] = R.drawable.art;
        //list[3] = getResources().getColor(R.color.yellow);
        //list[4] = getResources().getColor(R.color.orange);

        adapter2 = new SliderAdapter(list);
        imageContainer.setAdapter(adapter2);

        setIndicators();
        imageContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectedDots(position);
                super.onPageSelected(position);
                if(position==0)
                    initList();
                else if(position==1)
                    initList1();
                else
                    initList2();

            }
        });

        adpt=new RvAdapter(this,algorithmItems);
        rv.setAdapter(adpt);
        rv.setLayoutManager(new LinearLayoutManager(this));


        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Toast.makeText(FirstParentReg.this,   " selected", Toast.LENGTH_SHORT).show();
                        for (int i=0;i<algorithmItems.size();i++) {
                            if(position!=i) {
                                algorithmItems.get(i).setIm(R.drawable.gradgrey);
                            }
                        }
                        algorithmItems.get(position).setIm(R.drawable.grad);
                        adpt.setItems(algorithmItems);
                        adpt.notifyDataSetChanged();




                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever


                    }
                })
        );



        /*
        adapter = new AlgorithmAdapter(MainActivity.this, algorithmItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //spinner.setElevation(2);
        bt1 = findViewById(R.id.button6);

         */


        }





    // It is used to set the algorithm names to our array list.


    private void initList() {
        algorithmItems.clear();
        adpt.notifyDataSetChanged();
        Call<List<DataModel>> call = retrofitAPI.getSpacesCourses();
        //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {

                List<DataModel> responseFromAPI = response.body();
                int len=responseFromAPI.size();
                for (int i=0;i<len;i++)
                    algorithmItems.add(new AlgorithmItem(responseFromAPI.get(i).getCourseName(), responseFromAPI.get(i).getDay() + " " + responseFromAPI.get(i).getTime() + responseFromAPI.get(i).getLength(),R.drawable.gradgrey));
                //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(1).getCourseName(), responseFromAPI.get(1).getDay() + " " + responseFromAPI.get(1).getTime() + responseFromAPI.get(1).getLength()));
                //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(2).getCourseName(), responseFromAPI.get(2).getDay() +" "+ responseFromAPI.get(2).getTime() + responseFromAPI.get(2).getLength()));
                //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(3).getCourseName(), responseFromAPI.get(3).getDay() +" "+ responseFromAPI.get(3).getTime() + responseFromAPI.get(3).getLength()));
                //a1=(new AlgorithmItem(responseFromAPI.get(0).getCourseName(), responseFromAPI.get(0).getDay() + responseFromAPI.get(0).getTime() + responseFromAPI.get(0).getLength()));
                //a2=(new AlgorithmItem(responseFromAPI.get(1).getCourseName(), responseFromAPI.get(1).getDay() + responseFromAPI.get(1).getTime() + responseFromAPI.get(1).getLength()));

                //spinner.performClick();
                adpt.setItems(algorithmItems);
                adpt.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {

            }
        });
    }


        private void initList1 () {
            algorithmItems.clear();
            adpt.notifyDataSetChanged();
            Call<List<DataModel>> call = retrofitAPI.getAnimalsCourses();
            //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
            call.enqueue(new Callback<List<DataModel>>() {
                @Override
                public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {

                    List<DataModel> responseFromAPI = response.body();
                    int len=responseFromAPI.size();
                    for (int i=0;i<len;i++)
                        algorithmItems.add(new AlgorithmItem(responseFromAPI.get(i).getCourseName(), responseFromAPI.get(i).getDay() + " " + responseFromAPI.get(i).getTime() + responseFromAPI.get(i).getLength(),R.drawable.gradgrey));
                    //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(1).getCourseName(), responseFromAPI.get(1).getDay() +" "+ responseFromAPI.get(1).getTime() + responseFromAPI.get(1).getLength()));
                    //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(2).getCourseName(), responseFromAPI.get(2).getDay() +" "+ responseFromAPI.get(2).getTime() + responseFromAPI.get(2).getLength()));
                    //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(3).getCourseName(), responseFromAPI.get(3).getDay() +" "+ responseFromAPI.get(3).getTime() + responseFromAPI.get(3).getLength()));
                    //a1=(new AlgorithmItem(responseFromAPI.get(0).getCourseName(), responseFromAPI.get(0).getDay() + responseFromAPI.get(0).getTime() + responseFromAPI.get(0).getLength()));
                    //a2=(new AlgorithmItem(responseFromAPI.get(1).getCourseName(), responseFromAPI.get(1).getDay() + responseFromAPI.get(1).getTime() + responseFromAPI.get(1).getLength()));
                    //spinner.performClick();
                    adpt.setItems(algorithmItems);
                    adpt.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<DataModel>> call, Throwable t) {

                }
            });

        }



    private void initList2 () {
        algorithmItems.clear();
        adpt.notifyDataSetChanged();
        Call<List<DataModel>> call = retrofitAPI.getArtsCourses();
        //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {

                List<DataModel> responseFromAPI = response.body();
                int len=responseFromAPI.size();
                for (int i=0;i<len;i++)
                    algorithmItems.add(new AlgorithmItem(responseFromAPI.get(i).getCourseName(), responseFromAPI.get(i).getDay() + " " + responseFromAPI.get(i).getTime() + responseFromAPI.get(i).getLength(),R.drawable.gradgrey));
                //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(1).getCourseName(), responseFromAPI.get(1).getDay() +" "+ responseFromAPI.get(1).getTime() + responseFromAPI.get(1).getLength()));
                //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(2).getCourseName(), responseFromAPI.get(2).getDay() +" "+ responseFromAPI.get(2).getTime() + responseFromAPI.get(2).getLength()));
                //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(3).getCourseName(), responseFromAPI.get(3).getDay() +" "+ responseFromAPI.get(3).getTime() + responseFromAPI.get(3).getLength()));
                //a1=(new AlgorithmItem(responseFromAPI.get(0).getCourseName(), responseFromAPI.get(0).getDay() + responseFromAPI.get(0).getTime() + responseFromAPI.get(0).getLength()));
                //a2=(new AlgorithmItem(responseFromAPI.get(1).getCourseName(), responseFromAPI.get(1).getDay() + responseFromAPI.get(1).getTime() + responseFromAPI.get(1).getLength()));
                //spinner.performClick();
                adpt.setItems(algorithmItems);
                adpt.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {

            }
        });

    }


            //algorithmItems.add(a2);

/*
        algorithmItems.add(new AlgorithmItem("Quick Sort","12"));
        algorithmItems.add(new AlgorithmItem("Merge Sort","12"));
        algorithmItems.add(new AlgorithmItem("Heap Sort","12"));
        algorithmItems.add(new AlgorithmItem("Prims Algorithm","12"));
        algorithmItems.add(new AlgorithmItem("Kruskal Algorithm","12"));
        algorithmItems.add(new AlgorithmItem("Rabin Karp","12"));
        algorithmItems.add(new AlgorithmItem("Binary Search","12"));

*/



/*
        @Override
        public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){

            // It returns the clicked item.
            AlgorithmItem clickedItem = (AlgorithmItem) parent.getItemAtPosition(position);
            String name = clickedItem.getAlgorithmName();
            Toast.makeText(MainActivity.this, name + " selected", Toast.LENGTH_SHORT).show();
            if(position!=0)
                clickedItem.setIm(R.drawable.grad);
            if (first_one!=true)
                spinner.performClick();
            //if(first_one!=true)
              //  Toast.makeText(this,parent.getCount(),Toast.LENGTH_LONG).show();
            first_one=false;

        }

        @Override
        public void onNothingSelected (AdapterView < ? > parent){
            spinner.performClick();
        }

 */




        private void selectedDots ( int position){
            for (int i = 0; i < dots.length; i++) {
                if (i == position) {
                    dots[i].setTextColor(list[position]);
                } else {
                    dots[i].setTextColor(getResources().getColor(R.color.grey));
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
    }
package com.example.kidi2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstParentReg extends AppCompatActivity {
    private Button bt1, nextB, backB;
    public ArrayList<AlgorithmItem> algorithmItems;
    private AlgorithmAdapter adapter;
    boolean first_one = true;
    static int counter = 0;
    RvAdapter adpt;
    SharedPreferences s;

    Context context;

    ViewPager2 imageContainer;
    SliderAdapter adapter2;
    int list[];
    TextView[] dots;
    LinearLayout layout;
    RecyclerView rv;
    String space_id = "612a326989674a4e38a688a0";
    String art_id = "612a326a89674a4e38a688a1";
    String animal_id = "612a326a89674a4e38a688a2";
    String siecnce_id = "612a326a89674a4e38a688a3";
    String music_id = "61373a2bc1866b7771fe78da";


    Spinner spinner;
    SharedPreferences pref;
    Retrofit retrofit;
    RetroFitAPI2 retrofitAPI;

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_parent_reg);
        nextB = findViewById(R.id.nextButton);
        pref = getSharedPreferences("MyKIDIPref",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("fullName", "");
        editor.putString("email", "");
        editor.putString("phone", "");
        editor.putString("password", "");
        editor.putInt("code", 100);
        editor.putString("parentIDReg","");
        editor.commit();
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();
        // create an instance for our retrofit api class.
        retrofitAPI = retrofit.create(RetroFitAPI2.class);
        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstParentReg.this, FirstParentRegReal.class));

            }
        });
        backB = findViewById(R.id.backButtonReg);
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstParentReg.this, FirstScreen.class));

            }
        });
        //ViewPager v = findViewById(R.id.vie);
        //v.setAdapter(new CustomPagerAdapter(this));//,algorithmItems));

        // we pass our item list and context to our Adapter.
        rv = findViewById(R.id.rv_1);
        imageContainer = findViewById(R.id.image_container);
        layout = findViewById(R.id.dots_container);
        algorithmItems = new ArrayList<AlgorithmItem>();
        algorithmItems.add(new AlgorithmItem("choose course", "", R.color.white));

        dots = new TextView[5];
        //spinner = findViewById(R.id.spinner);
        list = new int[5];
        list[0] = R.drawable.drftspace;
        list[1] = R.drawable.anml1;  //getResources().getColor(R.color.red);
        list[2] = R.drawable.art;
        list[3] = R.drawable.scs;
        list[4] = R.drawable.musics;

        adapter2 = new SliderAdapter(list);
        imageContainer.setAdapter(adapter2);

        setIndicators();
        imageContainer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectedDots(position);
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        initListgen(space_id);
                        break;
                    case 1:
                        initListgen(animal_id);
                        break;
                    case 2:
                        initListgen(art_id);
                        break;
                    case 3:
                        initListgen(siecnce_id);
                        break;
                    case 4:
                        initListgen(music_id);
                        break;
                    default:
                        // code block
                }
                //fot test
//                switch (position)
//                {
//                    case 0:initList();
//                        break;
//                    case 1:initList1();
//                        break;
//                    case 2:initList2();
//
//                        break;
//                    default:
//                        // code block
//                }

            }
        });

        adpt = new RvAdapter(this, algorithmItems);
        rv.setAdapter(adpt);
        rv.setLayoutManager(new LinearLayoutManager(this));


        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Toast.makeText(FirstParentReg.this, " selected", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < algorithmItems.size(); i++) {
                            if (position != i) {
                                algorithmItems.get(i).setIm(R.drawable.gradgrey);
                            }
                        }

                        algorithmItems.get(position).setIm(R.drawable.grad);
                        adpt.setItems(algorithmItems);
                        adpt.notifyDataSetChanged();


                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
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

    private void initvp() {

        Call<List<Category>> call = retrofitAPI.getallctg();
        //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> responseFromAPI = response.body();
                dots = new TextView[responseFromAPI.size()];
                //spinner = findViewById(R.id.spinner);
                list = new int[responseFromAPI.size()];
                //list[0] = R.drawable.drftspace;
                //list[1] = R.drawable.anml1;  //getResources().getColor(R.color.red);
                //list[2] = R.drawable.art;
                //list[3] = getResources().getColor(R.color.yellow);
                //list[4] = getResources().getColor(R.color.orange);


                int len = responseFromAPI.size();
                for (int i = 0; i < len; i++)
                    ;
                // list[i] = responseFromAPI.get(i).getImage();                //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(1).getCourseName(), responseFromAPI.get(1).getDay() + " " + responseFromAPI.get(1).getTime() + responseFromAPI.get(1).getLength()));
                //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(2).getCourseName(), responseFromAPI.get(2).getDay() +" "+ responseFromAPI.get(2).getTime() + responseFromAPI.get(2).getLength()));
                //algorithmItems.add(new AlgorithmItem(responseFromAPI.get(3).getCourseName(), responseFromAPI.get(3).getDay() +" "+ responseFromAPI.get(3).getTime() + responseFromAPI.get(3).getLength()));
                //a1=(new AlgorithmItem(responseFromAPI.get(0).getCourseName(), responseFromAPI.get(0).getDay() + responseFromAPI.get(0).getTime() + responseFromAPI.get(0).getLength()));
                //a2=(new AlgorithmItem(responseFromAPI.get(1).getCourseName(), responseFromAPI.get(1).getDay() + responseFromAPI.get(1).getTime() + responseFromAPI.get(1).getLength()));

                //spinner.performClick();
                //adpt.setItems(algorithmItems);
                // adpt.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                int x = 5;
            }
        });

        adapter2 = new SliderAdapter(list);
        imageContainer.setAdapter(adapter2);

        setIndicators();


    }

    // It is used to set the algorithm names to our array list.

    private void initrv() {
        algorithmItems.clear();
        adpt.notifyDataSetChanged();
        Call<List<DataModel>> call = retrofitAPI.getSpacesCourses();


    }

    //    private void initRetrofit() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://unsplash.com/")
//                .build();
//
//        RetroFitAPI2 retrofitInterface = retrofit.create(RetroFitAPI2.class);
//
//        Call<ResponseBody> request = retrofitInterface.downloadImage("photos/YYW9shdLIwo/download?force=true");
//        try {
//
//            downloadImage(request.execute().body());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//    private void downloadImage(ResponseBody body) throws IOException {
//
//        int count;
//        byte data[] = new byte[1024 * 4];
//        long fileSize = body.contentLength();
//        InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);
//        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "journaldev-image-downloaded.jpg");
//        OutputStream outputStream = new FileOutputStream(outputFile);
//        long total = 0;
//        boolean downloadComplete = false;
//        //int totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
//
//        while ((count = inputStream.read(data)) != -1) {
//
//            total += count;
//            int progress = (int) ((double) (total * 100) / (double) fileSize);
//
//
//            //updateNotification(progress);
//            outputStream.write(data, 0, count);
//            downloadComplete = true;
//        }
//        //onDownloadComplete(downloadComplete);
//        outputStream.flush();
//        outputStream.close();
//        inputStream.close();
//
//    }
    private void initList() {
        algorithmItems.clear();
        adpt.notifyDataSetChanged();
        Call<List<DataModel>> call = retrofitAPI.getSpacesCourses();
        //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {

                List<DataModel> responseFromAPI = response.body();
                int len = responseFromAPI.size();
                for (int i = 0; i < len; i++)
                    algorithmItems.add(new AlgorithmItem(responseFromAPI.get(i).getCourseName(), responseFromAPI.get(i).getDay() + " " + responseFromAPI.get(i).getTime() + responseFromAPI.get(i).getLength(), R.drawable.gradgrey));


                adpt.setItems(algorithmItems);
                adpt.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {

            }
        });
    }

    private void initList1() {
        algorithmItems.clear();
        adpt.notifyDataSetChanged();
        Call<List<DataModel>> call = retrofitAPI.getAnimalsCourses();
        //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {

                List<DataModel> responseFromAPI = response.body();
                int len = responseFromAPI.size();
                for (int i = 0; i < len; i++)
                    algorithmItems.add(new AlgorithmItem(responseFromAPI.get(i).getCourseName(), responseFromAPI.get(i).getDay() + " " + responseFromAPI.get(i).getTime() + responseFromAPI.get(i).getLength(), R.drawable.gradgrey));
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

    private void initList2() {
        algorithmItems.clear();
        adpt.notifyDataSetChanged();

        Call<List<DataModel>> call = retrofitAPI.getArtsCourses();
        //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {

                List<DataModel> responseFromAPI = response.body();
                int len = responseFromAPI.size();
                for (int i = 0; i < len; i++)
                    algorithmItems.add(new AlgorithmItem(responseFromAPI.get(i).getCourseName(), responseFromAPI.get(i).getDay() + " " + responseFromAPI.get(i).getTime() + responseFromAPI.get(i).getLength(), R.drawable.gradgrey));
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

    private void selectedDots(int position) {
        for (int i = 0; i < dots.length; i++) {
            if (i == position) {
                dots[i].setTextColor(list[position]);
            } else {
                dots[i].setTextColor(getResources().getColor(R.color.grey));
                //changed from gray
            }
        }
    }

    private void setIndicators() {
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(18);
            layout.addView(dots[i]);
        }
    }


    private void initListgen(String s) {
        algorithmItems.clear();
        adpt.notifyDataSetChanged();
        // LogInInfo logInInfo=new LogInInfo("user","password");
        //  AuthorizationsCall authorizationsCall=new AuthorizationsCall(getString(R.string.BASE_URL),logInInfo);

        Call<List<Course4>> call = retrofitAPI.getcatcourses(s);
        //algorithmItems.add(new AlgorithmItem("choose course", "",R.color.white));
        call.enqueue(new Callback<List<Course4>>() {
            @Override
            public void onResponse(Call<List<Course4>> call, Response<List<Course4>> response) {

                List<Course4> responseFromAPI = response.body();
                int len = 0;
                if (responseFromAPI != null)
                    len = responseFromAPI.size();
                for (int i = 0; i < len; i++)
                    algorithmItems.add(new AlgorithmItem(responseFromAPI.get(i).getName(), responseFromAPI.get(i).getDay() + ", " + responseFromAPI.get(i).getStartHour() + "+ " + responseFromAPI.get(i).getMeetingDuration() /*+ responseFromAPI.get(i).getd()*/, R.drawable.gradgrey));
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
            public void onFailure(Call<List<Course4>> call, Throwable t) {

            }
        });

    }


}
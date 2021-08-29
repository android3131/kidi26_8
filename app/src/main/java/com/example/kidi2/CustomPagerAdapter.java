package com.example.kidi2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;


    /*
    ArrayList<AlgorithmItem>  algorithmItems=new ArrayList<AlgorithmItem>();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8090/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
    // create an instance for our retrofit api class.
    RetroFitAPI retrofitAPI = retrofit.create(RetroFitAPI.class);

*/
    public CustomPagerAdapter(Context mContext)//, ArrayList<AlgorithmItem> a1) {
    {
        this.mContext = mContext;
        //this.algorithmItems=a1;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ModelObject modelObject= ModelObject.values()[position];
        LayoutInflater inflater=LayoutInflater.from(mContext);
        ViewGroup layout= (ViewGroup)inflater.inflate(modelObject.getId2(),container, false);
        container.addView(layout);
       /* algorithmItems = new ArrayList<AlgorithmItem>();
        Call<List<DataModel>> call = retrofitAPI.getString();
        algorithmItems.add(new AlgorithmItem("",""));
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if(position==0) {
                    List<DataModel> responseFromAPI = response.body();
                    algorithmItems.add(new AlgorithmItem(responseFromAPI.get(0).getCourseName(), responseFromAPI.get(0).getDay() + " " + responseFromAPI.get(0).getTime() + responseFromAPI.get(0).getLength()));
                    algorithmItems.add(new AlgorithmItem(responseFromAPI.get(1).getCourseName(), responseFromAPI.get(1).getDay() + " " + responseFromAPI.get(1).getTime() + responseFromAPI.get(1).getLength()));
                    algorithmItems.add(new AlgorithmItem(responseFromAPI.get(2).getCourseName(), responseFromAPI.get(2).getDay() + " " + responseFromAPI.get(2).getTime() + responseFromAPI.get(2).getLength()));
                    algorithmItems.add(new AlgorithmItem(responseFromAPI.get(3).getCourseName(), responseFromAPI.get(3).getDay() + " " + responseFromAPI.get(3).getTime() + responseFromAPI.get(3).getLength()));
                    //a1=(new AlgorithmItem(responseFromAPI.get(0).getCourseName(), responseFromAPI.get(0).getDay() + responseFromAPI.get(0).getTime() + responseFromAPI.get(0).getLength()));
                    //a2=(new AlgorithmItem(responseFromAPI.get(1).getCourseName(), responseFromAPI.get(1).getDay() + responseFromAPI.get(1).getTime() + responseFromAPI.get(1).getLength()));
                }

            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {

            }
        });
        */


        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return ModelObject.values().length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    public  CharSequence getPageTitle(int position){
        ModelObject m1= ModelObject.values()[position];
        return mContext.getString(m1.getId1());
    }
}

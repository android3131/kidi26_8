package com.example.kidi2;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kidi2.DataKid;
import com.example.kidi2.RetrofitAPIThirdParentReg;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThirdParentRegActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button switchToThirdActivity;
    Button backButton;
    CheckedTextView box;
    private final int GALLERY = 1;
    private static final String IMAGE_DIRECTORY = "/myapp_upload_gallery";
    EditText etFullName;
    Spinner etDayBirth;
    Spinner etMonthBirth;
    Spinner etYearBirth;
    Gender etGender;
    Button postDataBtn;

    boolean flag=false;

    Retrofit retrofit ;

    RetrofitAPIThirdParentReg retrofitAPI ;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_parent_reg);
         pref = getApplicationContext().getSharedPreferences("MyKIDIPref", 0); // 0 - for private mode
         editor = pref.edit();

         retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL)
                )
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();

         retrofitAPI = retrofit.create(RetrofitAPIThirdParentReg.class);

        backButton = findViewById(R.id.fourth_back_button);

        backButton.setOnClickListener(view -> finish());
        addSpinners();
        // initializing our views
//        Picasso.with(getApplicationContext())
//                .load("https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/slideshows/is_my_cat_normal_slideshow/1800x1200_is_my_cat_normal_slideshow.jpg")
//                .into((ImageView)findViewById(R.id.imageView2));
        etFullName = ((EditText)findViewById(R.id.editTextTextPersonName));
        etFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String inputText= etFullName.getText().toString();
                ((TextView)findViewById(R.id.editTextKid)).setText(inputText);

            }
        });
        etDayBirth = ((Spinner)findViewById(R.id.day_spinner));
        etMonthBirth = ((Spinner)findViewById(R.id.month_spinner));
        etYearBirth = ((Spinner)findViewById(R.id.year_spinner));
        ImageView newImage = (ImageView)findViewById(R.id.imageView2);
        newImage.setImageResource(R.drawable.girl);
        postDataBtn = findViewById(R.id.fourth_next_button);

        //getName("reem");

        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box = findViewById(R.id.girl_box);
                etGender=Gender.Girl;
                if(!box.isChecked()){
                    box = findViewById(R.id.boy_box);
                    etGender=Gender.Boy;
                    if(!box.isChecked()){
                        box = findViewById(R.id.notrelevant_box);
                        etGender=Gender.NotRelevant;
                    }
                }
             //   etGender = box.getText().toString();
                /*System.out.println("wawa" + etFullName.getText().toString());
                System.out.println("wawa" +etGender);
                System.out.println("wawa" + etDayBirth);*/
                // validating if the text field is empty or not.
                if (etFullName.getText().toString().isEmpty()
                        || etDayBirth.getSelectedItem().toString().isEmpty()
                        || etMonthBirth.getSelectedItem().toString().isEmpty()
                        || etYearBirth.getSelectedItem().toString().isEmpty()
                        || etGender==null) {
                    Toast.makeText(ThirdParentRegActivity.this, "Please enter all the values", Toast.LENGTH_SHORT).show();
                    return;
                }

                // calling a method to post our new car's data
                postData(etFullName.getText().toString(),
                        etDayBirth.getSelectedItem().toString(),
                        etMonthBirth.getSelectedItem().toString(),
                        etYearBirth.getSelectedItem().toString(),
                        etGender
                );
                //startActivity(new Intent(ThirdParentRegActivity.this, ForthParentReg.class));
            }
        });

        //-------------------
        findViewById(R.id.photoField).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGallery();
            }
        });
        //-------------------
    }

    private void addSpinners() {
        List<String> dayList = new ArrayList<>();
        List<String> monthList = new ArrayList<>();
        List<String> yearList = new ArrayList<>();
        Spinner daySpinner = findViewById(R.id.day_spinner);
        Spinner monthSpinner = findViewById(R.id.month_spinner);
        Spinner yearSpinner = findViewById(R.id.year_spinner);

        dayList.add("Day");
        monthList.add("Month");
        yearList.add("Year");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i=1; i<32; i++){
            DecimalFormat formatter = new DecimalFormat("00");
            String aFormatted = formatter.format(i);
            dayList.add(aFormatted);
        }
        for(int i=1; i<13;i++){
            DecimalFormat formatter = new DecimalFormat("00");
            String aFormatted = formatter.format(i);
            monthList.add(aFormatted);

        }
        for(int i=2000; i<=thisYear; i++){
            yearList.add(Integer.toString(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner,dayList);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, R.layout.style_spinner, monthList);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, R.layout.style_spinner, yearList);
        dayAdapter.setDropDownViewResource(R.layout.style_spinner);

        daySpinner.setAdapter(dayAdapter);
        monthSpinner.setAdapter(monthAdapter);
        yearSpinner.setAdapter(yearAdapter);
        daySpinner.setPrompt("day");
//        daySpinner.setOnItemClickListener(this);
//        monthSpinner.setOnItemClickListener(this);
//        yearSpinner.setOnItemClickListener(this);


    }

    public void setCheckedButton(View view) {
        ImageView newImage = (ImageView)findViewById(R.id.imageView2);
        CheckedTextView boy_box = findViewById(R.id.boy_box);
        CheckedTextView girl_box = findViewById(R.id.girl_box);
        CheckedTextView notrelevant_box = findViewById(R.id.notrelevant_box);
        CheckedTextView box = findViewById(view.getId());
        if (box.isChecked()) {
            box.setChecked(false);
        } else if (!box.isChecked()){
            box.setChecked(true);
        }
        if(boy_box.isChecked() && girl_box.isChecked()){
            if(view.getId()==boy_box.getId()){
                girl_box.setChecked(false);
            }
            if(view.getId()==girl_box.getId()){
                boy_box.setChecked(false);
            }
        }
        if(girl_box.isChecked() && notrelevant_box.isChecked()){
            if(view.getId()==girl_box.getId()){
                notrelevant_box.setChecked(false);
            }
            if(view.getId()== notrelevant_box.getId()){
                girl_box.setChecked(false);
            }
        }
        if(boy_box.isChecked() && notrelevant_box.isChecked()){
            if(view.getId()==boy_box.getId()){
                notrelevant_box.setChecked(false);
            }
            if(view.getId()== notrelevant_box.getId()){
                boy_box.setChecked(false);
            }
        }

      if(!flag) {

          if (((CheckedTextView) findViewById(R.id.boy_box)).isChecked()) {
              newImage.setImageResource(R.drawable.bitmap);
          }

          if (((CheckedTextView) findViewById(R.id.notrelevant_box)).isChecked()) {
              newImage.setImageResource(R.drawable.ellipse_187);
          }

          if (((CheckedTextView) findViewById(R.id.girl_box)).isChecked()) {
              newImage.setImageResource(R.drawable.girl);
          }
      }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void postData(String fullName, String dayBirth, String monthBirth, String yearBirth,Gender gender) {
        String date = yearBirth + "-" + monthBirth + "-" + dayBirth;
        Date date2=new Date();
        try {
            date2 = new SimpleDateFormat("yyyy-mm-dd").parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            System.out.println("parsing date failed");
            e.printStackTrace();
        }
        // display our progress bar.
        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        if(gender) {
//            gender = gender.replace("A boy", "Boy");
//        }
//        else if(gender.equals("A girl")) {
//            gender = gender.replace("A girl", "Girl");
//        }
//        else{
//            gender = gender.replace("Not relevant","NotRelevant");
//        }
        // create retrofit builder and pass our base url
        // create an instance for our retrofit api class.
        // passing data from our text fields to our modal class.
        String parentID =pref.getString("parentIDReg",null);
        Kid myKidData = new Kid(fullName,date2,gender, parentID);
        // calling a method to create a post and passing our model class.
        Call<Kid> call = retrofitAPI.createPost(myKidData);

        // add the http request to queue
        call.enqueue(new Callback<Kid>() {
            @Override
            public void onResponse(Call<Kid> call, Response<Kid> response) {
                // this method is called when we get response from our api.
                Toast.makeText(ThirdParentRegActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // hiding our progress bar.

                // setting edit text to empty text
                ((EditText) findViewById(R.id.editTextTextPersonName)).setText("");
                ((Spinner) findViewById(R.id.day_spinner)).setSelection(0);
                ((Spinner) findViewById(R.id.month_spinner)).setSelection(0);
                ((Spinner) findViewById(R.id.year_spinner)).setSelection(0);
                ((CheckedTextView) findViewById(R.id.girl_box)).setChecked(true);
                ((CheckedTextView) findViewById(R.id.boy_box)).setChecked(false);
                ((CheckedTextView) findViewById(R.id.notrelevant_box)).setChecked(false);

                // getting response from our body
                // and passing it to our model class.
                Kid responseFromAPI = response.body();
//                if (responseFromAPI != null) {
//                    DataKid dk = responseFromAPI.get(0);
//                    System.out.println("reem" + response.code());
//                    // getting our data from model class and adding it to our string.
//                    String responseString = "Response Code : " + response.code() +
//                            "\nFull Name : " + dk.getFullName() +
//                            "Birth Date : " + dk.getDateOfBirth().toString().substring(0, 11) +
//                            "Gender : " + dk.getGender().toString();
//
//                    Toast.makeText(ThirdParentRegActivity.this, "The new Kid: " + responseString, Toast.LENGTH_SHORT).show();
//
//                    //String responseString = "Response Code : " + response.code() + "\n" + response.body();
//
//                    // setting responseString string to our text view
//
//                }
                startActivity(new Intent(ThirdParentRegActivity.this, ForthParentReg.class));
            }
            @Override
            public void onFailure(Call<Kid> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                System.out.println("avital" + t);
                Toast.makeText(ThirdParentRegActivity.this, "Failed!:(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getName(String name){
        Call<DataKid> call = retrofitAPI.createGet(name);
        call.enqueue(new Callback<DataKid>(){

                         @Override
                         public void onResponse(Call<DataKid> call, Response<DataKid> response) {
                             System.out.println(response.code());
                             System.out.println(response.body());

                         }

                         @Override
                         public void onFailure(Call<DataKid> call, Throwable t) {
                             System.out.println("avital + ERROR");
                         }
                     });
    }
    //=========================================
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("avital", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    //=============================================================================================
    //=============================================================================================
    //=============================================================================================
    private void startGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }
    //=============================================================================================
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {

            return;
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    // This way is deprecated now:
                    // postImageAsMultipartBody("/storage/emulated/0/myapp_upload_gallery");
                    //  use the following:
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveMyImg(bitmap);
                    postImageAsMultipartBody(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ThirdParentRegActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //================================================
    public String saveMyImg(Bitmap myBitmap)
    {

        String myName = "file";
        String myMimeType = "image/jpeg";
        ContentResolver resolver = this.getContentResolver();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, myName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, myMimeType);
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        // Primary directory Download not allowed for content://media/external/images/media; allowed directories are [DCIM, Pictures]
        // folderPath = mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + File.separator + "appFolderName";
        // change to
        String folderPath = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator + "appFolderName";

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        Glide.with(this)
//                .load(bytes.toByteArray())
//                .apply(RequestOptions.circleCropTransform())
//                .into(((ImageView)findViewById(R.id.imageView2)));
        ((ImageView)findViewById(R.id.imageView2)).setImageBitmap(myBitmap);
        flag=true;

        File wallpaperDirectory = new File(folderPath);

        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("avital", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    //================================================
    //==============================================
    private void postImageAsMultipartBody(String path){
        String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
        Retrofit retrofit = new Retrofit.Builder()
                //  .baseUrl("https://examples-and-exercises.herokuapp.com/android/profile/")
                // ///http://localhost:8082/spring-rest/fileserver/singlefileupload/
                // .baseUrl("http://10.0.2.2:2255/")
                .baseUrl(getString(R.string.BASE_URL)
                )

                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();
        // create an instance for our retrofit api class.
        RetrofitAPIThirdParentReg retrofitAPI = retrofit.create(RetrofitAPIThirdParentReg.class);

        //Create a file object using file path
        File file = new File(path);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), imgname);


        // calling a method to create a post and passing our model class.
        Call<Object> call = retrofitAPI.uploadImage(fileToUpload, filename);


        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("avital", response.body().toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Toast.makeText(getApplicationContext(), jsonObject.getString("myInfo"), Toast.LENGTH_SHORT).show();

                    //jsonObject.toString().replace("\\\\","");

                    // if (jsonObject.getString("status").equals("true")) {

//                        JSONArray dataArray = jsonObject.getJSONArray("data");
//
//                        String url = "";
//                        for (int i = 0; i < dataArray.length(); i++) {
//                            JSONObject dataobj = dataArray.getJSONObject(i);
//                            url = dataobj.optString("pathToFile");
//                        }
                    /////////// Picasso.get().load(url).into(imageView);
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("avital onFailure", call.toString() + "---" + t);
            }
        });

    }
    //==============================================
}
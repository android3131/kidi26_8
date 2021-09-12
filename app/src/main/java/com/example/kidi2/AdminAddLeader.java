package com.example.kidi2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminAddLeader extends AppCompatActivity {


    /*********************Building retrofit********************************/

    Retrofit retrofit ;

    RetrofitAPIGrp2AdminLeader retrofitAPI ;

    /*********************Variables Define - XML- ********************************/

    private final int GALLERY = 1;
    private static final String IMAGE_DIRECTORY = "/myapp_upload_gallery";

    EditText fullName,email,phoneNumber,streetAddress,state,city,country,zip;
    Spinner areaCode;
    Button AddLeader,updateLeader;
    Button addProfilePicture;
    EditText leaderDate;
    String leaderBirthday;
    Date myBirthDate;

    /**********************Variables To Define for usage in the flow*******************************/

    private ArrayList<Category> categoryArrayList;
    private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_leader);
       retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();

         retrofitAPI = retrofit.create(RetrofitAPIGrp2AdminLeader.class);

        /****************assigning data from xml to java******************/
        fullName = findViewById(R.id.leaderName);
        email = findViewById(R.id.leaderEmail);
        areaCode = findViewById(R.id.spinner);
        phoneNumber = findViewById(R.id.leaderPhone);
        streetAddress = findViewById(R.id.leaderStreetAddress);
        state = findViewById(R.id.leaderState);
        city = findViewById(R.id.leaderCity);
        country = findViewById(R.id.leaderCountry);
        zip =findViewById(R.id.countryZip);
        addProfilePicture = findViewById(R.id.addImageButton);
        recyclerView = findViewById(R.id.recyclerViewer);
        AddLeader = findViewById(R.id.addLeader);
        updateLeader = findViewById(R.id.updateLeader);
        leaderDate = findViewById(R.id.leaderBirthday);
        leaderDate.setInputType(InputType.TYPE_NULL);


        /****************initialize data******************/
        setCategoryData();
        setAdapter();

        /**************** dding the calender view ******************/

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select A Date");
        MaterialDatePicker materialDatePicker = builder.build();
        leaderDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
            }
        });

        /*********************** saving the data from the ui for birth ******************************/

        Date myDate;
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                TimeZone timeZoneUTC = TimeZone.getDefault();
                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

                leaderDate.setText(simpleFormat.format(selection));

                leaderBirthday = simpleFormat.format(selection);
                try {
                    Date myDate = (Date) simpleFormat.parse(leaderBirthday);
                    myBirthDate = myDate;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        /**************** adding the profile picture -picture chooser- ******************/
        addProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGallery();

            }
        });

        /**************** initialize the Address from entered text ******************/

        //Address(String streetAddress, String zipCode, String city, String state, String country

        String streetAdd, zipCode,cityName,stateName,countryName;
        streetAdd = streetAddress.getText().toString();
        zipCode = zip.getText().toString();
        cityName = city.getText().toString();
        stateName = state.getText().toString();
        countryName = country.getText().toString();

        Address address = new Address(streetAdd,zipCode,cityName,stateName,countryName);

        /**************** initialize the variable from entered text ******************/

        String name,emailAddress,number, profilePicture;

        name = fullName.getText().toString();
        emailAddress = email.getText().toString();
        number = phoneNumber.getText().toString();
        profilePicture = "myPicture.jpeg";

        Leader leader = new Leader(name,emailAddress,number,address,myBirthDate,profilePicture);


        /**************** adding the leader to the database ******************/

        AddLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Leader> call = retrofitAPI.addLeader(leader);
                call.enqueue(new Callback<Leader>() {
                    @Override
                    public void onResponse(Call<Leader> call, Response<Leader> response) {
                        // this method is called when we get response from our api.
                        //Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        Leader responseFromAPI = response.body();
                        Toast.makeText(AdminAddLeader.this, "Leader added", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Leader> call, Throwable t) {

                    }
                });
            }
        });


        /**************** updating the leader to the database ******************/

        updateLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });






    }


    /******************* start image file chooser ******************************/
    private void startGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }


    /********************** choosing the image to save it ***************************/
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
                    Toast.makeText(AdminAddLeader.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /************************** getting selected item from image chooser to save image ******************************************/
    public String saveMyImg(Bitmap myBitmap)
    {

        String myName = "file";
        String myMimeType = "image/jpeg";
        ContentResolver resolver = this.getContentResolver();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, myName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, myMimeType);
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        String folderPath = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator + "appFolderName";

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

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
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    /********************** getting the image path **************************/
    private void postImageAsMultipartBody(String path){
        String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.valueOf(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPIGrp2AdminLeader retrofitAPI = retrofit.create(RetrofitAPIGrp2AdminLeader.class);

        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), imgname);


        Call<Object> call = retrofitAPI.uploadImage(fileToUpload, filename);


        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Toast.makeText(getApplicationContext(), jsonObject.getString("myInfo"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
    }

    /*************** adding the category names ***********************/
    private void setCategoryData() {

        categoryArrayList = new ArrayList<Category>();
        categoryArrayList.add(new Category("Arts"));
        categoryArrayList.add(new Category("Animals"));
        categoryArrayList.add(new Category("Spaces"));
        categoryArrayList.add(new Category("Science"));
    }

    /*************** setting the category names in the adapter ***********************/
    private void setAdapter() {

        recyclerAdapter adapter = new recyclerAdapter(categoryArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
package com.example.kidi2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ForthParentReg extends AppCompatActivity {
    CardView zoomLink;
    String zoomLinkString;
    Course course;
    RetroFitAPIForthParent retrofitAPI;
    Date date;
    String ids = "111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL)
                )
                // when sending data in json format we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // and build our retrofit builder.
                .build();
        // create an instance for our retrofit api class.
        retrofitAPI = retrofit.create(RetroFitAPIForthParent.class);

        // create retrofit builder and pass our base url.
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Get the courseID
        myEdit.putString("courseID", "zoomLinkTestCheck");
//        myEdit.commit();
//        zoomLinkString = sharedPreferences.getString("testZoomLink","");
        // Get the course ID.
        String courseIDBySharedPreferences = "courseID";
        getByID(courseIDBySharedPreferences);
        System.out.println("The value of shared : " + zoomLinkString);
        setContentView(R.layout.activity_forth_parent_reg2);
        zoomLink = findViewById(R.id.zoomLinkCard);
        System.out.println("Entered here");
    }

    public void addZoomLink() {
        // Get the link from shared preferences.
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", zoomLinkString);
        // Copy to clipboard.
        clipboard.setPrimaryClip(clip);
    }

    public void ZoomClick(View v) {

        addZoomLink();
        // Make a Toast to notify the user that the link was copied.
        Toast.makeText(getApplicationContext(), "Zoom link was copied to clipboard", Toast.LENGTH_LONG).show();
    }

    public void goToHomeScreen(View v) {
        startActivity(new Intent(ForthParentReg.this, LogInScreenActivity.class));
    }


    public void addToCalendar(View v) {
        addZoomLink();
        System.out.println("Entered the event");
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "Course Name"); // Need to change it with course.
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "online course via zoom");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "(Course Name with + leader name) \n" + "Zoom link : " + zoomLinkString);
        intent.putExtra(CalendarContract.Events.ALL_DAY, "true");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;UNTIL=20300116");
        System.out.println("Got the end of the event");
        startActivity(intent);
        System.out.println("started the event");
    }


    private void getByID(String ids) {

        // We can Get the zoom link using the shared preferences
        Call<Course> call = retrofitAPI.getByID(ids);
        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {

                course = response.body();
//                zoomLinkString = course.getZoomMeetingLink();
//                date = course.getStartDateTime();


            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                System.out.println("The error is " + t);
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void openWebView(View v) {
        startActivity(new Intent(ForthParentReg.this, webViewBrowser.class));
    }


    public void sendEmail(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Zoom Link Bellow:");
        i.putExtra(Intent.EXTRA_TEXT   , zoomLinkString);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ForthParentReg.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
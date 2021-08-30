package com.example.kidi2;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


public class KidiOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Log.d("ofra","you chose " + position + " " + id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}




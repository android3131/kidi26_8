package com.example.kidi2;

import android.os.Bundle;
import android.widget.CheckBox;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomCheckbox extends Addactivity implements OnClickListener {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_item);

        CheckBox checkb  = ( CheckBox ) findViewById( R.id.checkbox);
        checkb.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}

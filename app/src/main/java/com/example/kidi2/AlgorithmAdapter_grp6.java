package com.example.kidi2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlgorithmAdapter_grp6 extends ArrayAdapter<AlgorithmItem_grp6> {

  
    public AlgorithmAdapter_grp6(Context context,
                                 ArrayList<AlgorithmItem_grp6> algorithmList)
    {
        super(context, 0, algorithmList);



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.algorithm_spinner, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.text_view);
        TextView textViewName2 = convertView.findViewById(R.id.textView2);
        CheckedTextView check =convertView.findViewById(R.id.checkBox);
        AlgorithmItem_grp6 currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getAlgorithmName());
            textViewName2.setText(currentItem.getElse_information());
            check.setChecked(currentItem.getCheck());
        }
        return convertView;
    }
}

package com.example.kidi2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;




public class TermsOfService extends AppCompatDialogFragment {

    //private DialogListener listener;
    private EditText editTextEmail;
    private Context ctx;
    TermsOfService(Context ctx){
        this.ctx=ctx;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.terms_service,null);
        builder.setView(view)


                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {




                        //listener.applyTexts(Email);
                    }
                });

        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }








    }





package com.example.kidi2;

import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.Date;

public class KidCheckBox {
    private Kid kid;
   private CheckedTextView checkBox;
   private boolean Ischecked;


    public boolean isIschecked() {
        return Ischecked;
    }

    public KidCheckBox(){}

    public KidCheckBox(Kid kid, Boolean Ischecked) {
        this.Ischecked=false;
        this.kid = kid;
    }

    public Kid getKid() {
        return kid;
    }

    public void setKid(Kid kid) {
        this.kid = kid;
    }

    public void setCheckBoxID(int id){
        checkBox.setId(id);
    }

    public void setCheckBox(CheckedTextView checkBox) {
        this.checkBox = checkBox;
    }

    public CheckedTextView getCheckBox() {
        return checkBox;
    }

    public void setIschecked(boolean ischecked) {
        Ischecked = ischecked;
    }
}

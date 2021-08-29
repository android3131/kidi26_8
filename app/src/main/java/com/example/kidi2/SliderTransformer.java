package com.example.kidi2;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;

class SliderTransformer implements ViewPager2.PageTransformer {
    private int offscreenPageLimit=3;


private Float DEFAULT_TRANSLATION_X=.0f;
private Float DEFAULT_TRANSLATION_FACTOR=1.2f;

private Float SCALE_FACTOR=.14f;
private Float DEFAULT_SCALE=1f;

private Float ALPHA_FACTOR=.3f;
private Float DEFAULT_ALPHA=1f;

    Float translationX;
    Float  scaleX;
    Float scaleY;
    Float alpha ;

SliderTransformer(View page,float position){
    transformPage(page,position);
}



    @Override
    public void transformPage( View page, float position) {

        ViewCompat.setElevation(page,-Math.abs(position));

        Float scaleFactor=-SCALE_FACTOR*position+DEFAULT_SCALE;
        Float alphaFactor=-ALPHA_FACTOR*position+DEFAULT_ALPHA;



        if(position==0f){
            Float translationX=DEFAULT_TRANSLATION_X;

             scaleX=DEFAULT_SCALE;
             scaleY=DEFAULT_SCALE;
             alpha=DEFAULT_ALPHA+position ;
        }


        if(position<=offscreenPageLimit-1&&position>0){
            scaleX=scaleFactor;

            scaleY=scaleFactor;
            translationX=-(page.getWidth()/DEFAULT_TRANSLATION_FACTOR)*position*(1.175f);
              alpha=alphaFactor ;
        }



        else{
            translationX=DEFAULT_TRANSLATION_X;
            scaleX=DEFAULT_SCALE;
            scaleY=DEFAULT_SCALE;
            alpha=DEFAULT_ALPHA;
        }
        page.setAlpha(alpha);
        page.setScaleX(scaleX);
        page.setScaleY(scaleY);
        page.setTranslationX(translationX);

    }
}





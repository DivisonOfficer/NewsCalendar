package edu.skku.cs.mysimplecalendar.activity.main;

import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SlideModifier implements View.OnTouchListener {
    private View lView;
    private View slider;
    private View rootView;
    private Integer minHeight, maxHeight;
    public SlideModifier(View rootView, View view, View slider, Integer minHeight, Integer maxHeight)
    {
        this.rootView = rootView;
        this.lView = view;
        this.slider = slider;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        setTouch();
    }

    private void setTouch(){
        slider.setOnTouchListener(this);
    }

    Float prey = 0f;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            prey = motionEvent.getRawY();
            return true;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE)
        {
            Float dis =  prey - motionEvent.getRawY();
            ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams) lView.getLayoutParams();
            param.height = Integer.max(Integer.min((int) (param.height + dis),maxHeight),minHeight);
            lView.setY(rootView.getHeight() - param.height);
            lView.post(() -> {
                lView.setLayoutParams(param);

              //  rootView.requestLayout();
                //view.requestLayout();

                Log.d("SlideModifer","onTouch" + param.height + "/ " + view.getY());
            });
            prey = motionEvent.getRawY();
            return true;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP)
        {

            return true;
        }

        return false;
    }
}

package edu.skku.cs.mysimplecalendar.activity.main;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
        initAnimator();
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
            //lView.setY(rootView.getHeight() - param.height);
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
            startAnimator();

            return true;
        }

        return false;
    }

    boolean isOpen = false;
    ValueAnimator animator = ObjectAnimator.ofFloat(0f,1f);

    private void startAnimator(){
        if(animator.isRunning()) return;
        fromHeight = lView.getHeight();
        if(fromHeight > maxHeight *( 0.3 + (isOpen ? 0.5 : 0))) {
            isOpen = true;
            toHeight = maxHeight;
        }
        else {
            isOpen = false;
            toHeight = minHeight;
        }
        animator.start();
    }

    private void initAnimator(){
        animator.setDuration(100);
        animator.addUpdateListener(anim->{
            updateHeight(anim.getAnimatedFraction());
        });
    }
    Integer fromHeight;
    Integer toHeight;
    private void updateHeight(Float ratio){
        ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams)lView.getLayoutParams();
        param.height = (int) (fromHeight + (toHeight - fromHeight) * (ratio));
        lView.post(()->{

           lView.setLayoutParams(param);
        });
    }

}

package edu.skku.cs.mysimplecalendar.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class PlanSlideUtil {

    View ll;
    View icon;
    Integer maxWidth;
    RecyclerView rv;
    ScrollControlLinear manager;
    Listener listener;
    public PlanSlideUtil(View ll, View icon, RecyclerView rv,Listener listener)
    {
        this.ll = ll;
        this.icon = icon;
        maxWidth = icon.getWidth();
        this.listener = listener;
        setAnimator();
        this.rv = rv;
        this.manager = (ScrollControlLinear)rv.getLayoutManager();
        setOnTouch();
    }

    Float preX = 0f;
    Float oldX = 0f;

    Float fromX;

    private void setOnTouch(){
        ll.setOnTouchListener((view, motionEvent) ->

        {

            Log.d("PlanSlider","onTouch"+motionEvent.getAction() + "/"+motionEvent.getRawX());

            Float x = motionEvent.getRawX();

            switch(motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN :
                    preX = x;
                    oldX = x;

                        return true;

                case MotionEvent.ACTION_MOVE :

                    Float newX = ll.getX();
                    newX += (x - preX) ;
                    Log.d("PlanSlider","newX : "+newX + "XAdd" + (x-preX));
                    if(newX < 0f) newX = 0f;
                    else if(newX > icon.getWidth()) newX = (float)icon.getWidth();
                    if(newX > 10) manager.setScrollable(false);
                    Float finalNewX = newX;
                    ll.post(()->{
                       ll.setX(finalNewX);
                    });

                    preX = x;
                    return true;


                case MotionEvent.ACTION_UP:


                    fromX = ll.getX();
                    animator.start();
                    manager.setScrollable(true);
                    if(fromX < 10)
                    {
                        return ll.performClick();
                    }
                    if(fromX >= icon.getWidth() - 10)
                    {
                        listener.action();
                        return true;
                    }
                    return true;
                case MotionEvent.ACTION_CANCEL:
                    fromX = ll.getX();
                    //manager.setScrollable(true);
                    //animator.start();
                    break;
            }


            return true;
        }
                );
    }

    ValueAnimator animator = ObjectAnimator.ofFloat(1f,0f);
    private void setAnimator(){
        animator.addUpdateListener(animator->{
            ll.setX(fromX * (1f-animator.getAnimatedFraction()));
        });
        animator.setDuration(100);
    }


    public interface Listener{
        void action();
    }
}

package edu.skku.cs.mysimplecalendar.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

public class ScrollControlLinear extends LinearLayoutManager {

    private boolean isScrollable = true;

    public void setScrollable(boolean scroll)
    {
        isScrollable = scroll;
    }

    public ScrollControlLinear(Context context) {
        super(context);
    }

    public ScrollControlLinear(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public ScrollControlLinear(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollable && super.canScrollVertically() ;
    }
}

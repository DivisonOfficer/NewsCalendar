package edu.skku.cs.mysimplecalendar.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;

public class StaticGrid extends GridLayoutManager {
    public StaticGrid(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public StaticGrid(Context context, int spanCount) {
        super(context, spanCount);
    }

    public StaticGrid(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}

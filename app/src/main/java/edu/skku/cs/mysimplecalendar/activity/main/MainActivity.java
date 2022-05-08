package edu.skku.cs.mysimplecalendar.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Resources;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.activity.BaseActivity;
import edu.skku.cs.mysimplecalendar.databinding.ActivityMainBinding;
import edu.skku.cs.mysimplecalendar.fragment.BottomPlanAdapter;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    MainViewModel viewModel;

    ActivityMainBinding binding;

    CalendarAdapter adapter = new CalendarAdapter(this::setCurrentDate);

    BottomPlanAdapter newsAdapter = new BottomPlanAdapter();





    /************************************************************************
     * APP LIFECYCLE
     *************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = bind(R.layout.activity_main);

        binding.setAdapter(adapter);
        binding.setNewsAdapter(newsAdapter);
        setOnClick();
        observeData();
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(this::setViewSlider);
        viewModel.getDailyNews();
    }


    private void setCurrentDate(Integer date)
    {
        viewModel.setCurrentDay(date);

    }


    private void setOnClick(){
        binding.btMonthNext.setOnClickListener(this);
        binding.btMonthPrevious.setOnClickListener(this);
    }

    private void observeData(){
        viewModel.currentDay().observe(this, day->{
            adapter.setCurrentDate(day);
        });
        viewModel.currentYear().observe(this,year->{
            adapter.setYear(year);
            binding.setYear(String.valueOf(year) + "년");
        });
        viewModel.currentMonth().observe(this,month->{
            adapter.setMonth(month);
            binding.setMonth(String.valueOf(month) + "월");
        });
        viewModel.newsList().observe(this,list->{
            newsAdapter.updateList(list);
        });

    }
    SlideModifier modifier;

    private void setViewSlider(){
        if(modifier != null) return;
        Integer height = Resources.getSystem().getDisplayMetrics().heightPixels;
        modifier = new SlideModifier(binding.getRoot(),binding.llBottomRv,binding.flSlider,(int) (height * 0.3), binding.getRoot().getHeight() - binding.layoutHead.getHeight());
    }

    @Override
    public void onClick(View view) {
        if(view == binding.btMonthNext)
        {
            viewModel.nextMonth();
        }
        else if(view== binding.btMonthPrevious)
        {
            viewModel.previousMonth();
        }
    }
}
package edu.skku.cs.mysimplecalendar.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Resources;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.activity.BaseActivity;
import edu.skku.cs.mysimplecalendar.databinding.ActivityMainBinding;
import edu.skku.cs.mysimplecalendar.fragment.BottomPlanAdapter;
import edu.skku.cs.mysimplecalendar.fragment.NewsScrapDialog;

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

        newsAdapter.setRecyclerView(binding.rvNews);
        binding.setAdapter(adapter);
        binding.setNewsAdapter(newsAdapter);
        newsAdapter.setScrapListener(data->{
            viewModel.setDataOnScrap(data);
            showScrap();
        });
        setLinkClick();
        setOnClick();
        setOnScrap();
        observeData();
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(this::setViewSlider);
        viewModel.getDailyNews();
    }

    private void setLinkClick(){
        newsAdapter.setLinkClick(news->{
            openWebView(news.title,news.url);
        });
    }


    private void setCurrentDate(Integer date)
    {
        viewModel.setCurrentDay(date);

    }

    private void setOnScrap(){
        scrapDialog.onScrap = category->{
            toast(getString(R.string.success_scrap));
            viewModel.scrapWithCategory(category);
        };
    }

    private void setOnClick(){
        binding.btMonthNext.setOnClickListener(this);
        binding.btMonthPrevious.setOnClickListener(this);
    }

    private void observeData(){
        viewModel.currentDay().observe(this, day->{
            adapter.setCurrentDate(day);
        });
        viewModel.currentMonth().observe(this,month->{
            adapter.setMonth(month);
            binding.setMonth(getResources().getStringArray(R.array.month_string)[month - 1]);
        });
        viewModel.newsList().observe(this,list->{
            newsAdapter.updateList(list);
        });

        viewModel.scrapList().observe(this,list->{

            ArrayList<Pair<Integer,String>> ovals = new ArrayList<>();
            list.stream().map(data-> new Pair<Integer,String>(data.day(), data.localCategory)).forEach(ovals::add);
            adapter.setOvals(ovals);
        });

    }
    SlideModifier modifier;

    private void setViewSlider(){
        if(modifier != null) return;
        Integer height = Resources.getSystem().getDisplayMetrics().heightPixels;
        modifier = new SlideModifier(binding.getRoot(),binding.llBottomRv,binding.flSlider,binding.llBottomRv.getHeight(), binding.getRoot().getHeight() - binding.layoutHead.getHeight() - binding.btNav.getHeight());
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

    private void showScrap(){
        scrapDialog.show(getSupportFragmentManager(),scrapDialog.getTag());
    }

    NewsScrapDialog scrapDialog = new NewsScrapDialog();
}
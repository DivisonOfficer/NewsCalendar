package edu.skku.cs.mysimplecalendar.activity.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.skku.cs.mysimplecalendar.datamodels.remote.NewsData;
import edu.skku.cs.mysimplecalendar.datamodels.remote.NewsResponse;
import edu.skku.cs.mysimplecalendar.utils.HttpRequestUtil;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Integer> _currentMonth = new MutableLiveData<>(5);

    public LiveData<Integer> currentMonth(){
        return _currentMonth;
    }

    private final MutableLiveData<Integer> _currentYear = new MutableLiveData<>(2022);

    public LiveData<Integer> currentYear(){
        return _currentYear;
    }

    private final MutableLiveData<Integer> _currentDay = new MutableLiveData<>(0);

    public LiveData<Integer> currentDay(){
        return _currentDay;
    }

    private final MutableLiveData<ArrayList<NewsData>> _newsList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<NewsData>> newsList(){
        return _newsList;
    }

    public void setCurrentDay(Integer day)
    {
        _currentDay.setValue(day);
    }



    public void nextMonth()
    {
        Integer month = currentMonth().getValue();
        month +=1;
        if(month > 12)
        {
            month = 1;
            _currentYear.setValue(_currentYear.getValue() + 1);
        }

        _currentMonth.setValue(month);
        _currentDay.setValue(0);
    }
    public void previousMonth(){
        Integer month = currentMonth().getValue();
        month -=1;
        if(month < 1)
        {
            _currentYear.setValue(_currentYear.getValue() - 1);
            month = 12;
        }
        _currentMonth.setValue(month);
        _currentDay.setValue(0);
    }

    public void getDailyNews(){
        new HttpRequestUtil().setURL(newsUrl + topHeadLine).addParameter("apiKey",newsApiKey).addParameter("country","kr").setOnSuccessListener(
                body -> {
                    NewsResponse response = new Gson().fromJson(body,NewsResponse.class);
                    _newsList.setValue(response.articles);
                }
        ).request();
    }



    public static String newsUrl = "https://newsapi.org/";
    public static String newsApiKey = "448661a0dc1e4bc1bbd84f215553424b";
    public static String topHeadLine = "v2/top-headlines";
}

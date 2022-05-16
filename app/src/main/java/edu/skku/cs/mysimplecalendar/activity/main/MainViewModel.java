package edu.skku.cs.mysimplecalendar.activity.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.skku.cs.mysimplecalendar.activity.login.LoginActivity;
import edu.skku.cs.mysimplecalendar.activity.login.LoginViewModel;
import edu.skku.cs.mysimplecalendar.datamodels.remote.NewsData;
import edu.skku.cs.mysimplecalendar.datamodels.remote.NewsPostBody;
import edu.skku.cs.mysimplecalendar.datamodels.remote.NewsResponse;
import edu.skku.cs.mysimplecalendar.datamodels.remote.NewsScrapBody;
import edu.skku.cs.mysimplecalendar.utils.HttpRequestUtil;
import edu.skku.cs.mysimplecalendar.utils.PreferenceUtil;

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

    private final MutableLiveData<ArrayList<NewsData>> _scrapList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<NewsData>> scrapList(){
        return _scrapList;
    }

    private final MutableLiveData<ArrayList<NewsData>> _scrapListByMonth = new MutableLiveData<>();

    public LiveData<ArrayList<NewsData>> scrapListByMonth(){return _scrapListByMonth;}

    public void setCurrentDay(Integer day)
    {
        _currentDay.setValue(day);
        ArrayList<NewsData> list = new ArrayList<>();
        filterScrapListByDate();
        getNewsByDate();
    }

    private final MutableLiveData<Integer> _layoutMode = new MutableLiveData<>(LAYOUT_SCRAP);

    public LiveData<Integer> layoutMode(){ return _layoutMode;}

    private final MutableLiveData<Integer> _scrapStatus = new MutableLiveData<>(0);

    public LiveData<Integer> scrapStatus(){ return _scrapStatus; }

    private final MutableLiveData<ArrayList<NewsData>> _scrapListByDate = new MutableLiveData<>();

    public LiveData<ArrayList<NewsData>> scrapListByDate(){ return _scrapListByDate; }

    private final MutableLiveData<ArrayList<NewsData>> _oldNews = new MutableLiveData<>();

    public LiveData<ArrayList<NewsData>> oldNews(){return _oldNews;}


    private NewsData dataOnScrap;

    public void setDataOnScrap(NewsData data)
    {
        this.dataOnScrap = data;
    }

    public void scrapWithCategory(String category)
    {
        //dataOnScrap.localCategory = category;
        //ArrayList<NewsData> list = scrapList().getValue();
        //list.add(dataOnScrap);
        //_scrapList.setValue(list);
        scrapNews(dataOnScrap,category);
    }

    public void filterScrapList()
    {
        ArrayList<NewsData> list = new ArrayList<>();

        _scrapList.getValue().forEach(data->{
            Log.d("MainViewModel",data.year() + "/" + data.month());
            if(data.year().equals(currentYear().getValue()) && data.month().equals(currentMonth().getValue())) list.add(data);
        });
        _scrapListByMonth.setValue(list);
    }

    public void filterScrapListByDate(){
        ArrayList<NewsData> list = new ArrayList<>();
        _scrapList.getValue().stream().filter(newsData -> {return newsData.day().equals(currentDay().getValue()) && newsData.month().equals(currentMonth().getValue()) && newsData.year().equals(currentYear().getValue());}).forEach(list::add);
        _scrapListByDate.setValue(list);
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
        filterScrapList();
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
        filterScrapList();
    }

    public void getDailyNews(){
        new HttpRequestUtil().setURL(newsUrl + topHeadLine).addParameter("apiKey",newsApiKey).addParameter("country","kr").addParameter("pageSize","100").setOnSuccessListener(
                body -> {
                    NewsResponse response = new Gson().fromJson(body,NewsResponse.class);

                    _newsList.setValue(response.articles);
                    postNews(response.articles);
                }
        ).request();
    }

    public void postNews(ArrayList<NewsData> news)
    {
        new HttpRequestUtil().setURL(LoginViewModel.BACKEND_URL + "news/post").setPostBody(new NewsPostBody(news)).setOnSuccessListener((response)->{
            Log.d("MainViewModel","Collect " + news.size() + "data to backedn");
        }).enableDebug().request();
    }

    public void scrapNews(NewsData news, String category)
    {
        NewsScrapBody body = new NewsScrapBody(PreferenceUtil.instance.getString(PreferenceUtil.USER_ID,""),news.url,category);
        new HttpRequestUtil().setURL(LoginViewModel.BACKEND_URL + "news/scrap").setPostBody(body).setOnSuccessListener((response)->{
            _scrapStatus.setValue(1);
            ArrayList<NewsData> list = scrapList().getValue();
            news.category = category;
            list.add(news);
            _scrapList.setValue(list);
            filterScrapList();
            filterScrapListByDate();
        }).enableDebug().request();
    }

    public void setCurrentLayout(Integer mode)
    {
        _layoutMode.setValue(mode);
        if(mode == LAYOUT_SCRAP)
            getScrapData();
    }

    public void getScrapData(){
        new HttpRequestUtil().setURL(LoginViewModel.BACKEND_URL+"news/scrap/get").addParameter("userId",PreferenceUtil.instance.getString(PreferenceUtil.USER_ID,"")).enableDebug().setOnSuccessListener(response -> {
            Type listType = new TypeToken<List<NewsData>>() {}.getType();
            ArrayList<NewsData> list = new Gson().fromJson(response,listType);
            _scrapList.setValue(list);
            filterScrapList();

        }).request();
    }
    public void deleteScrap(NewsData news)
    {
        ArrayList<NewsData> list = new ArrayList<>();
        _scrapList.getValue().stream().filter(data->!data.url.equals(news.url)).forEach(list::add);
        _scrapList.setValue(list);
        list = new ArrayList<>();
        _scrapListByDate.getValue().stream().filter(data->!data.url.equals(news.url)).forEach(list::add);
        _scrapListByDate.setValue(list);
        filterScrapList();
        NewsScrapBody body = new NewsScrapBody(PreferenceUtil.instance.getString(PreferenceUtil.USER_ID,""),news.url,news.category);
        new HttpRequestUtil().setURL(LoginViewModel.BACKEND_URL + "news/scrap/delete").setPostBody(body).setOnSuccessListener((response)->{
        }).enableDebug().request();
    }

    public void getNewsByDate()
    {
        String from = currentYear().getValue()+ "-"+currentMonth().getValue()+"-"+currentDay().getValue();
        new HttpRequestUtil().setURL(newsUrl + everythingNews).addParameter("apiKey",newsApiKey).addParameter("q","이").addParameter("pageSize","100").addParameter("from",from).addParameter("to",from).setOnSuccessListener(
                body -> {
                    NewsResponse response = new Gson().fromJson(body,NewsResponse.class);

                    _oldNews.setValue(response.articles);
                    postNews(response.articles);
                }
        ).request();
    }


    public final static String newsUrl = "https://newsapi.org/";
    public final static String newsApiKey = "448661a0dc1e4bc1bbd84f215553424b";
    public final static String topHeadLine = "v2/top-headlines";
    public final static String everythingNews = "v2/everything";

    public final static Integer LAYOUT_SCRAP = 0;
    public final static Integer LAYOUT_HEADLINE = 1;
    public final static Integer LAYOUT_NEWS_CALENDAR = 2;
    public final static Integer LAYOUT_MYPAGE = 3;
}

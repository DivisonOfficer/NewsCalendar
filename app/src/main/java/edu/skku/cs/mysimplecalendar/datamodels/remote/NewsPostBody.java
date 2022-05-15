package edu.skku.cs.mysimplecalendar.datamodels.remote;

import java.util.ArrayList;

public class NewsPostBody implements PostBody{
    public ArrayList<NewsData> newsList;
    public NewsPostBody(ArrayList<NewsData> list)
    {
        newsList = list;
    }
}

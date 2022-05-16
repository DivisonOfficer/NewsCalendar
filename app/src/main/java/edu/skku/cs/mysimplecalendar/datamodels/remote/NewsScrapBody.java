package edu.skku.cs.mysimplecalendar.datamodels.remote;

public class NewsScrapBody implements PostBody{
    public String userId;
    public String url;
    public String category;
    public NewsScrapBody(String userId, String url, String category)
    {
        this.url = url;
        this.userId = userId;
        this.category = category;
    }
}

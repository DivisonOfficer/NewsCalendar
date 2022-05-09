package edu.skku.cs.mysimplecalendar.datamodels.remote;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import edu.skku.cs.mysimplecalendar.utils.TimeFormat;

public class NewsData{
    public SourceData source;
    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt;
    public String content;
    public Integer color;

    public String localCategory;


    public String getDateString(){
        try {
            return TimeFormat.planListDateFormat.format(TimeFormat.remoteFormat.parse(publishedAt));
        } catch (ParseException e) {
            e.printStackTrace();
            return "FORMAT_ERROR";
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
            return "No_PublishedAt";
        }
    }

    public Integer day(){
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = TimeFormat.remoteFormat.parse(publishedAt);

            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}

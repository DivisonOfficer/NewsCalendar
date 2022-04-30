package edu.skku.cs.mysimplecalendar.datamodels.local.plan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlanThumbnailData implements ThumbnailData{
    public String title = "";
    public String content = "";
    public Date startTime = null, endTime = null;

    public PlanThumbnailData(String title, String content, Date startTime, Date endTime)
    {
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public PlanThumbnailData(String title, String content, Date startTime)
    {
        this(title,content,startTime,null);
    }
    public PlanThumbnailData(String title, String content)
    {
        this(title,content,null,null);
    }

    public String getTimeString(){
        if(startTime==null) return "하루종일";
        if(endTime==null) return format.format(startTime);
        return format.format(startTime)+":"+format.format(endTime);
    }
    static DateFormat format = DateFormat.getTimeInstance();
}

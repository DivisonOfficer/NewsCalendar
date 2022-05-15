package edu.skku.cs.mysimplecalendar.datamodels.remote;

public class StatusBody implements ResponseBody{
    public Boolean success;
    public StatusBody(Boolean success)
    {
        this.success = success;
    }
}

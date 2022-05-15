package edu.skku.cs.mysimplecalendar.datamodels.remote;

public class UserBody implements PostBody{
    public String userName;
    public String passWord;

    public UserBody(String userName, String passWord)
    {
        this.userName = userName;
        this.passWord = passWord;
    }
}

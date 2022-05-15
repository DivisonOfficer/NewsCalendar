package edu.skku.cs.mysimplecalendar.datamodels.remote;

import com.google.gson.annotations.SerializedName;

public class UserBody implements PostBody{
    @SerializedName(value = "userId") public String userName;
    @SerializedName(value = "passWd") public String passWord;

    public UserBody(String userName, String passWord)
    {

        this.userName = userName;
        this.passWord = passWord;
    }
}

package edu.skku.cs.mysimplecalendar.utils;

import com.google.gson.Gson;

import edu.skku.cs.mysimplecalendar.datamodels.remote.PostBody;
import edu.skku.cs.mysimplecalendar.datamodels.remote.ResponseBody;

public class RemoteDataSource {


    public static void getExample(String param, OnSuccessListener<ResponseBody> onSuccess)
    {
        new HttpRequestUtil().setURL(url).addParameter("Param1",param).setOnSuccessListener(response ->
        {
            onSuccess.onSuccess(new Gson().fromJson(response, ResponseBody.class));
        }).request();
    }


    private static String url = "";

    public interface OnSuccessListener<T extends ResponseBody>{
        void onSuccess(T response);
    }
}

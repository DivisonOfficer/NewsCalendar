package edu.skku.cs.mysimplecalendar.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import edu.skku.cs.mysimplecalendar.datamodels.remote.PostBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequestUtil implements Runnable {

    String url;
    public HttpRequestUtil(){
    }

    private PostBody postBody = null;

    private Boolean debug = false;

    public HttpRequestUtil setPostBody(PostBody postBody)
    {
        this.postBody = postBody;
        return this;
    }

    public HttpRequestUtil enableDebug()
    {
        this.debug = true;
        return this;
    }

    ArrayList<Pair<String,String>> params = new ArrayList<>();
    public HttpRequestUtil setURL(String url)
    {
        this.url = url;
        return this;
    }
    public HttpRequestUtil addParameter(String key, String value)
    {
        params.add(new Pair<>(key,value));
        return this;
    }
    public HttpRequestUtil setOnSuccessListener(OnSuccessListener listener)
    {
        this.onSuccessListener = listener;
        return this;
    }
    public HttpRequestUtil setOnFailedListener(OnFailedListener listener)
    {
        this.onFailedListener = listener;
        return this;
    }


    public void request() {
        new Thread(this).start();
    }

    private void setClient(){
        if(client == null) client = new OkHttpClient();
    }
    OnSuccessListener onSuccessListener = null;
    OnFailedListener onFailedListener = null;



    @Override
    public void run() {
        setClient();
        if(postBody != null) post();
        else get();
    }

    private void post(){
        String json = new Gson().toJson(postBody);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

        Request.Builder builder;
        try{
            builder = new Request.Builder().url(url).post(requestBody);
        }catch(Exception e)
        {
            throw e;
        }
        Request request = builder.build();
        Response response;
        try {
            if(debug) Log.d(Tag,"POST : "+url);
            if(debug) Log.d(Tag,json);
            response = client.newCall(request).execute();
            if(response.isSuccessful())
            {
                runOnSuccess(response);
            }
            else{
                if(debug) Log.d(Tag,"Response : "+response.code());
                if(onFailedListener != null) runOnFailed(response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void get(){

        if(params.size() > 0) url +="?";

        for(int i = 0; i< params.size();i++)
        {
            if(i>0)  url += "&";
            Pair<String,String> param = params.get(i);
            url += param.first + "=" + param.second;

        }
        if(debug)Log.d(Tag,"Request : get " +url);

        Request.Builder builder = new Request.Builder().url(url).get();
        Request request = builder.build();

        Response response;
        try {

            response = client.newCall(request).execute();

            if(response.isSuccessful())
            {
                runOnSuccess(response);
            }
            else{
                if(debug) Log.d(Tag,"Response : "+response.code());
                if(onFailedListener != null) runOnFailed(response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void runOnSuccess(Response finalResponse) throws IOException{
        String json = Objects.requireNonNull(finalResponse.body()).string();
        new Handler(Looper.getMainLooper()).post(() -> onSuccessListener.onSuccess(json));
    }

    private void runOnFailed(Integer code)
    {
        new Handler(Looper.getMainLooper()).post(()->onFailedListener.onFailed(code));
    }

    public interface OnSuccessListener{
        void onSuccess(String response);
    }
    public interface OnFailedListener{
        void onFailed(Integer code);
    }

    private static final String Tag = "HTTP LOG";
    static OkHttpClient client = null;
}
package edu.skku.cs.mysimplecalendar.activity;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import edu.skku.cs.mysimplecalendar.activity.webview.WebActivity;

public class BaseActivity extends AppCompatActivity {

    protected <T extends ViewDataBinding> T bind(Integer id)
    {
        return DataBindingUtil.setContentView(this,id);
    }

    protected void toast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


    protected void openWebView(String title, String url)
    {
        Intent intent = new Intent(this,WebActivity.class);
        intent.putExtra(WebActivity.CODE_WEBVIEW_URL,url);
        intent.putExtra(WebActivity.CODE_WEBVIEW_TITLE,title);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}

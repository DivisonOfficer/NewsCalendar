package edu.skku.cs.mysimplecalendar.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.activity.webview.WebActivity;
import edu.skku.cs.mysimplecalendar.databinding.ItemCustomToastBinding;

public class BaseActivity extends AppCompatActivity {

    protected <T extends ViewDataBinding> T bind(Integer id)
    {
        return DataBindingUtil.setContentView(this,id);
    }

    protected void toast(String msg)
    {
        Toast toast = new Toast(this);
        ItemCustomToastBinding inner = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_custom_toast, null,false);
        inner.setText(msg);
        toast.setView(inner.getRoot());
        toast.show();
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

    protected InputMethodManager imm(){
        return (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
    }
}

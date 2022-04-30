package edu.skku.cs.mysimplecalendar.activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class BaseActivity extends AppCompatActivity {

    protected <T extends ViewDataBinding> T bind(Integer id)
    {
        return DataBindingUtil.setContentView(this,id);
    }

    protected void toast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}

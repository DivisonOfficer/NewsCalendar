package edu.skku.cs.mysimplecalendar.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.activity.BaseActivity;
import edu.skku.cs.mysimplecalendar.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;


    /************************************************************************
     * APP LIFECYCLE
     *************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = bind(R.layout.activity_main);
    }




}
package edu.skku.cs.mysimplecalendar.activity.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.activity.BaseActivity;
import edu.skku.cs.mysimplecalendar.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLoginBinding bind;
    private LoginViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = bind(R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        bind.setViewmodel(viewModel);
        setEditText();
    }

    @Override
    public void onClick(View view) {
        if(view == bind.btnLogin)
        {

        }
        else if(view == bind.btnConfirm)
        {

        }
        else if(view == bind.btnRegister)
        {
            viewModel.registerMode();
        }
    }


    private void setEditText(){
        bind.etUsername.setOnEditorActionListener((v, action, event)->{
            if(action == EditorInfo.IME_ACTION_NEXT)
            {
                v.clearFocus();
                bind.etPassword.requestFocus();
                return true;
            }

            return false;
        });
        bind.etPassword.setOnEditorActionListener((v,action,event)->{
            if(action == EditorInfo.IME_ACTION_DONE)
            {
                v.clearFocus();
                imm().hideSoftInputFromWindow(v.getWindowToken(),0);

            }

            return false;
        });
    }
}
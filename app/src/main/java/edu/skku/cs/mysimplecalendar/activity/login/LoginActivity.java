package edu.skku.cs.mysimplecalendar.activity.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.activity.BaseActivity;
import edu.skku.cs.mysimplecalendar.activity.main.MainActivity;
import edu.skku.cs.mysimplecalendar.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLoginBinding bind;
    private LoginViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = bind(R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        bind.setLifecycleOwner(this);
        bind.setViewmodel(viewModel);
        setEditText();
        bind.btnConfirm.setOnClickListener(this);
        bind.btnLogin.setOnClickListener(this);
        bind.btnRegister.setOnClickListener(this);
        observeData();
        LoginViewModel.BACKEND_URL = getString(R.string.str_url_backend);
    }

    @Override
    public void onClick(View view) {
        if(view == bind.btnLogin)
        {
            clearFocus();
            viewModel.login(bind.etUsername.getText().toString(), bind.etPassword.getText().toString());
        }
        else if(view == bind.btnConfirm)
        {
            clearFocus();
            viewModel.registerUser(bind.etUsername.getText().toString(), bind.etPasswordRegister.getText().toString(), bind.etPasswordConfirm.getText().toString());
        }
        else if(view == bind.btnRegister)
        {
            clearFocus();
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
            if(action == EditorInfo.IME_ACTION_GO)
            {
                v.clearFocus();
                imm().hideSoftInputFromWindow(v.getWindowToken(),0);

            }

            return false;
        });
    }

    private void observeData(){
        viewModel.messageCode().observe(this, code ->{
            if (code == LoginViewModel.CODE_USERNAME_NOT_ACCEPT) {
                toast("아이디를 다시 입력해주세요");
            }
            else if(code == LoginViewModel.CODE_PASSWORD_WRONG)
            {
                toast("비밀번호를 다시 입력해주세요");
            }
            else if(code == LoginViewModel.CODE_SUCCESS_LOGIN)
            {
                toast("로그인 성공");
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else if(code == LoginViewModel.CODE_SUCCESS_REGISTER)
            {
                toast("회원가입 성공");
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else if(code == LoginViewModel.CODE_FAILED_LOGIN)
            {
                toast("로그인 실패");
            }
            else if(code == LoginViewModel.CODE_FAILED_REGISTER)
            {
                toast("회원가입 실패");
            }

        });
    }

    private void clearFocus()
    {
        imm().hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        getCurrentFocus().clearFocus();
    }


}
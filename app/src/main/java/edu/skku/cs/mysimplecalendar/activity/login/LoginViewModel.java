package edu.skku.cs.mysimplecalendar.activity.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.datamodels.remote.StatusBody;
import edu.skku.cs.mysimplecalendar.datamodels.remote.UserBody;
import edu.skku.cs.mysimplecalendar.utils.HttpRequestUtil;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Integer> _messageCode = new MutableLiveData<>(-1);
    public LiveData<Integer> messageCode(){
        return _messageCode;
    }

    private MutableLiveData<Boolean> _register = new MutableLiveData<>(false);
    public LiveData<Boolean> register(){
        return _register;
    }

    public void registerMode(){
        _register.setValue(true);
    }


    public void login(String username, String password)
    {
        if(!checkUsername(username))
        {
            _messageCode.setValue(CODE_USERNAME_NOT_ACCEPT);
            return;
        }
        if(!checkPassword(password))
        {
            _messageCode.setValue(CODE_PASSWORD_WRONG);
            return;
        }
        try {
            new HttpRequestUtil().setURL(BACKEND_URL + "user/login").setPostBody(new UserBody(username, password)).setOnSuccessListener(response -> {
                StatusBody body = new Gson().fromJson(response, StatusBody.class);
                if (body.success) {
                    _messageCode.setValue(CODE_SUCCESS_LOGIN);
                } else {
                    _messageCode.setValue(CODE_FAILED_LOGIN);
                }

            }).setOnFailedListener(code ->{
                _messageCode.setValue(CODE_FAILED_LOGIN);
            }).request();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            _messageCode.setValue(CODE_FAILED_LOGIN);
        }

    }

    public void registerUser(String username, String password, String passwordConfirm) {
        if(!checkUsername(username))
        {
            _messageCode.setValue(CODE_USERNAME_NOT_ACCEPT);
            return;
        }
        if (!checkPassword(password) || !password.equals(passwordConfirm))
        {
            _messageCode.setValue(CODE_PASSWORD_WRONG);
            return;
        }
        try {
            new HttpRequestUtil().setURL(BACKEND_URL + "user/add").setPostBody(new UserBody(username, password)).setOnSuccessListener(response -> {
                StatusBody body = new Gson().fromJson(response, StatusBody.class);
                if (body.success) {
                    _messageCode.setValue(CODE_SUCCESS_REGISTER);
                } else {
                    _messageCode.setValue(CODE_FAILED_REGISTER);
                }

            }).setOnFailedListener(code ->{
                _messageCode.setValue(CODE_FAILED_REGISTER);
            }).request();
        }
         catch(Exception e)
        {
            e.printStackTrace();
            _messageCode.setValue(CODE_FAILED_REGISTER);
        }

    }


    private Boolean checkUsername(String username)
    {
        if(username.isEmpty()) return false;

        return true;
    }

    private Boolean checkPassword(String password)
    {
        if(password.isEmpty()) return false;

        return true;
    }



    public final static Integer CODE_USERNAME_NOT_ACCEPT = 0;
    public final static Integer CODE_PASSWORD_WRONG = 1;
    public final static Integer CODE_FAILED_LOGIN = 2;
    public final static Integer CODE_FAILED_REGISTER = 3;
    public final static Integer CODE_SUCCESS_LOGIN = 4;
    public final static Integer CODE_SUCCESS_REGISTER = 5;

    public static String BACKEND_URL;

    // userId = admin , passWd = 1234
}

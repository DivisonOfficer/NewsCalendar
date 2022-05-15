package edu.skku.cs.mysimplecalendar.activity.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Boolean> _register = new MutableLiveData<>(false);
    public LiveData<Boolean> register(){
        return _register;
    }

    public void registerMode(){
        _register.setValue(true);
    }

}

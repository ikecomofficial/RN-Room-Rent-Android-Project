package com.example.rnroomrent.ui.account_setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountSettingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AccountSettingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Account Setting fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
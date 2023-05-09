package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.checkerframework.checker.nullness.qual.NonNull;

import ru.student.detected.educator.data.models.User;

public class UserViewModel extends AndroidViewModel {

    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private User user;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<User> getUserLiveData() {
        return userMutableLiveData;
    }
    public void setUserName(String name) {
        user.setFirstName(name);
    }
    public void setUserImage(String uri){
        user.setPhotoUrl(uri);
        if (userMutableLiveData.getValue() != null)
            userMutableLiveData.getValue().setPhotoUrl(uri);
    }

    public void setUser(User user) {
        this.user = user;
        // Without setting new value UI is not updated and observe is not called
        userMutableLiveData.setValue(user);
    }
}
package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
        // Both updates LiveData but does not update UI
        user.setFirstName(name);
        // userMutableLiveData.getValue().setName("Updated Name");

        // This one Updates UI
        //  userMutableLiveData.setValue(userMutableLiveData.getValue());
    }

    public void setUser(User user) {
        this.user = user;
        // Without setting new value UI is not updated and observe is not called
        userMutableLiveData.setValue(user);
    }
}
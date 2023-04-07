package ru.student.detected.page1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.student.detected.page1.data.models.User;

public class UserViewModel extends ViewModel {

    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private User user;

    public UserViewModel(User user) {
       this.user = user;
        userMutableLiveData.setValue(user);

    }

    public void setUserName(String name) {
        // Both updates LiveData but does not update UI
        user.setName(name);
        // userMutableLiveData.getValue().setName("Updated Name");

        // This one Updates UI
        //  userMutableLiveData.setValue(userMutableLiveData.getValue());
    }

    public void changeUser(User user) {
        this.user = user;
        // Without setting new value UI is not updated and observe is not called
        userMutableLiveData.setValue(user);
    }
}
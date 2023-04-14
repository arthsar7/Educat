package ru.student.detected.educator.data.data_sources;

import android.app.Application;
import android.content.res.TypedArray;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ru.student.detected.educator.data.models.Theory;
import ru.student.detected.page1.R;

public class TheoryDataSource {
    public LiveData<List<Theory>> theories(Application application) {
        List<String> descriptions = Arrays.asList(application.getApplicationContext().getResources().
                getStringArray(R.array.theory_descriptions));
        List<String> names = Arrays.asList(application.getApplicationContext().getResources().
                getStringArray(R.array.theory_names));
        TypedArray images = application.getApplicationContext().getResources().obtainTypedArray(R.array.theory_images);
        TypedArray item_images = application.getApplicationContext().getResources().obtainTypedArray(R.array.theory_item_images);
        MutableLiveData<List<Theory>> result = new MutableLiveData<>();
        List<Theory> theories = new ArrayList<>();
        new Thread(() -> {
            for (int i = 0; i < names.size(); i++) {
                theories.add(new Theory(images.getResourceId(i,R.drawable.educatlogo),
                        names.get(i), descriptions.get(i),
                        item_images.getResourceId(i,R.drawable.item_green)));
            }
            images.recycle();
            item_images.recycle();
            result.postValue(theories);
        }).start();
        return result;
    }

}

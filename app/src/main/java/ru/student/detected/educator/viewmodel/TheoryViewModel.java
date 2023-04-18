package ru.student.detected.educator.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.student.detected.educator.data.models.Theory;
import ru.student.detected.educator.data.repositories.TheoryRepository;

public class TheoryViewModel extends AndroidViewModel {
    private final LiveData<List<Theory>> theories;
    private final MutableLiveData<Theory> selectedTheory = new MutableLiveData<>();
    public TheoryViewModel(@NonNull Application application) {
        super(application);
        TheoryRepository repository = new TheoryRepository();
        theories = repository.getTheoryData(application);
    }
    public String progress(){
        if(theories.getValue()!=null) {
            int all_theory = theories.getValue().size();
            int counter = 0;
            for (Theory theory : theories.getValue()){
                if(theory.isChecked()){
                    counter++;
                }
            }
            return String.valueOf((int)((float) counter/all_theory*100));
        }
        return "0";
    };
    public void setSelectedTheory(Theory theory) {
        selectedTheory.setValue(theory);
    }
    public LiveData<Theory> getSelectedTheory() {
        return selectedTheory;
    }

    public LiveData<List<Theory>> getTheories() {
        return theories;
    }
    public void addTheories(Theory theories) {
    }
}
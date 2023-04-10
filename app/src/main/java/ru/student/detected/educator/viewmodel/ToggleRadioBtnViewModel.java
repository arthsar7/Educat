package ru.student.detected.educator.viewmodel;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.IntStream;

import ru.student.detected.educator.ui.views.ToggleRadioButton;

public class ToggleRadioBtnViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ToggleRadioButton>> data = new MutableLiveData<>();
    private ArrayList<ToggleRadioButton> buttons;
    private final ArrayList<ImageView> selectors;

    public ToggleRadioBtnViewModel(ArrayList<ToggleRadioButton> buttons, ArrayList<ImageView> selectors) {
        this.buttons = buttons;
        this.selectors = selectors;
        data.setValue(buttons);
    }

    public void setButtonWork(){
        IntStream.range(0, buttons.size()).forEach(index ->
                Objects.requireNonNull(data.getValue()).get(index).setOnCheckedChangeListener((buttonView, isChecked) ->
                        selectors.get(index).setVisibility(isChecked ? VISIBLE : INVISIBLE)
        ));
    }
    public ArrayList<ToggleRadioButton> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<ToggleRadioButton> buttons) {
        this.buttons = buttons;
        data.setValue(buttons);
    }

    public MutableLiveData<ArrayList<ToggleRadioButton>> getData() {
        return data;
    }

    public void setData(MutableLiveData<ArrayList<ToggleRadioButton>> data) {
        this.data = data;
    }
}

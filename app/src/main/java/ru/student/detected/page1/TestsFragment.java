package ru.student.detected.page1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;

import ru.student.detected.page1.databinding.FragmentTestsBinding;

public class TestsFragment extends Fragment {
    private FragmentTestsBinding binding;
    private MutableLiveData<Boolean[]> selectorViewModel = new MutableLiveData<>();
    private Boolean[] currAr;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tests, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(selectorViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<ToggleRadioButton> buttons = new ArrayList<>(Arrays.asList(binding.entryTestBtn, binding.test2Btn));
        currAr = new Boolean[2];
        for (int i = 0; i < buttons.size(); i++) {
            int finalI = i;
            buttons.get(i).setOnCheckedChangeListener((buttonView, isChecked) -> {
                currAr[finalI] = isChecked;
                selectorViewModel.setValue(currAr);
            });
        }

    }
}
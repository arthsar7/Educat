package ru.student.detected.page1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

import ru.student.detected.page1.R;
import ru.student.detected.page1.viewmodel.ToggleRadioBtnViewModel;
import ru.student.detected.page1.ToggleRadioButton;
import ru.student.detected.page1.databinding.FragmentTestsBinding;

public class TestsFragment extends Fragment {
    private FragmentTestsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tests, container, false);
        ArrayList<ToggleRadioButton> buttons = new ArrayList<>(Arrays.asList(binding.entryTestBtn, binding.test2Btn));
        ArrayList<ImageView> selectors = new ArrayList<>(Arrays.asList(binding.entryTestSelector, binding.selector2));
        ToggleRadioBtnViewModel btnViewModel = new ToggleRadioBtnViewModel(buttons, selectors);
        binding.setLifecycleOwner(this);
        binding.setViewModel(btnViewModel);
        btnViewModel.setButtonWork();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
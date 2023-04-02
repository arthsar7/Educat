package ru.student.detected.page1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import ru.student.detected.page1.databinding.FragmentTestsBinding;

public class TestsFragment extends Fragment {
    private FragmentTestsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tests, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ToggleRadioButton[] buttons = new ToggleRadioButton[]{
                binding.entryTestBtn,
                binding.test2Btn
        };
        for(ToggleRadioButton button : buttons){
            button.setOnCheckedChangeListener( (buttonView, isChecked) -> {
                if(isChecked){
                    Toast.makeText(getActivity(), "da", Toast.LENGTH_SHORT).show();
                }else{

                }
            });
        }

    }
}
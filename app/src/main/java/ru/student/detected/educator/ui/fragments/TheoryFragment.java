package ru.student.detected.educator.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentTheoryBinding;

public class TheoryFragment extends Fragment {
    private FragmentTheoryBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_theory, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.home.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_theoryFragment_to_tests));
        binding.profile.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_theoryFragment_to_userProfileFragment));
    }
}
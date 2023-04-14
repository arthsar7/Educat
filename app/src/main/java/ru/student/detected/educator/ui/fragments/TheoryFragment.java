package ru.student.detected.educator.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Collections;
import java.util.Objects;

import ru.student.detected.educator.ui.adapters.TheoryViewAdapter;
import ru.student.detected.educator.data.models.Theory;
import ru.student.detected.educator.viewmodel.TheoryViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentTheoryBinding;

public class TheoryFragment extends Fragment {
    private FragmentTheoryBinding binding;
    private TheoryViewModel theoryViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_theory, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        theoryViewModel = new ViewModelProvider(this).get(TheoryViewModel.class);
        binding.home.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_theoryFragment_to_tests));
        binding.profile.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_theoryFragment_to_userProfileFragment));
        binding.itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.itemList.setAdapter(new TheoryViewAdapter());
        theoryViewModel.getTheories().observe(getViewLifecycleOwner(), (value)->
                ((TheoryViewAdapter) Objects.requireNonNull(binding.itemList.getAdapter()))
                .updateData(value));
    }
}
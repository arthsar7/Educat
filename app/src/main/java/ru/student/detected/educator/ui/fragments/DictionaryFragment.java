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
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.Collections;

import ru.student.detected.educator.ui.adapters.DictionaryAdapter;
import ru.student.detected.educator.viewmodel.DictionaryViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentDictionaryBinding;

public class DictionaryFragment extends Fragment {
    private FragmentDictionaryBinding binding;
    private DictionaryViewModel dictionaryViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dictionary, container, false);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        DictionaryAdapter dictionaryAdapter = new DictionaryAdapter();
        binding.back.setOnClickListener(v ->
                Navigation.findNavController(v).popBackStack());
        dictionaryViewModel = new ViewModelProvider(requireActivity()).get(DictionaryViewModel.class);
        binding.recyclerView.setAdapter(dictionaryAdapter);
        dictionaryViewModel.getLivePairs().observe(getViewLifecycleOwner(), pairs -> {
            Collections.sort(pairs);
            ((DictionaryAdapter) binding.recyclerView.getAdapter()).updateData(pairs);
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}

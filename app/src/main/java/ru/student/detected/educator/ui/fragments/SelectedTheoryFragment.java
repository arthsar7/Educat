package ru.student.detected.educator.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ru.student.detected.educator.viewmodel.TheoryViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentSelectedTheoryBinding;

public class SelectedTheoryFragment extends Fragment {
    private TheoryViewModel theoryViewModel;
    private FragmentSelectedTheoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selected_theory, container, false);
        theoryViewModel = new ViewModelProvider(requireActivity()).get(TheoryViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setTheoryViewModel(theoryViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.back.setOnClickListener(v-> Navigation.findNavController(view).navigate(R.id.action_selectedTheoryFragment_to_theoryFragment));
    }
}

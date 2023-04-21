package ru.student.detected.educator.ui.fragments;

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
import ru.student.detected.page1.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.alreadyHaveAccBtn.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_authentication);
        });
    }
}

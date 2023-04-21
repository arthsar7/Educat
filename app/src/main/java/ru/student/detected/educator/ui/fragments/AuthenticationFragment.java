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
import ru.student.detected.page1.databinding.FragmentAuthenticationBinding;

public class AuthenticationFragment extends Fragment {
    private FragmentAuthenticationBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_authentication, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.signbutton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.auth_to_test));
        binding.signUpButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_authentication_to_registerFragment));
    }
}
package ru.student.detected.educator.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.student.detected.educator.viewmodel.UserViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentUserProfileBinding;

public class UserProfileFragment extends Fragment {

    private UserViewModel mViewModel;
    private FragmentUserProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.home.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_tests));
        binding.theory.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_theoryFragment));
    }

}
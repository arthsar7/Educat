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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import ru.student.detected.educator.data.models.User;
import ru.student.detected.educator.viewmodel.UserViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentUserProfileBinding;

public class UserProfileFragment extends Fragment {
    private FragmentUserProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false);
        UserViewModel userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::setUserData);
        return binding.getRoot();
    }

    private void setUserData(User user) {
        binding.userFullname.setText(user.getFirstName() + " " + user.getSecondName());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.home.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_tests));
        binding.theory.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_theoryFragment));
    }

}
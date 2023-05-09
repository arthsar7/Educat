package ru.student.detected.educator.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import kotlin.Unit;
import ru.student.detected.educator.data.models.User;
import ru.student.detected.educator.viewmodel.UserViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentUserProfileBinding;

public class UserProfileFragment extends Fragment {
    private FragmentUserProfileBinding binding;
    private FirebaseFirestore db;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::setUserData);
        binding.exit.setOnClickListener(v -> {
            showExitDialog();

        });
        db = FirebaseFirestore.getInstance();
        return binding.getRoot();
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Уже покидаете?");
        builder.setMessage("Вы действительно хотите выйти?");
        builder.setPositiveButton("Да", (dialog, which) -> {
            FirebaseAuth.getInstance().signOut();
            dialog.dismiss();
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_userProfileFragment_to_authentication);
        }).setNegativeButton("Нет", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @SuppressLint("SetTextI18n")
    private void setUserData(User user) {
        binding.userFullname.setText(user.getFirstName() + " " + user.getSecondName());
        binding.userId.setText("@" + user.getID());
        if(user.getPhotoUrl() != null)
            binding.userPhoto.setImageURI(Uri.parse(user.getPhotoUrl()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.home.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_tests));
        binding.theory.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_theoryFragment));
        binding.changePhoto.setOnClickListener(v ->
                ImagePicker.Companion.with(this)
                        .crop()
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .createIntent(intent -> {
                            resultLauncher.launch(intent);
                            return Unit.INSTANCE;
                        })
        );
    }
    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                db.collection("User")
                            .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                    .update("photoUrl", uri);
                    binding.userPhoto.setImageURI(uri);
                    userViewModel.setUserImage(uri.toString());
                }
            });

}
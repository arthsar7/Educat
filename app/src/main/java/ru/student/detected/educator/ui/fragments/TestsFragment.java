package ru.student.detected.educator.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

import ru.student.detected.educator.data.models.User;
import ru.student.detected.educator.ui.views.LocationDialog;
import ru.student.detected.educator.ui.views.ToggleRadioButton;
import ru.student.detected.educator.viewmodel.ToggleRadioBtnViewModel;
import ru.student.detected.educator.viewmodel.UserViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentTestsBinding;

public class TestsFragment extends Fragment {
    private FragmentTestsBinding binding;
    private FirebaseFirestore db;
    private UserViewModel userViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        db = FirebaseFirestore.getInstance();
        FirebaseUser dbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (dbUser != null) {
            DocumentReference reference = db.collection("User").document(dbUser.getUid());
            reference.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    userViewModel.setUser(user);
                }
            });
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tests, container, false);
        ArrayList<ToggleRadioButton> buttons = new ArrayList<>(Arrays.asList(binding.entryTestBtn, binding.test2Btn));
        ArrayList<ImageView> selectors = new ArrayList<>(Arrays.asList(binding.entryTestSelector, binding.selector2));
        ToggleRadioBtnViewModel btnViewModel = new ToggleRadioBtnViewModel(buttons, selectors);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(btnViewModel);
        btnViewModel.setButtonWork();
        binding.card1.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_tests_to_cardFragment));
        boolean isEntryTestPassed = requireContext().getSharedPreferences("EntryTestPassed", Context.MODE_PRIVATE)
                .getBoolean("rlyEntryTestPassed", false);
        if (isEntryTestPassed) {
            if(!requireContext().getSharedPreferences("Dialogs", Context.MODE_PRIVATE)
                    .getBoolean("Dialog2", false)) {
                LocationDialog locationDialog = new LocationDialog(requireContext());
                locationDialog.show();
                requireContext().getSharedPreferences("Dialogs", Context.MODE_PRIVATE)
                        .edit().putBoolean("Dialog2", true).apply();
            }
            unlockBtn(buttons, selectors, 2, R.drawable.test2_btn, R.drawable.selected2);

        }
        return binding.getRoot();
    }

    private void unlockBtn(ArrayList<ToggleRadioButton> buttons, ArrayList<ImageView> selectors, int position,
                           int btnDrawable, int slDrawable) {
        int index = position - 1;
        if(buttons.get(index)!= null && selectors.get(index)!=null) {
            buttons.get(index).setBackground(ContextCompat.getDrawable(requireContext(), btnDrawable));
            selectors.get(index).setImageResource(slDrawable);
            selectors.get(index).setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_tests_to_test2Fragment));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.profile.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_tests_to_userProfileFragment));
        binding.theory.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_tests_to_theoryFragment));
        binding.entryTestSelector.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_tests_to_entryTestFragment));
    }
}
package ru.student.detected.educator.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicBoolean;

import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentAuthenticationBinding;

public class AuthenticationFragment extends Fragment {
    private FragmentAuthenticationBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_authentication, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO: MAKE USER KEEP LOGGED IN
//        mAuth.addAuthStateListener(authStateListener->{
//            FirebaseUser user = authStateListener.getCurrentUser();
//            if (user != null && user.isEmailVerified()) {
//                binding.progressBar.setVisibility(View.VISIBLE);
////                Navigation.findNavController(view).navigate(R.id.auth_to_test);
//            }
//        });
        binding.signbutton.setOnClickListener(this::onLoginClick);
        binding.signUpButton.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_authentication_to_registerFragment));
        binding.forgotPassword.setOnClickListener(v ->
        {
        });
    }

    private void onLoginClick(View v) {
        String login = binding.login.getText().toString(),
                password = binding.password.getText().toString();
        if (checkCorrect(login, password)) {
            binding.progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user != null && user.isEmailVerified()) {
                                binding.progressBar.setVisibility(View.GONE);
                                Navigation.findNavController(v).navigate(R.id.auth_to_test);
                            }
                            else {
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Проверьте почту", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Неверный логин или пароль",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(),
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private boolean checkCorrect(String login, String password) {
        boolean isCorrect = true;
        if (TextUtils.isEmpty(login)) {
            binding.login.setError("Введите логин");
            isCorrect = false;
        }
        if (TextUtils.isEmpty(password)) {
            binding.password.setError("Введите пароль");
            return false;
        }
        return isCorrect;
    }
}
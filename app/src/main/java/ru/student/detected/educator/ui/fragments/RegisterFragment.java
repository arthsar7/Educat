package ru.student.detected.educator.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import ru.student.detected.educator.data.models.User;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.alreadyHaveAccBtn.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_authentication);
        });
        binding.createBtn.setOnClickListener(v->{
            String login = binding.login.getText().toString(),
                    password = binding.password.getText().toString();
            String firstName = binding.name.getText().toString().split(" ")[0];
            String secondName;
                if(binding.name.getText().toString().split(" ").length == 2)
                    secondName = binding.name.getText().toString().split(" ")[1];
                else secondName = "";
            if (checkCorrect(login, password, firstName, secondName)) {
                binding.progressBar1.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener(task ->
                {
                   if(task.isSuccessful()){
                       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                       if (user != null) {
                           binding.progressBar1.setVisibility(View.GONE);
                           sendEmailAndNavigate(login, firstName, secondName, user);
                       }
                   }
                   else{
                       binding.progressBar1.setVisibility(View.GONE);
                       Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }).addOnFailureListener(e -> {
                   binding.progressBar1.setVisibility(View.GONE);
                   Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT)
                           .show();
               });
            }
        });
    }

    private void sendEmailAndNavigate(String login, String firstName, String secondName, FirebaseUser user) {
        user.sendEmailVerification().addOnSuccessListener(v1 -> {
            Toast.makeText(getContext(), "Письмо отправлено", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        });
        binding.createBtn.setText("Подтвердить");
        binding.createBtn.setOnClickListener(v1 -> addUser(login, firstName, secondName)
        );
    }

    private void addUser(String login, String firstName, String secondName) {
        Toast.makeText(requireContext(), "Аккаунт создан, подтвердите почту",
                Toast.LENGTH_SHORT).show();
        db.collection("User")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .set(new User(firstName, login, secondName, null));
        Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_authentication);
    }


    private boolean checkCorrect(String login, String password, String firstName, String secondName) {
        boolean isCorrect = true;
        if(TextUtils.isEmpty(login)){
            binding.login.setError("Введите логин");
            isCorrect = false;
        }
        if(TextUtils.isEmpty(password)){
            binding.password.setError("Введите пароль");
            isCorrect = false;
        }
        if(TextUtils.isEmpty(firstName)){
            binding.name.setError("Введите имя");
            isCorrect = false;
        }
        if(TextUtils.isEmpty(secondName)){
            binding.name.setError("Введите фамилию");
            return false;
        }
        return isCorrect;
    }
}


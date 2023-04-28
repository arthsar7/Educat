package ru.student.detected.educator.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import ru.student.detected.educator.data.models.Theory;
import ru.student.detected.educator.ui.adapters.TheoryViewAdapter;
import ru.student.detected.educator.ui.interfaces.OnTheoryClickListener;
import ru.student.detected.educator.viewmodel.TheoryViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentTheoryBinding;

public class TheoryFragment extends Fragment implements OnTheoryClickListener {
    private FragmentTheoryBinding binding;
    private TheoryViewModel theoryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theory, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        theoryViewModel = new ViewModelProvider(requireActivity()).get(TheoryViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.home.setOnClickListener(v -> Navigation.findNavController(view)
                .navigate(R.id.action_theoryFragment_to_tests));
        binding.profile.setOnClickListener(v -> Navigation.findNavController(view)
                .navigate(R.id.action_theoryFragment_to_userProfileFragment));
        binding.itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        TheoryViewAdapter theoryViewAdapter = new TheoryViewAdapter(this);
        binding.itemList.setAdapter(theoryViewAdapter);
        theoryViewModel.getTheories().observe(getViewLifecycleOwner(), (value)-> {
            ((TheoryViewAdapter)
                    Objects.requireNonNull(binding.itemList.getAdapter()))
                    .updateData(value);
            progressCheck(value);
        });
        binding.theory.setOnLongClickListener(v -> {
            Toast.makeText(requireContext(), "Halo", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void progressCheck(List<Theory> value) {

        int c = (int) value.stream().filter(Theory::isChecked).count();
        int progress = (int)((float) c/ value.size()*100);
        requireContext().getSharedPreferences("theoryProgress", Context.MODE_PRIVATE)
                .edit().putInt("theoryProgress", progress).apply();
        int currentProgress = requireContext()
                .getSharedPreferences("theoryProgress", Context.MODE_PRIVATE)
                .getInt("theoryProgress", 0);
        binding.numProgress.setVisibility(currentProgress == 0 ?
                View.GONE : View.VISIBLE);  //Пофиксить
        binding.numProgress.setText( MessageFormat.format("{0}%", currentProgress));
        setProgressColor(currentProgress);
        if(currentProgress == 100) {
            SharedPreferences preferences = requireContext().getSharedPreferences("TheoryIsChecked", Context.MODE_PRIVATE);
            boolean isChecked = preferences.getBoolean("TheoryIsChecked", false);
            if(!isChecked) {
                preferences.edit().putBoolean("TheoryIsChecked", true).apply();
                showDialogOneTime();
            }
        }
    }

    private void setProgressColor(int progress) {
        String progressColor = 10 - progress /10 == 10? "ff" : (10 - progress /10) + "" + (10 - progress /10);
        String color = "#" + progressColor +"ff" + progressColor;
        binding.numProgress.setTextColor(Color.parseColor(color));
        binding.textProgress.setTextColor(Color.parseColor(color));
    }

    private void showDialogOneTime() {
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle("Прогресс завершен");
        dialog.setMessage("Вы прочитали всю теорию");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE,
                "Открыть следующие статьи(потом доделаю)",
                (dialog1, which) -> {
            dialog1.dismiss();
        });
        dialog.show();
    }

    @Override
    public void onTheoryClick(int position, View itemView) {
        theoryViewModel.getTheories().observe(getViewLifecycleOwner(), (value) -> {
            value.get(position).setChecked(true);
        });
        final int[] fragment_ids = {
            R.id.action_theoryFragment_to_selectedTheoryFragment,
            R.id.action_theoryFragment_to_presentContinuousFragment,
            R.id.action_theoryFragment_to_presentPerfectFragment,
            R.id.action_theoryFragment_to_presentPerfectContinuousFragment,
            R.id.action_theoryFragment_to_pastSimpleFragment,
        };
        Navigation.findNavController(requireView()).navigate(fragment_ids[position]);
    }

}
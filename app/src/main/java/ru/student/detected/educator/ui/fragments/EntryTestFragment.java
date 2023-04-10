package ru.student.detected.educator.ui.fragments;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ru.student.detected.educator.data.models.Question;
import ru.student.detected.educator.viewmodel.EntryTestViewModel;
import ru.student.detected.educator.viewmodel.QuestionViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentEntryTestBinding;

public class EntryTestFragment extends Fragment {

    private EntryTestViewModel mViewModel;
    private FragmentEntryTestBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_entry_test, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QuestionViewModel qViewModel = new QuestionViewModel(requireActivity().getApplication());
        qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questionList -> {
            if (questionList == null){
                return;
            }
            for(Question question : questionList){
                Log.d("questions", question.getQuestion());
                Log.d("questions", question.getDescription());
            }
            shuffle(questionList);
            binding.varA.setOnClickListener(v -> shuffle(questionList));

        });
    }

    private void shuffle(List<Question> questionList) {
        Collections.shuffle(questionList);
        Question question = questionList.get(0);
        binding.question.setText(question.getQuestion());
        binding.description.setText(question.getDescription());
        binding.varA.setText(String.format("a)%s", question.getVariants().get(0)));
        binding.varB.setText(String.format("b)%s", question.getVariants().get(1)));
        binding.varC.setText(String.format("c)%s", question.getVariants().get(2)));
    }
}
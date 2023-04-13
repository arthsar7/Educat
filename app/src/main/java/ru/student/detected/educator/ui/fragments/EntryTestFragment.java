package ru.student.detected.educator.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.Objects;

import ru.student.detected.educator.data.models.Question;
import ru.student.detected.educator.viewmodel.EntryTestViewModel;
import ru.student.detected.educator.viewmodel.QuestionViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentEntryTestBinding;

public class EntryTestFragment extends Fragment {

    private EntryTestViewModel mViewModel;
    private FragmentEntryTestBinding binding;
    private int points = 0;

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
        qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questionEntities -> {
            for (Question question : questionEntities) {
                Log.d("Question", question.getQuestion());
            }
        }
        );
        int steps = 0;
        binding.varA.setOnClickListener(v -> {
           if(Objects.requireNonNull(qViewModel.
                   getAllQuestions().getValue()).get(steps).equals(binding.varA.getText())) {
               points++;
           }
        });
    }

//    private void shuffle(List<QuestionEntity> questionList) {
//        Collections.shuffle(questionList);
//        QuestionEntity question = questionList.get(0);
//        binding.question.setText(question.getQuestion());
//        binding.description.setText(question.getDescription());
//        binding.varA.setText(String.format("a)%s", question.getVariants().get(0)));
//        binding.varB.setText(String.format("b)%s", question.getVariants().get(1)));
//        binding.varC.setText(String.format("c)%s", question.getVariants().get(2)));
//        binding.image.setImageDrawable(ContextCompat.getDrawable(requireContext().getApplicationContext(), question.getImageId()));
//    }
}
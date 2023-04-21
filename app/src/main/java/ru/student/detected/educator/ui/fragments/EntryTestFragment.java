package ru.student.detected.educator.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.student.detected.educator.data.models.Question;
import ru.student.detected.educator.ui.views.EntryTestDialog;
import ru.student.detected.educator.viewmodel.EntryTestViewModel;
import ru.student.detected.educator.viewmodel.QuestionViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentEntryTestBinding;

public class EntryTestFragment extends Fragment {

    private EntryTestViewModel mViewModel;
    private FragmentEntryTestBinding binding;
    private List<Question> currentQuestions;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_entry_test, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(EntryTestViewModel.class);
        QuestionViewModel qViewModel = new QuestionViewModel(requireActivity().getApplication());
        qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questions -> {
            for (Question question : questions) {
                Log.d("Question", question.getQuestion());
            }
        });
        EntryTestDialog dialog = new EntryTestDialog(requireContext()) {
            @Override
            public void onBeginnerClick(View beginner) {
                qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questions -> {
                    currentQuestions = questions.stream().
                            filter(question -> question.getDifficulty() == 1).collect(Collectors.toList());
                    shuffle(currentQuestions);
                });
            }
            @Override
            public void onIntermediateClick(View intermediate) {
                qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questions -> {
                    currentQuestions = questions.stream().
                            filter(question -> question.getDifficulty() == 2).collect(Collectors.toList());
                    shuffle(currentQuestions);
                });
            }
            @Override
            public void onAdvancedClick(View advanced) {
                qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questions -> {
                    currentQuestions = questions.stream().
                            filter(question -> question.getDifficulty() == 3).collect(Collectors.toList());
                    shuffle(currentQuestions);
                });
            }
            @Override
            public void onBackClick(View back) {
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_entryTestFragment_to_tests);
            }
        };
        dialog.show();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void shuffle(List<Question> questionList) {
        Collections.shuffle(questionList);
        Question question = questionList.get(0);
        binding.question.setText(question.getQuestion());
        binding.description.setText(question.getDescription());
        binding.varA.setText(String.format("a)%s", question.getVariants().get(0)));
        binding.varB.setText(String.format("b)%s", question.getVariants().get(1)));
        binding.varC.setText(String.format("c)%s", question.getVariants().get(2)));
        binding.image.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.hello_kitten));
    }
}
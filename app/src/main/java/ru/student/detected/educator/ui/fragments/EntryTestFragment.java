package ru.student.detected.educator.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.student.detected.educator.data.models.Question;
import ru.student.detected.educator.ui.views.EntryTestDialog;
import ru.student.detected.educator.viewmodel.EntryTestViewModel;
import ru.student.detected.educator.viewmodel.QuestionViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentEntryTestBinding;

public class EntryTestFragment extends Fragment {

    private EntryTestViewModel mViewModel;
    private QuestionViewModel qViewModel;
    private FragmentEntryTestBinding binding;
    private List<Question> currentQuestions;
    private static final int TEST_AWARD = 2;
    private int currentStep;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        qViewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);
        qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questions -> {
            questions.forEach(question -> {
                Log.d("TAG", "onViewCreated: " + question.getQuestion());
            });
        });
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_entry_test, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(EntryTestViewModel.class);
        mViewModel.addStep();
        mViewModel.getPoints().observe(getViewLifecycleOwner(), points -> {
            binding.points.setText(String.valueOf(points));
        });
        checkIfTestPassed();


        return binding.getRoot();
    }

    private void checkIfTestPassed() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("EntryTestPassed", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("EntryTestPassed", false)) {
            AlertDialog dialog = new AlertDialog.Builder(requireContext()).create();
            dialog.setMessage("Вы прошли тест, желаете начать заново?");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Начать заново", (dialog1, which) -> {
                mViewModel.setPoints(0);
                mViewModel.setSteps(0);
                sharedPreferences.edit().putBoolean("EntryTestPassed", false).apply();
                dialog1.dismiss();
                showEntryTestDialog(qViewModel);
            });
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(!requireContext().getSharedPreferences("EntryTestPassed",Context.MODE_PRIVATE)
                .getBoolean("EntryTestPassed", false)){
            showEntryTestDialog(qViewModel);
        }

    }

    private void showEntryTestDialog(QuestionViewModel qViewModel) {
        if(mViewModel.getDifficulty().getValue()==null) {
            EntryTestDialog dialog = new EntryTestDialog(requireContext()) {
                @Override
                public void onBeginnerClick(View beginner) {
                    mViewModel.setDifficulty(1);
                    qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questions -> {
                        currentQuestions = questions.stream().
                                filter(question -> question.getDifficulty() == 1).collect(Collectors.toList());
                    });
                    execute();
                }

                @Override
                public void onIntermediateClick(View intermediate) {
                    mViewModel.setDifficulty(2);
                    qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questions -> {
                        currentQuestions = questions.stream().
                                filter(question -> question.getDifficulty() == 2).collect(Collectors.toList());
                    });
                    execute();
                }

                @Override
                public void onAdvancedClick(View advanced) {
                    mViewModel.setDifficulty(3);
                    qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questions -> {
                        currentQuestions = questions.stream().
                                filter(question -> question.getDifficulty() == 3).collect(Collectors.toList());
                    });
                    execute();
                }

                @Override
                public void onBackClick(View back) {
                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_entryTestFragment_to_tests);
                }
            };
            dialog.show();
            dialog.setOnKeyListener((dialogInterface, keycode, event) -> {
                if (keycode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    dialog.dismiss();
                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_entryTestFragment_to_tests);
                }
                return true;
            });
        }
        else{
            qViewModel.getAllQuestions().observe(getViewLifecycleOwner(), questions -> {
                currentQuestions = questions.stream().
                        filter(question -> question.getDifficulty() == mViewModel.getDifficulty().getValue()).collect(Collectors.toList());
                execute();
            });
        }
    }

    private void execute() {
        if(currentQuestions != null) {
            Question currentQuestion = shuffle(currentQuestions);
            Stream.<Button>of(binding.varA, binding.varB, binding.varC).forEach(
                    button -> button.setOnClickListener(v -> {
                        String currentAnswer = button.getText().toString().substring(2);
                        Toast.makeText(requireContext(), currentQuestion.getAnswer(), Toast.LENGTH_SHORT).show();
                if (currentAnswer.equals(currentQuestion.getAnswer())) {
                    Toast.makeText(requireContext(), "Верно", Toast.LENGTH_SHORT).show();
                    mViewModel.addPoints(TEST_AWARD);
                } else {
                    Toast.makeText(requireContext(), "Неверно", Toast.LENGTH_SHORT).show();
                }
                mViewModel.getSteps().observe(getViewLifecycleOwner(), steps -> {
                    if(steps < EntryTestViewModel.ENTRY_TEST_STAGES) {
                        nextStage();
                    }
                    else{
                        stopTest();
                    }
                });
            }));
        }
        else{
            Toast.makeText(requireContext(), "Нет вопросов", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopTest() {
        AlertDialog builder = new AlertDialog.Builder(requireContext()).create();
        builder.setCanceledOnTouchOutside(false);
        SharedPreferences sharedPreferences = requireContext()
                .getSharedPreferences("EntryTestPassed", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("EntryTestPassed", true);
        editor.putBoolean("rlyEntryTestPassed", true);
        editor.apply();
        builder.setTitle("Тест завершен");
        builder.setMessage("Количество баллов: " + mViewModel.getPoints().getValue());
        builder.setButton(DialogInterface.BUTTON_NEUTRAL,"Продолжить", (dialog, which) -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_entryTestFragment_to_tests);
            builder.dismiss();
        });
        builder.show();
    }

    private void nextStage() {
        final int[] stages = {
          R.id.action_entryTestFragment_to_pairsFragment,
                R.id.action_entryTestFragment_self,
        };
        final int stages_length = stages.length;
        final int i = (int) (Math.random() * stages_length);
        Navigation.findNavController(requireView()).navigate(stages[i]);
    }


    private Question shuffle(List<Question> questionList) {
        mViewModel.getSteps().observe(getViewLifecycleOwner(), steps -> {
            currentStep = steps;
            Question question = questionList.get(steps);
            binding.question.setText(question.getQuestion());
            binding.description.setText(question.getDescription());
            binding.varA.setText(String.format("a)%s", question.getVariants().get(0)));
            binding.varB.setText(String.format("b)%s", question.getVariants().get(1)));
            binding.varC.setText(String.format("c)%s", question.getVariants().get(2)));
        });
        return questionList.get(currentStep);
    }
}
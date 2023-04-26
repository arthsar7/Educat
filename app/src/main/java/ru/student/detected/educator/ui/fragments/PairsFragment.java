package ru.student.detected.educator.ui.fragments;

import static java.util.Objects.requireNonNull;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ru.student.detected.educator.data.models.Pair;
import ru.student.detected.educator.ui.adapters.PairsAdapter;
import ru.student.detected.educator.viewmodel.EntryTestViewModel;
import ru.student.detected.educator.viewmodel.PairViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentPairsBinding;

public class PairsFragment extends Fragment implements PairsAdapter.ClickListener {
    public static final int PAIR_AWARD = 1;
    private FragmentPairsBinding binding;
    private PairViewModel pairViewModel;
    private Pair currentPair;
    private EntryTestViewModel entryTestViewModel;
    private Map<String, String> map;
    private int lastPosition, currentPoints, currentSteps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pairViewModel = new ViewModelProvider(requireActivity()).get(PairViewModel.class);
        entryTestViewModel = new ViewModelProvider(requireActivity()).get(EntryTestViewModel.class);
        currentPoints = 0;
        currentSteps = 0;
        map = pairViewModel.getMap();
        entryTestViewModel.getPoints().observe(getViewLifecycleOwner(), points -> {
            binding.points.setText(String.valueOf(points));
        });
        entryTestViewModel.addStep();
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_pairs, null, false);
        currentPair = new Pair();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> engWords = new ArrayList<>();
        List<String> rusWords = new ArrayList<>();
        initPairs(engWords, rusWords);
        PairsAdapter engAdapter = new PairsAdapter(engWords, this);
        PairsAdapter rusAdapter = new PairsAdapter(rusWords, this);
        binding.engWordsLayout.setAdapter(engAdapter);
        binding.rusWordsLayout.setAdapter(rusAdapter);
    }

    private void initPairs(List<String> engWords, List<String> rusWords) {
        entryTestViewModel.getDifficulty().observe(getViewLifecycleOwner(), difficulty -> {
            pairViewModel.updatePair();
            pairViewModel.getPairs().observe(getViewLifecycleOwner(), p ->{
                List<Pair> pairs = p.stream().filter(pair -> pair.getDifficulty() == difficulty)
                        .collect(Collectors.toList());
                for(int i = 0; i < 4; i ++){
                    engWords.add(pairs.get(i).getEngWord());
                    rusWords.add(pairs.get(i).getRusWord());
                }
                Arrays.asList(engWords, rusWords).forEach(Collections::shuffle);
            });
        });
    }

    @Override
    public void onItemClick(View view, int position, TextView text) {
        String word = text.getText().toString();
        AppCompatImageView imageView = view.findViewById(R.id.item);
        imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_yellow));
        checkWord(position, word);
    }

    private void checkWord(int position, String word) {
        if(map.containsKey(word)){
            if(currentPair.getEngWord()!=null && position != lastPosition){
                ImageView prevEngItem = requireNonNull(binding.engWordsLayout
                        .findViewHolderForAdapterPosition(lastPosition)).itemView
                        .findViewById(R.id.item);
                prevEngItem.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_gray));
            }
            currentPair.setEngWord(word);
            if(currentPair.getRusWord() != null){
                getAnswer(position, lastPosition, binding.engWordsLayout, binding.rusWordsLayout);
            }
        }
        else{
            if(currentPair.getRusWord()!=null && position != lastPosition){
                ImageView prevRusItem = requireNonNull(binding.rusWordsLayout.findViewHolderForAdapterPosition(lastPosition)).itemView.findViewById(R.id.item);
                prevRusItem.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_gray));
            }
            currentPair.setRusWord(word);
            if(currentPair.getEngWord() != null){
                getAnswer(position, lastPosition, binding.rusWordsLayout, binding.engWordsLayout);
            }
        }
        lastPosition = position;
    }

    private void getAnswer(int position, int lastPosition,RecyclerView first, @NonNull RecyclerView second) {
        currentSteps++;
        ImageView one = requireNonNull(first.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.item);
        ImageView two = requireNonNull(second.findViewHolderForAdapterPosition(lastPosition)).itemView.findViewById(R.id.item);
        if (requireNonNull(pairViewModel.getPairs().getValue()).stream().anyMatch(currentPair::isRightPair)) {
            Toast.makeText(requireContext(), "Вы правы", Toast.LENGTH_SHORT).show();
            Arrays.asList(one, two).forEach(imageView -> imageView.setImageDrawable
                    (ContextCompat.getDrawable(requireContext(), R.drawable.item_green)));
            entryTestViewModel.addPoints(PAIR_AWARD);
            entryTestViewModel.getPoints().observe(getViewLifecycleOwner(), points -> {
                binding.points.setText(String.valueOf(points));
            });
            currentPoints+=PAIR_AWARD;
        }
        else {
            Toast.makeText(requireContext(), "Вы не правы", Toast.LENGTH_SHORT).show();
            Arrays.asList(one, two).forEach(imageView -> imageView.setImageDrawable
                    (ContextCompat.getDrawable(requireContext(), R.drawable.item_red)));
        }
        Arrays.asList(one, two).forEach(imageView -> imageView.setClickable(false));
        currentPair.setEngWord(null);
        currentPair.setRusWord(null);
        if (currentSteps == 4) {
            showDialog();
        }
    }

    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(requireContext()).create();
        dialog.setTitle("Задание пройдено");
        dialog.setMessage("Правильных ответов: " + currentPoints);
        entryTestViewModel.getSteps().observe(getViewLifecycleOwner(), steps->{
            if(steps < EntryTestViewModel.ENTRY_TEST_STAGES){
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Продолжить", (dialogInterface, i) -> {
                    nextStage();
                });
            }
            else{
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Завершить", (dialogInterface, i) -> {
                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences("EntryTestPassed", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("EntryTestPassed", true);
                    editor.apply();
                    dialog.dismiss();
                    Navigation.findNavController(requireView()).navigate(R.id.action_pairsFragment_to_tests);
                });
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void nextStage() {
        pairViewModel.updatePair();
        final int[] stages ={
                R.id.action_pairsFragment_to_entryTestFragment
        };
        final int stages_length = stages.length;
        int i = (int) (Math.random() * stages_length);
        Navigation.findNavController(requireView()).navigate(stages[i]);
    }
}

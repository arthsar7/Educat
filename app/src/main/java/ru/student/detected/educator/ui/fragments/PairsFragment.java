package ru.student.detected.educator.ui.fragments;

import static java.util.Objects.requireNonNull;

import android.app.AlertDialog;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.student.detected.educator.data.models.Pair;
import ru.student.detected.educator.ui.adapters.PairsAdapter;
import ru.student.detected.educator.viewmodel.EntryTestViewModel;
import ru.student.detected.educator.viewmodel.PairViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentPairsBinding;

public class PairsFragment extends Fragment implements PairsAdapter.ClickListener {
    private FragmentPairsBinding binding;
    private PairViewModel pairViewModel;
    private Pair pair;
    private EntryTestViewModel entryTestViewModel;
    private Map<String, String> map;
    private int lastPosition, points, steps;

    public PairsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pairViewModel = new PairViewModel(requireActivity().getApplication());
        entryTestViewModel = new ViewModelProvider(requireActivity()).get(EntryTestViewModel.class);
        points = 0;
        steps = 0;
        map = new HashMap<>();
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_pairs, null, false);
        pair = new Pair();
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
            pairViewModel.getPairs().stream().filter(pair -> pair.getDifficulty() == difficulty)
                    .collect(Collectors.toList()).forEach( pair -> {
                                engWords.add(pair.getEngWord());
                                rusWords.add(pair.getRusWord());
                                map.put(pair.getEngWord(), pair.getRusWord());
                            }
                    );
            Arrays.asList(engWords, rusWords).forEach(Collections::shuffle);
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
            if(pair.getEngWord()!=null && position != lastPosition){
                ImageView prevEngItem = requireNonNull(binding.engWordsLayout
                        .findViewHolderForAdapterPosition(lastPosition)).itemView
                        .findViewById(R.id.item);
                prevEngItem.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_gray));
            }
            pair.setEngWord(word);
            if(pair.getRusWord() != null){
                getAnswer(position, lastPosition, binding.engWordsLayout, binding.rusWordsLayout);
            }
        }
        else{
            if(pair.getRusWord()!=null && position != lastPosition){
                ImageView prevRusItem = requireNonNull(binding.rusWordsLayout.findViewHolderForAdapterPosition(lastPosition)).itemView.findViewById(R.id.item);
                prevRusItem.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_gray));
            }
            pair.setRusWord(word);
            if(pair.getEngWord() != null){
                getAnswer(position, lastPosition, binding.rusWordsLayout, binding.engWordsLayout);
            }
        }
        lastPosition = position;
    }

    private void getAnswer(int position, int lastPosition,RecyclerView first, @NonNull RecyclerView second) {
        steps++;
        ImageView one = requireNonNull(first.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.item);
        ImageView two = requireNonNull(second.findViewHolderForAdapterPosition(lastPosition)).itemView.findViewById(R.id.item);
        if (pairViewModel.getPairs().stream().anyMatch(pair::isRightPair)) {
            Toast.makeText(requireContext(), "Вы правы", Toast.LENGTH_SHORT).show();
            Arrays.asList(one, two).forEach(imageView -> imageView.setImageDrawable
                    (ContextCompat.getDrawable(requireContext(), R.drawable.item_green)));
            points++;
        }
        else {
            Toast.makeText(requireContext(), "Вы не правы", Toast.LENGTH_SHORT).show();
            Arrays.asList(one, two).forEach(imageView -> imageView.setImageDrawable
                    (ContextCompat.getDrawable(requireContext(), R.drawable.item_red)));
        }
        Arrays.asList(one, two).forEach(imageView -> imageView.setClickable(false));
        pair.setEngWord(null);
        pair.setRusWord(null);
        if (steps == map.size()) {
            showDialog();
        }
    }

    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(requireContext()).create();
        dialog.setTitle("Задание пройдено");
        dialog.setMessage("Правильных ответов: " + points);
        dialog.show();
    }
}

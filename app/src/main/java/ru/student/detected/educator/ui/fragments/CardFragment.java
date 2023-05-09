package ru.student.detected.educator.ui.fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import ru.student.detected.educator.data.models.Pair;
import ru.student.detected.educator.viewmodel.DictionaryViewModel;
import ru.student.detected.educator.viewmodel.PairViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.RandomWords1Binding;

public class CardFragment extends Fragment {
    private RandomWords1Binding binding;
    private AnimatorSet front_anim;
    private AnimatorSet back_anim;
    private PairViewModel pairViewModel;
    private Map<String, String> dictionary;
    private TextToSpeech tts;
    private DictionaryViewModel dictionaryViewModel;
    private boolean isFront = true;
    private FirebaseFirestore db;
    int index;
    private boolean isWordsOver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.random_words1, container, false);
        dictionaryViewModel = new ViewModelProvider(requireActivity()).get(DictionaryViewModel.class);
        pairViewModel = new ViewModelProvider(this).get(PairViewModel.class);
        dictionary = pairViewModel.getMap();
        db = FirebaseFirestore.getInstance();
        binding.back.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
        isWordsOver = false;
        initWords();
        return binding.getRoot();
    }

    private void initWords() {
        List<String> eng = new ArrayList<>(dictionary.keySet());
        List<String> rus = new ArrayList<>(dictionary.values());
        dictionaryViewModel.getLivePairs().observe(getViewLifecycleOwner(),
                pairs -> {
            List<String> allEng = pairs.stream().map(Pair::getEngWord).collect(Collectors.toList());
            List<String> allRus = pairs.stream().map(Pair::getRusWord).collect(Collectors.toList());
            eng.removeAll(allEng);
            rus.removeAll(allRus);
            if(eng.size() == 0){
                binding.readFront.setVisibility(View.GONE);
                binding.readBack.setVisibility(View.GONE);
                binding.next.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Вы запомнили все слова, зайдите в словарь", Toast.LENGTH_SHORT).show();
            }
        });
        if(eng.size() == 0){
            binding.readFront.setVisibility(View.GONE);
            binding.readBack.setVisibility(View.GONE);
            Toast.makeText(requireContext(), "Вы запомнили все слова, зайдите в словарь", Toast.LENGTH_SHORT).show();
        }
        else{
            index = new Random().nextInt(eng.size());
            binding.textFront.setText(eng.get(index));
            binding.textBack.setText(rus.get(index));
        }
        isWordsOver = eng.size() == 0;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        float scale = getResources().getDisplayMetrics().density;
        binding.cardFront.setCameraDistance(8000*scale);
        binding.cardBack.setCameraDistance(8000*scale);
        binding.next.setOnClickListener(v -> {
            initWords();
            if (binding.readFront.getVisibility() == View.GONE) {
                binding.readFront.setVisibility(View.VISIBLE);
                binding.readBack.setVisibility(View.VISIBLE);
            }
        });
        front_anim = (AnimatorSet) AnimatorInflater.loadAnimator(requireContext(),R.animator.front_animator);
        back_anim = (AnimatorSet) AnimatorInflater.loadAnimator(requireContext(),R.animator.back_animator);
        binding.cardFront.setOnClickListener(v -> {
            startAnimation();
        });
        tts = new TextToSpeech(requireContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.UK);
            }
        });
        binding.play.setOnClickListener(v -> {
            if(isFront)
                tts.speak(binding.textFront.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
        });
        binding.readFront.setOnClickListener(v -> {
            if (!isWordsOver) {
                Pair pair = new Pair(binding.textFront.getText().toString(),
                        binding.textBack.getText().toString(),
                        pairViewModel.getDifficultyByWords(binding.textFront.getText().toString(),
                                binding.textBack.getText().toString()));
                dictionaryViewModel.addPair(pair);
                Toast.makeText(requireContext(), "Слово " + binding.textFront.getText().toString() +
                        " успешно добавлено в ваш словарь, проверьте словарь", Toast.LENGTH_SHORT).show();
                binding.readFront.setVisibility(View.GONE);
                binding.readBack.setVisibility(View.GONE);
            }
            else{
                Toast.makeText(requireContext(), "Вы запомнили все слова, зайдите в словарь", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void startAnimation() {
        if(!front_anim.isRunning() && !back_anim.isRunning()) {

            if (isFront) {
                front_anim.setTarget(binding.cardFront);
                back_anim.setTarget(binding.cardBack);
                front_anim.start();
                back_anim.start();
                isFront = false;
            } else {
                front_anim.setTarget(binding.cardBack);
                back_anim.setTarget(binding.cardFront);
                front_anim.start();
                back_anim.start();
                isFront = true;
            }
        }
    }
}

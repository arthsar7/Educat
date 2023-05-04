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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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
    private boolean isFront = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.random_words1, container, false);
        initWords();
        return binding.getRoot();
    }

    private void initWords() {
        pairViewModel = new ViewModelProvider(this).get(PairViewModel.class);
        dictionary = pairViewModel.getMap();
        List<String> eng = new ArrayList<>(dictionary.keySet());
        List<String> rus = new ArrayList<>(dictionary.values());
        int index = new Random().nextInt(eng.size());
        binding.textFront.setText(eng.get(index));
        binding.textBack.setText(rus.get(index));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        float scale = getResources().getDisplayMetrics().density;
        binding.cardFront.setCameraDistance(8000*scale);
        binding.cardBack.setCameraDistance(8000*scale);
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
            Toast.makeText(requireContext(), "Слово " + binding.textFront.getText().toString() +
                    " успешно добавлено в ваш словарь, проверьте словарь", Toast.LENGTH_SHORT).show();
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

package ru.student.detected.educator.ui.fragments.articles;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.concurrent.atomic.AtomicReference;

import ru.student.detected.educator.viewmodel.TheoryViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentPastSimpleBinding;

public class PastSimpleFragment extends Fragment {
    private TheoryViewModel theoryViewModel;
    private FragmentPastSimpleBinding binding;
    private static final AtomicReference<MediaPlayer> mediaPlayer = new AtomicReference<>(new MediaPlayer());;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_past_simple, container, false);
        theoryViewModel = new ViewModelProvider(requireActivity()).get(TheoryViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setTheoryViewModel(theoryViewModel);
        binding.button.setOnClickListener(v -> {
            playIfNotPlaying(R.raw.past_simple1);
        });
        binding.button2.setOnClickListener(v ->
                playIfNotPlaying(R.raw.past_simple2));
        return binding.getRoot();
    }
    private void playIfNotPlaying(int audioID) {
        if(!mediaPlayer.get().isPlaying()) {
            mediaPlayer.set(MediaPlayer.create(getContext(), audioID));
            mediaPlayer.get().start();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.back.setOnClickListener(v-> {
            Navigation.findNavController(v).popBackStack();
        });
    }
}

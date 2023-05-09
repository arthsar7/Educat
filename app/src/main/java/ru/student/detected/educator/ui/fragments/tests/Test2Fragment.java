package ru.student.detected.educator.ui.fragments.tests;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import ru.student.detected.educator.data.models.VideoQuestion;
import ru.student.detected.educator.viewmodel.VideoQuestionViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentTest2Binding;

public class Test2Fragment extends Fragment {
    private FragmentTest2Binding binding;
    private VideoQuestionViewModel qViewModel;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test2, container, false);
        qViewModel = new ViewModelProvider(requireActivity()).get(VideoQuestionViewModel.class);
        position = 0;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVideo();
        playVideo();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(requireContext(), "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void playVideo() {
        MediaController mediaController = new MediaController(requireContext());
        binding.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(binding.videoView);
        binding.videoView.seekTo(1);
        binding.videoView.setOnPreparedListener(mp -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.frame.setVisibility(View.VISIBLE);

        }, 100));
        binding.videoView.start();
    }

    private void initVideo() {
        List<VideoQuestion> videoQuestions = qViewModel.getVideoQuestionList();
        List<String> phrases = videoQuestions.get(position).getPhrases();
        int right = videoQuestions.get(position).getRightPhrase();
        List<Button> buttons = Arrays.asList(binding.phrase1, binding.phrase2, binding.phrase3);
        Uri uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + videoQuestions.get(position).getVideoId());
        binding.videoView.setVideoURI(uri);
        IntStream.range(0, buttons.size()).forEach(i -> {
            buttons.get(i).setText(phrases.get(i));
            buttons.get(i).setOnClickListener(v -> {
                if (i == right)
                    Toast.makeText(requireContext(), "Right", Toast.LENGTH_SHORT).show();
                if(position < videoQuestions.size()-1) {
                    position++;
                    initVideo();
                    playVideo();
                }
                else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_test2Fragment_to_dialog2Fragment);
                }
            });
        });
    }

    @Override
    public void onResume() {
        binding.videoView.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        binding.videoView.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        binding.videoView.stopPlayback();
        super.onDestroy();
    }
}

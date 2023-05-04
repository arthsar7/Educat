package ru.student.detected.educator.ui.fragments.tests;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import ru.student.detected.educator.data.models.VideoDialog;
import ru.student.detected.educator.viewmodel.VideoDialogViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentDialog2Binding;

public class Dialog2Fragment extends Fragment {
    private FragmentDialog2Binding binding;
    private VideoDialogViewModel qViewModel;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog2, container, false);
        qViewModel = new ViewModelProvider(requireActivity()).get(VideoDialogViewModel.class);
        position = 0;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initVideo();
        playVideo();
        super.onViewCreated(view, savedInstanceState);
    }
    private void initVideo() {
        List<VideoDialog> dialogs = qViewModel.getVideoDialogs();
        List<String> answers = dialogs.get(position).getAnswers();
        int right = dialogs.get(position).getRightI();
        List<Button> buttons = Arrays.asList(binding.phrase1, binding.phrase2, binding.phrase3);
        IntStream.range(0, buttons.size()).forEach(i -> {
            buttons.get(i).setText(answers.get(i));
            buttons.get(i).setOnClickListener(v -> {
                if (i == right) {
                    Toast.makeText(requireContext(), "Верно", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(requireContext(), "Ответ:", Toast.LENGTH_SHORT).show();
                binding.videoView.setVideoURI(Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + dialogs.get(position).getVideoAnswerId()));
                binding.videoView.start();
            });
        });
        Uri uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + dialogs.get(position).getVideoQuestionId());
        binding.videoView.setVideoURI(uri);

    }
    private void playVideo() {
        MediaController mediaController = new MediaController(requireContext());
        binding.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(binding.videoView);
        binding.videoView.seekTo(1);
        binding.videoView.setOnPreparedListener(mp -> new Handler().postDelayed(() ->
                binding.frame.setVisibility(View.VISIBLE), 100));
        binding.videoView.start();
    }
}

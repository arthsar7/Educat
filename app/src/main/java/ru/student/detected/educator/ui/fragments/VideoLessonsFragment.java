package ru.student.detected.educator.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.MessageFormat;

import ru.student.detected.educator.viewmodel.TheoryViewModel;
import ru.student.detected.page1.R;
import ru.student.detected.page1.databinding.FragmentVideoLessonsBinding;

public class VideoLessonsFragment extends Fragment{
    private FragmentVideoLessonsBinding binding;
    private TheoryViewModel theoryViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_lessons, container, false);
        theoryViewModel = new ViewModelProvider(requireActivity()).get(TheoryViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int[] raw = new int[]{R.raw.taxi_driver_talking}; //TODO
        int position = theoryViewModel.getPosition();
        Toast.makeText(requireContext(), "position = " + position, Toast.LENGTH_SHORT).show();
        if(position < raw.length){
            Uri uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + raw[position]);
            binding.video.setVideoURI(uri);
            MediaController mediaController = new MediaController(requireContext());
            binding.video.setMediaController(mediaController);
            mediaController.setAnchorView(binding.video);
            binding.video.seekTo(1);
        }

    }
}

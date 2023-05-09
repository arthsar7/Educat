package ru.student.detected.educator.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
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
        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                .controls(1)
                .fullscreen(1)
                .build();
        getLifecycle().addObserver(binding.video);
        String[] raw = new String[]{"6Qd1xAikoQc", "pz8zEqhGleY","DInKEo2hiVM","TU78I3CRVlY", "gMHxEO3LrQg"};
        int position = theoryViewModel.getPosition();
        Toast.makeText(requireContext(), "position = " + position, Toast.LENGTH_SHORT).show();
        if(position < raw.length){
            binding.video.setEnableAutomaticInitialization(false);
            binding.video.initialize(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                }
            }, iFramePlayerOptions);
            binding.video.getYouTubePlayerWhenReady(youTubePlayer -> {
                YouTubePlayerUtils.loadOrCueVideo(youTubePlayer, getLifecycle(), raw[position],0);
            });
            binding.video.addFullscreenListener(new FullscreenListener() {
                @Override
                public void onEnterFullscreen(@NonNull View view, @NonNull Function0<Unit> function0) {
                    binding.video.setVisibility(View.INVISIBLE);
                    binding.screen.setVisibility(View.VISIBLE);
                    binding.screen.addView(view);
                }

                @Override
                public void onExitFullscreen() {
                    binding.video.setVisibility(View.VISIBLE);
                    binding.screen.setVisibility(View.GONE);
                    binding.screen.removeAllViews();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.video.release();
    }
}

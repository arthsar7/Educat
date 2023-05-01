package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import ru.student.detected.educator.data.models.VideoDialog;
import ru.student.detected.educator.data.repositories.VideoDialogRepository;

public class VideoDialogViewModel extends AndroidViewModel {
    private VideoDialogRepository repository;
    private List<VideoDialog> videoDialogs;
    public VideoDialogViewModel(@NonNull Application application) {
        super(application);
        repository = new VideoDialogRepository();
        videoDialogs = repository.getVideoDialogs(application);
    }

    public List<VideoDialog> getVideoDialogs() {
        return videoDialogs;
    }
}

package ru.student.detected.educator.data.repositories;

import android.app.Application;

import java.util.List;

import ru.student.detected.educator.data.data_sources.VideoDialogDataSource;
import ru.student.detected.educator.data.models.VideoDialog;

public class VideoDialogRepository {
    private final VideoDialogDataSource videoDialogDataSource;
    public VideoDialogRepository(){
        this.videoDialogDataSource = new VideoDialogDataSource();
    }
    public List<VideoDialog> getVideoDialogs(Application application){
        return videoDialogDataSource.dialogs(application);
    }
}

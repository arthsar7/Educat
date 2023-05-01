package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import ru.student.detected.educator.data.models.VideoQuestion;
import ru.student.detected.educator.data.repositories.VideoQuestionRepository;

public class VideoQuestionViewModel extends AndroidViewModel {
    private VideoQuestionRepository videoQuestionRepository;
    private List<VideoQuestion> videoQuestionList;
    public VideoQuestionViewModel(@NonNull Application application) {
        super(application);
        videoQuestionRepository = new VideoQuestionRepository();
        videoQuestionList = videoQuestionRepository.getVideoQuestions(application);
    }

    public List<VideoQuestion> getVideoQuestionList() {
        return videoQuestionList;
    }

    public void setVideoQuestionList(List<VideoQuestion> videoQuestionList) {
        this.videoQuestionList = videoQuestionList;
    }
}

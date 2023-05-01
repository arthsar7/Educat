package ru.student.detected.educator.data.repositories;

import android.app.Application;

import java.util.List;

import ru.student.detected.educator.data.data_sources.VideoQuestionDataSource;
import ru.student.detected.educator.data.models.VideoQuestion;

public class VideoQuestionRepository {
    private final VideoQuestionDataSource videoQuestionDataSource;

    public VideoQuestionRepository() {
        this.videoQuestionDataSource = new VideoQuestionDataSource();
    }

    public List<VideoQuestion> getVideoQuestions(Application application) {
        return videoQuestionDataSource.videoQuestions(application);
    }
}

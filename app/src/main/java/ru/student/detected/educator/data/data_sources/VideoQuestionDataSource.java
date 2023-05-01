package ru.student.detected.educator.data.data_sources;

import android.app.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ru.student.detected.educator.data.models.VideoQuestion;
import ru.student.detected.page1.R;

public class VideoQuestionDataSource {
    public List<VideoQuestion> videoQuestions(Application application) {
        List<VideoQuestion> videoQuestionList;
        List<String> phrases = Arrays.asList(application.getApplicationContext().getResources().getStringArray(R.array.video_phrases));
        List<List<String>> phrasesList = phrases.stream().map(s -> Arrays.asList(s.split(","))).collect(Collectors.toList());
        int[] imageIds = new int[]{R.raw.taxi_driver_talking, R.raw.cotozavr};
        int[] rightPhrases = application.getApplicationContext().getResources().getIntArray(R.array.video_right_phrases);
        videoQuestionList = IntStream.range(0, phrasesList.size()).mapToObj(i -> new VideoQuestion(phrasesList.get(i), rightPhrases[i], imageIds[i])).collect(Collectors.toList());
        return videoQuestionList;
    }
}

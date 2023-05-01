package ru.student.detected.educator.data.data_sources;

import android.app.Application;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ru.student.detected.educator.data.models.VideoDialog;
import ru.student.detected.page1.R;

public class VideoDialogDataSource {
    public List<VideoDialog> dialogs(Application application){
        List<VideoDialog> dialogs;
        int[] videoAnswers = new int[]{R.raw.kunfu_2};
        int[] videoQuestions = new int[]{R.raw.kunfu_1};
        int[] rightIs = application.getApplicationContext().getResources().getIntArray(R.array.video_right_answers);
        List<String> video_answers = Arrays.asList(application.getApplicationContext().getResources().getStringArray(R.array.video_answers));
        List<List<String>> answersList = video_answers.stream().map(s -> Arrays.asList(s.split(";"))).collect(Collectors.toList());
        dialogs = IntStream.range(0, videoQuestions.length).mapToObj(i -> new VideoDialog(answersList.get(i),
                rightIs[i],videoQuestions[i], videoAnswers[i])).collect(Collectors.toList());
        return dialogs;
    }
}

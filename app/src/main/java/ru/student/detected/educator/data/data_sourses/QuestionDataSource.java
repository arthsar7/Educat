package ru.student.detected.educator.data.data_sourses;


import java.util.ArrayList;
import java.util.List;

import ru.student.detected.educator.data.models.ImageQuestion;
import ru.student.detected.educator.data.models.Question;
import ru.student.detected.page1.R;

public class QuestionDataSource {
    public List<Question> questions() {
        List<Question> questions = new ArrayList<>();
        for(int i = 0; i < R.array.questions; i++) {
        }
        return questions;
    }
}

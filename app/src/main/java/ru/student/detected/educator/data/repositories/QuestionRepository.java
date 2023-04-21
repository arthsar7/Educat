package ru.student.detected.educator.data.repositories;
import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ru.student.detected.educator.data.data_sources.room.entities.QuestionEntity;
import ru.student.detected.educator.data.data_sources.room.root.QuestionDatabase;
import ru.student.detected.educator.data.models.Question;

public class QuestionRepository {
    private final QuestionDatabase databaseSource;

    public QuestionRepository(Application application) {
        databaseSource = QuestionDatabase.getDatabase(application);
    }

    public LiveData<List<Question>> getQuestionData() {
        return Transformations.map(databaseSource.questionDao().getAllQuestions(),
                questionEntities -> questionEntities.stream().
                        map(QuestionEntity::toDomainModel).collect(Collectors.toList()));
    }

    public void insertQuestion(Question... question) {
        QuestionDatabase.databaseWriteExecutor.execute(() ->
                databaseSource.questionDao().insertAll(Arrays.stream(question).map(q ->
                        new QuestionEntity(q.getQuestion(), q.getAnswer(), q.getVariants(),
                                q.getDescription(), q.getDifficulty())).toArray(QuestionEntity[]::new))
                );
    }

    public void deleteAllQuestions(){
        QuestionDatabase.databaseWriteExecutor.execute(() ->
                databaseSource.questionDao().deleteAll());
    }
}

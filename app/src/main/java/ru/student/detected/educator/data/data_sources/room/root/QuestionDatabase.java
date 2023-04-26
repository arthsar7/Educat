package ru.student.detected.educator.data.data_sources.room.root;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.student.detected.educator.data.data_sources.room.dao.QuestionDao;
import ru.student.detected.educator.data.data_sources.room.converters.Converters;
import ru.student.detected.educator.data.data_sources.room.entities.QuestionEntity;

@Database(entities = {QuestionEntity.class},
        version = 2,
autoMigrations = {
        @androidx.room.AutoMigration(from = 1, to = 2)
})
@TypeConverters(Converters.class)
public abstract class QuestionDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static volatile QuestionDatabase INSTANCE;
    public static QuestionDatabase getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (QuestionDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            QuestionDatabase.class, "questions-database").
                            createFromAsset("db/QuestionEntity.db").build();
                }
            }
        }
        return INSTANCE;
    }

}

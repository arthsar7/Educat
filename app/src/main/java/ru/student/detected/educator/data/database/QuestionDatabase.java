package ru.student.detected.educator.data.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ru.student.detected.educator.data.models.Question;

@Database(entities = {Question.class},
        version = 2)
@TypeConverters(Converters.class)
public abstract class QuestionDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public static volatile QuestionDatabase INSTANCE;
    public static QuestionDatabase getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (QuestionDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            QuestionDatabase.class, "questions-database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}

package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.student.detected.educator.data.models.DictionaryDB;
import ru.student.detected.educator.data.models.Pair;

public class DictionaryViewModel extends AndroidViewModel {
    private FirebaseFirestore db;
    private MutableLiveData<List<Pair>> livePairs = new MutableLiveData<>();
    public DictionaryViewModel(@NonNull Application application) {
        super(application);
        db = FirebaseFirestore.getInstance();
        initPairs();
    }
    public void addPair(Pair pair) {
        List<Pair> pairs = livePairs.getValue();
        if (pairs != null) {
            pairs.add(pair);
            Map<String,List<Pair>> data = new HashMap<>();
            data.put("words",pairs);
            db.collection("dictionary")
                    .document(FirebaseAuth.getInstance().getUid())
                    .set(data);
        }
        else {
            List<Pair> newPairs = new ArrayList<>();
            newPairs.add(pair);
            livePairs.setValue(newPairs);
            Map<String,List<Pair>> data = new HashMap<>();
            data.put("words",newPairs);
            db.collection("dictionary")
                    .document(FirebaseAuth.getInstance().getUid())
                    .set(data);
        }
    }

    private void initPairs() {
        db.collection("dictionary")
                .document(FirebaseAuth.getInstance().getUid()).get()
                .addOnSuccessListener(documentSnapshot -> {
                   if (documentSnapshot.exists()){
                       List<Pair> pairs = documentSnapshot.toObject(DictionaryDB.class).getWords();
                       livePairs.setValue(pairs);
                   }
                });
    }

    public LiveData<List<Pair>> getLivePairs() {
        return livePairs;
    }

}

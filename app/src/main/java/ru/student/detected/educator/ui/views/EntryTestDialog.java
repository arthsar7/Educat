package ru.student.detected.educator.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import ru.student.detected.page1.R;

public abstract class EntryTestDialog extends Dialog {
    private Button beginner;
    private Button intermediate;
    private Button advanced;
    private Button back;

    public EntryTestDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.entry_test_dialog);
        beginner = findViewById(R.id.beginner);
        intermediate = findViewById(R.id.intermediate);
        advanced = findViewById(R.id.advanced);
        back = findViewById(R.id.back_to_home);
        beginner.setOnClickListener(beginner1 -> {
            onBeginnerClick(beginner1);
            dismiss();
        });
        intermediate.setOnClickListener(intermediate1 -> {
            onIntermediateClick(intermediate1);
            dismiss();
        });
        advanced.setOnClickListener(advanced1 -> {
            onAdvancedClick(advanced1);
            dismiss();
        });
        back.setOnClickListener(back1 -> {
            onBackClick(back1);
            dismiss();
        });
        setCanceledOnTouchOutside(false);
    }
    abstract public void onBeginnerClick(View beginner);
    abstract public void onIntermediateClick(View intermediate);
    abstract public void onAdvancedClick(View advanced);
    abstract public void onBackClick(View back);

    public Button getIntermediate() {
        return intermediate;
    }

    public Button getAdvanced() {
        return advanced;
    }

    public Button getBack() {
        return back;
    }
}

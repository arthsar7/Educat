package ru.student.detected.educator.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.student.detected.page1.R;

public class LocationDialog extends Dialog {
    TextView textView;
    Button next;

    public LocationDialog(@NonNull Context context) {
        super(context);
    }

    public LocationDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LocationDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_location_dialog);
        textView = findViewById(R.id.text);
        next = findViewById(R.id.next);
        next.setOnClickListener(v -> dismiss());
    }
    public void setText(String text){
        textView.setText(text);
    }
}
